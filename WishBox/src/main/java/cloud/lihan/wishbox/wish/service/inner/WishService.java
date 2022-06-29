package cloud.lihan.wishbox.wish.service.inner;

import cloud.lihan.wishbox.wish.dto.WishDTO;
import cloud.lihan.wishbox.wish.vo.WishVO;

import java.io.IOException;
import java.util.List;

/**
 * 愿望相关的业务方法
 *
 * @author hanyun.li
 * @createTime 2022/06/28 18:38:00
 */
public interface WishService {

    /**
     * 随机获取一个愿望
     *
     * @return {@link WishDTO}
     */
    WishDTO getSingeRandomWish() throws IOException;

    /**
     * 随机获取指定数量的愿望(最多获取10条)
     *
     * @param wishDocumentNum 需要获取的愿望文档数量
     * @return {@link List<WishDTO>}
     */
    List<WishDTO> getMultipleRandomWish(Integer wishDocumentNum) throws IOException;

    /**
     * 保存愿望
     *
     * @param wishVO 愿望信息
     * @return {@link WishVO}
     */
    Boolean saveWish(WishVO wishVO) throws IOException;
}
