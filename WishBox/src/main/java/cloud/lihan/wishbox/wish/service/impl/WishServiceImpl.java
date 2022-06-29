package cloud.lihan.wishbox.wish.service.impl;

import cloud.lihan.common.constants.IntegerConstant;
import cloud.lihan.wishbox.wish.dao.inner.WishDao;
import cloud.lihan.wishbox.wish.document.WishDocument;
import cloud.lihan.wishbox.wish.dto.WishDTO;
import cloud.lihan.wishbox.wish.manager.WishManager;
import cloud.lihan.wishbox.wish.service.inner.WishService;
import cloud.lihan.wishbox.wish.vo.WishVO;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
    public WishDTO getSingeRandomWish() throws IOException {
        WishDocument wishDocument = wishDao.getWishDocumentById("1");
        return wishManager.WishDocumentConvertWishDTO(wishDocument);
    }

    @Override
    public List<WishDTO> getMultipleRandomWish(Integer wishDocumentNum) throws IOException {
        List<WishDocument> wishDocuments = wishDao.getRandomNumbersWishDocuments(wishDocumentNum);
        List<WishDTO> wishDTOS = new LinkedList<>();
        for (WishDocument wishDocument : wishDocuments) {
            WishDTO wishDTO = wishManager.WishDocumentConvertWishDTO(wishDocument);
            if (Objects.nonNull(wishDTO)) {
                wishDTOS.add(wishDTO);
            }
        }
        return wishDTOS;
    }

    @Override
    public Boolean saveWish(WishVO wishVO) throws IOException {
        WishDocument wishDocument = wishManager.WishVOConvertWishDocument(wishVO);
        if (Objects.isNull(wishDocument)) {
            return Boolean.FALSE;
        }
        return wishDao.createWishDocument(wishDocument);
    }

}
