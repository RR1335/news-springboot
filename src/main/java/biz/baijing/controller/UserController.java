package biz.baijing.controller;

import biz.baijing.common.ErrorMessage;
import biz.baijing.pojo.Result;
import biz.baijing.pojo.User;
import biz.baijing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册用户
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public Result register(String username, String password) {
        // 确定用户是否存在
        User user = userService.findByUsername(username);
        if (user != null) {
            return Result.error(ErrorMessage.REGISTER_ERROR);
        }else {
            userService.register(username,password);
            return Result.success();
        }
    }


}
