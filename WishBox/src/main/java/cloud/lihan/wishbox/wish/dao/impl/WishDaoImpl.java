package cloud.lihan.wishbox.wish.dao.impl;

import cloud.lihan.common.constants.IndexConstant;
import cloud.lihan.common.constants.IntegerConstant;
import cloud.lihan.wishbox.wish.dao.inner.WishDao;
import cloud.lihan.wishbox.wish.document.WishDocument;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson.JSON;
import org.thymeleaf.expression.Lists;
import org.thymeleaf.util.ListUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author hanyun.li
 * @createTime 2022/06/28 18:39:00
 */
@Slf4j
@Repository("wishDao")
public class WishDaoImpl implements WishDao {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 生成随机id使用
     */
    private final UUID uuid = UUID.randomUUID();

    @Override
    public Boolean createWishDocument(WishDocument wishDocument) throws IOException {
        wishDocument.setId(uuid.toString());
        IndexRequest indexRequest = new IndexRequest(IndexConstant.WISH_INDEX)
                .id(wishDocument.getId())
                .source(JSON.toJSONString(wishDocument), XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        return indexResponse.status().equals(RestStatus.OK);
    }

    @Override
    public Boolean bulkCreateWishDocument(List<WishDocument> wishDocuments) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (WishDocument document : wishDocuments) {
            document.setId(uuid.toString());
            IndexRequest indexRequest = new IndexRequest(IndexConstant.WISH_INDEX)
                    .id(uuid.toString())
                    .source(JSON.toJSONString(document), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return bulkResponse.status().equals(RestStatus.OK);
    }

    @Override
    public WishDocument getWishDocumentById(String wishDocumentId) throws IOException {
        GetRequest getRequest = new GetRequest(IndexConstant.WISH_INDEX, wishDocumentId);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        WishDocument result = new WishDocument();
        if (getResponse.isExists()) {
            String sourceAsString = getResponse.getSourceAsString();
            result = JSON.parseObject(sourceAsString, WishDocument.class);
        } else {
            log.error("没有找到wishDocumentId= " + wishDocumentId + " 的文档");
        }
        return result;
    }

    @Override
    public List<WishDocument> getWishDocuments() throws IOException {
        GetRequest getRequest = new GetRequest(IndexConstant.WISH_INDEX);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        String sourceAsString = getResponse.getSourceAsString();
        return JSON.parseArray(sourceAsString, WishDocument.class);
    }

    @Override
    public List<WishDocument> getRandomNumbersWishDocuments(Integer wishDocumentNum) throws IOException {
        List<WishDocument> wishDocuments = this.getWishDocuments();
        if (Objects.isNull(wishDocuments) || Objects.isNull(wishDocumentNum)) {
            return Collections.emptyList();
        }

        if (wishDocumentNum > IntegerConstant.ZERO && wishDocumentNum <= wishDocuments.size()) {
            List<WishDocument> newsWishDocument = new LinkedList<>();
            for (int i = 0; i < wishDocumentNum; i++) {
                newsWishDocument.add(wishDocuments.get(i));
            }
            return newsWishDocument;
        }
        return wishDocuments;
    }

}
