package cloud.lihan.wishbox.wish.dao.inner;

import cloud.lihan.wishbox.wish.document.WishDocument;

import java.io.IOException;
import java.util.List;

/**
 * 愿望相关的数据方法
 *
 * @author hanyun.li
 * @createTime 2022/06/29 10:16:00
 */
public interface WishDao {

    /**
     * 创建单个愿望文档
     *
     * @param wishDocument 愿望文档
     * @return true:创建成功 false:创建失败
     * @throws IOException 异常信息
     */
    Boolean createWishDocument(WishDocument wishDocument) throws IOException;

    /**
     * 批量创建多个愿望文档
     *
     * @param wishDocuments 愿望文档
     * @return true:创建成功 false:创建失败
     * @throws IOException 异常信息
     */
    Boolean bulkCreateWishDocument(List<WishDocument> wishDocuments) throws IOException;

    /**
     * 根据ID获取愿望文档
     *
     * @param wishDocumentId 愿望文档标识
     * @return {@link WishDocument}
     * @throws IOException 异常信息
     */
    WishDocument getWishDocumentById(String wishDocumentId) throws IOException;

    /**
     * 获取所有愿望文档
     *
     * @return {@link List<WishDocument>}
     * @throws IOException 异常信息
     */
    List<WishDocument> getWishDocuments() throws IOException;

    /**
     * 随机获取指定数量的愿望文档
     *
     * @param wishDocumentNum 需要获取的愿望文档数量
     * @return {@link List<WishDocument>}
     * @throws IOException 异常信息
     */
    List<WishDocument> getRandomNumbersWishDocuments(Integer wishDocumentNum) throws IOException;

}
