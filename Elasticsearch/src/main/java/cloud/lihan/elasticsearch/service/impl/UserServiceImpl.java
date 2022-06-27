package cloud.lihan.elasticsearch.service.impl;

import cloud.lihan.elasticsearch.constant.Constant;
import cloud.lihan.elasticsearch.document.UserDocument;
import cloud.lihan.elasticsearch.service.UserService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author hanyun.li
 * @createTime 2022/06/24 16:40:00
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private RestHighLevelClient client;

    @Override
    public Boolean createUserIndex(String index) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0)
        );
        createIndexRequest.mapping("{\n" +
                "  \"properties\": {\n" +
                "    \"city\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"sex\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"name\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"id\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"age\": {\n" +
                "      \"type\": \"integer\"\n" +
                "    }\n" +
                "  }\n" +
                "}", XContentType.JSON);
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return createIndexResponse.isAcknowledged();
    }

    @Override
    public Boolean deleteUserIndex(String index) throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        AcknowledgedResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return deleteIndexResponse.isAcknowledged();
    }

    @Override
    public Boolean createUserDocument(UserDocument userDocument) throws Exception {
        UUID uuid = UUID.randomUUID();
        userDocument.setId(uuid.toString());
        IndexRequest indexRequest = new IndexRequest(Constant.INDEX)
                .id(userDocument.getId())
                .source(JSON.toJSONString(userDocument), XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.status().equals(RestStatus.OK);
    }

    @Override
    public Boolean bulkCreateUserDocument(List<UserDocument> userDocuments) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (UserDocument document : userDocuments) {
            String id = UUID.randomUUID().toString();
            document.setId(id);
            IndexRequest indexRequest = new IndexRequest(Constant.INDEX)
                    .id(id)
                    .source(JSON.toJSONString(document), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse.status().equals(RestStatus.OK);
    }

    @Override
    public UserDocument getUserDocument(String id) throws IOException {
        GetRequest getRequest = new GetRequest(Constant.INDEX, id);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        UserDocument result = new UserDocument();
        if (getResponse.isExists()) {
            String sourceAsString = getResponse.getSourceAsString();
            result = JSON.parseObject(sourceAsString, UserDocument.class);
        } else {
            logger.error("没有找到该 id 的文档");
        }
        return result;
    }
}
