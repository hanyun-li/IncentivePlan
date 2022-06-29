package cloud.lihan.wishbox.wish.manager;

import cloud.lihan.common.constants.TimeFormatConstant;
import cloud.lihan.wishbox.wish.document.WishDocument;
import cloud.lihan.wishbox.wish.dto.WishDTO;
import cloud.lihan.wishbox.wish.vo.WishVO;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 愿望相关的实体对象之间的字段属性赋值
 *
 * @author hanyun.li
 * @createTime 2022/06/29 14:02:00
 */
@Component("wishManager")
public class WishManager {

    /**
     * 特定对象转换 {@link WishDocument} to {@link WishDTO}
     *
     * @param wishDocument 愿望文档实体
     * @return {@link WishDTO}
     */
    public WishDTO WishDocumentConvertWishDTO(WishDocument wishDocument) {
        if (Objects.isNull(wishDocument)) {
            return null;
        }
        WishDTO wishDTO = new WishDTO();
        wishDTO.setId(wishDocument.getId());
        wishDTO.setWishInfo(wishDocument.getWishInfo());
        wishDTO.setIsRealized(wishDocument.getIsRealized());
        wishDTO.setUserId(wishDocument.getUserId());
        wishDTO.setCreateTime(wishDocument.getCreateTime());
        wishDTO.setUpdateTime(wishDocument.getUpdateTime());
        return wishDTO;
    }

    /**
     * 特定对象转换 {@link WishVO} to {@link WishDocument}
     *
     * @param wishVO 愿望输入实体
     * @return {@link WishDocument}
     */
    public WishDocument WishVOConvertWishDocument(WishVO wishVO) {
        if (Objects.isNull(wishVO)) {
            return null;
        }
        WishDocument wishDocument = new WishDocument();
        wishDocument.setId(wishVO.getId());
        wishDocument.setWishInfo(wishVO.getWishInfo());
        wishDocument.setIsRealized(wishVO.getIsRealized());
        wishDocument.setUserId(wishVO.getUserId());
        SimpleDateFormat format = new SimpleDateFormat(TimeFormatConstant.STANDARD);
        wishDocument.setCreateTime(format.format(new Date()));
        wishDocument.setUpdateTime(format.format(new Date()));
        return wishDocument;
    }
}
