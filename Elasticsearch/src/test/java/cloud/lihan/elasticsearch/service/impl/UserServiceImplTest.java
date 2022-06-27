package cloud.lihan.elasticsearch.service.impl;

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
        userService.createUserIndex("person");
    }

    @Test
    void deleteUserIndex() throws IOException {
        userService.deleteUserIndex("person");
    }

    @Test
    void createUserDocument() {
    }

    @Test
    void bulkCreateUserDocument() {
    }

    @Test
    void getUserDocument() {
    }
}