package cloud.lihan.wishbox.wish.controller;

import cloud.lihan.common.controller.BaseController;
import cloud.lihan.common.core.Base;
import cloud.lihan.wishbox.wish.service.inner.WishService;
import cloud.lihan.wishbox.wish.vo.WishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * 愿望相关控制器
 *
 * @author hanyun.li
 * @createTime 2022/06/29 15:16:00
 */
@Controller("wishController")
@RequestMapping("/wish")
public class WishController extends BaseController {

    @Autowired
    private WishService wishService;

    @GetMapping()
    public Base getSingeRandomWish() {
        try {
            return apiOk(wishService.getSingeRandomWish());
        } catch (IOException e) {
            return apiErr(e.getMessage());
        }
    }

    @PutMapping()
    public Base savaWish(@RequestBody WishVO wishVO) {
        try {
            Boolean isSaved = wishService.saveWish(wishVO);
            if (isSaved) {
                return apiOk();
            } else {
                return apiErr("保存失败");
            }
        } catch (IOException e) {
            return apiErr(e.getMessage());
        }
    }

}
