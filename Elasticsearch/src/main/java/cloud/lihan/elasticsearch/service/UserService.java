package cloud.lihan.elasticsearch.service;

import cloud.lihan.elasticsearch.document.UserDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author hanyun.li
 * @createTime 2022/06/24 16:39:00
 */
public interface UserService {

    /**
     * 创建用户索引
     *
     * @param index 索引值
     * @return true:创建成功 false:创建失败
     * @throws IOException 异常信息
     */
    Boolean createUserIndex(String index) throws IOException;

    /**
     * 删除用户索引
     *
     * @param index 索引值
     * @return true:删除成功 false:删除失败
     * @throws IOException 异常信息
     */
    Boolean deleteUserIndex(String index) throws IOException;

    /**
     * 创建用户文档
     *
     * @param userDocument 用户文档
     * @return true:删除成功 false:删除失败
     * @throws Exception 异常信息
     */
    Boolean createUserDocument(UserDocument userDocument) throws Exception;

    /**
     * 批量创建用户文档
     *
     * @param userDocuments 用户文档集合
     * @return true:删除成功 false:删除失败
     * @throws IOException 异常信息
     */
    Boolean bulkCreateUserDocument(List<UserDocument> userDocuments) throws IOException;

    /**
     * 查看用户文档
     *
     * @param id 用户id
     * @return 用户文档
     * @throws IOException 异常信息
     */
    UserDocument getUserDocument(String id) throws IOException;
}
