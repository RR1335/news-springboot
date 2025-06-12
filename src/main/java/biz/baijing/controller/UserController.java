package biz.baijing.controller;

import biz.baijing.common.ErrorMessage;
import biz.baijing.pojo.Result;
import biz.baijing.pojo.User;
import biz.baijing.service.UserService;
import biz.baijing.utils.JwtUtil;
import biz.baijing.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
@Slf4j
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
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password) {
        // 确定用户是否存在
        User user = userService.findByUsername(username);
        if (user != null) {
            return Result.error(ErrorMessage.REGISTER_ERROR);
        }else {
            String md5PW = Md5Util.getMD5String(password);     // 对 password 进行 md5 加密
            userService.register(username,md5PW);
            return Result.success();
        }
    }


    /**
     * 登录
     * @param username
     * @param password
     * @return                // String jwt的字符串
     */
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password) {
        User loginUser = userService.findByUsername(username);
        // 用户不存在，直接返回一个错误
        if (loginUser == null) {
            return Result.error(ErrorMessage.LOGIN_ERROR);
        }

        // 用户名密码正确，登录成功
        if (loginUser.getPassword().equals(Md5Util.getMD5String(password))) {
            // 获取登录成功的 Token
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);

            return Result.success(token);
        }

        // 密码错误
        return Result.error(ErrorMessage.LOGOIN_PASSWORD_ERROR);
    }

    /**
     * 查询用户信息
     * @param token
     * @return
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token) {
        // 获取当前用户名 （或 id，存 token 的时候存了 id）
        log.info("userInfo - 获取的token: {}",token);
        Map<String, Object> map =  JwtUtil.parseToken(token);
        String username = (String) map.get("username");

/*        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");*/

        User user = userService.findByUsername(username);

        return Result.success(user);
    }

}
