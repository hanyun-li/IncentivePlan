package cloud.lihan.wishbox.wish.service.impl;

import cloud.lihan.common.constants.IntegerConstant;
import cloud.lihan.wishbox.wish.dao.inner.WishDao;
import cloud.lihan.wishbox.wish.document.WishDocument;
import cloud.lihan.wishbox.wish.dto.WishDTO;
import cloud.lihan.wishbox.wish.manager.WishManager;
import cloud.lihan.wishbox.wish.service.inner.WishService;
import cloud.lihan.wishbox.wish.vo.WishVO;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 愿望相关的业务细节
 *
 * @author hanyun.li
 * @createTime 2022/06/28 18:38:00
 */
@Service("WishService")
public class WishServiceImpl implements WishService {

    @Autowired
    private WishDao wishDao;
    @Autowired
    private WishManager wishManager;

    @Override
    public void saveWish(WishVO wishVO) throws IOException {
        wishDao.createWishDocument(wishManager.wishVOConvertWishDocument(wishVO));
    }

    @Override
    public void bulkSaveWish(List<WishVO> wishVOs) throws IOException {
        wishDao.bulkCreateWishDocument(wishManager.wishVOConvertsWishDocument(wishVOs));
    }

    @Override
    public void deleteWishDocumentById(String wishDocumentId) throws IOException {
        wishDao.deleteWishDocumentById(wishDocumentId);
    }

    @Override
    public void fulfillmentWishById(String wishDocumentId) throws IOException {
        // 指定主键
        Query query = new Query.Builder()
                .ids(id -> id.values(wishDocumentId))
                .build();
        // 组装更新map
        Map<String, JsonData> optionMaps = new HashMap<>(IntegerConstant.ONE);
        optionMaps.put("isRealized", JsonData.of(Boolean.TRUE));
        wishDao.updateWishDocumentById(optionMaps, query);
    }

    @Override
    public WishDTO getSingeRandomWish() throws IOException {
        List<WishDTO> multipleRandomWish = this.getMultipleRandomWish(IntegerConstant.ONE);
        if (CollectionUtils.isEmpty(multipleRandomWish)) {
            return new WishDTO();
        }
        return wishManager.wishDocumentConvertWishDTO(multipleRandomWish.get(IntegerConstant.ZERO));
    }

    @Override
    public List<WishDTO> getMultipleRandomWish(Integer wishDocumentNum) throws IOException {
        // 筛选没有被实现的愿望
        Query query = new Query.Builder().term(t -> t
                .field("isRealized")
                .value(v -> v.booleanValue(Boolean.FALSE))
        ).build();
        List<WishDocument> wishDocuments = wishDao.getRandomNumbersWishDocuments(wishDocumentNum, query);
        return wishManager.wishDocumentsConvertWishDTO(wishDocuments);
    }

    @Override
    public WishDTO getWishByWishDocumentId(String wishDocumentId) throws IOException {
        WishDocument wishDocument = wishDao.getWishDocumentById(wishDocumentId);
        return wishManager.wishDocumentConvertWishDTO(wishDocument);
    }

}
