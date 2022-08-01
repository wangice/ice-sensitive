package com.ice.sensitive;

import com.ice.framework.annotation.SensitiveMethod;
import com.ice.framework.annotation.SensitiveParam;
import com.ice.framework.enums.SensitiveType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangwei
 * @Date 2022/4/1 19:34
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @SensitiveMethod({@SensitiveParam(fields = "user", type = SensitiveType.CHINESE_NAME),
    @SensitiveParam(fields = "identity", type = SensitiveType.ID_CARD),
    @SensitiveParam(fields = "phone", type = SensitiveType.MOBILE_PHONE)})
    @GetMapping(value = "getUser")
    public UserResponse getUser() {
        UserResponse userResponse = new UserResponse();
        userResponse.setUser("王伟");
        userResponse.setIdentity("429004199802276291");
        userResponse.setPhone("13476257867");
        return userResponse;
    }


    @SensitiveMethod
    @GetMapping(value = "getSensitiveProperties")
    public UserResponse getSensitiveProperties() {
        UserResponse userResponse = new UserResponse();
        userResponse.setUser("王伟");
        userResponse.setIdentity("429004199802276291");
        userResponse.setPhone("13476257867");
        userResponse.setCity("湖北省仙桃市挖口村六组");
        return userResponse;
    }
}
