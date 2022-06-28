package cloud.lihan.elasticsearch.document;

import lombok.Data;

/**
 * @author hanyun.li
 * @createTime 2022/06/24 15:04:00
 */
@Data
public class UserDocument {
    private String id;
    private String name;
    private String sex;
    private Integer age;
    private String city;
}
