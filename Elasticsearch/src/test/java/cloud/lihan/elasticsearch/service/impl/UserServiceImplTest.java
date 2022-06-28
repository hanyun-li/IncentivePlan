package cloud.lihan.elasticsearch.service.impl;

import cloud.lihan.elasticsearch.constant.Constant;
import cloud.lihan.elasticsearch.document.UserDocument;
import cloud.lihan.elasticsearch.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author hanyun.li
 * @createTime 2022/06/24 17:22:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void createUserIndex() throws IOException {
        userService.createUserIndex(Constant.INDEX);
    }

    @Test
    void deleteUserIndex() throws IOException {
        userService.deleteUserIndex(Constant.INDEX);
    }

    @Test
    void createUserDocument() throws Exception {
        userService.createUserDocument(new UserDocument());
    }

    @Test
    void bulkCreateUserDocument() {
    }

    @Test
    void getUserDocument() throws IOException {
        UserDocument userDocument = userService.getUserDocument("1");
        System.out.println(userDocument);
    }
}