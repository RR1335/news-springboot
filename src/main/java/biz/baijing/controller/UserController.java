package biz.baijing.controller;

import biz.baijing.common.ErrorMessage;
import biz.baijing.pojo.Result;
import biz.baijing.pojo.User;
import biz.baijing.service.UserService;
import biz.baijing.utils.JwtUtil;
import biz.baijing.utils.Md5Util;
import biz.baijing.utils.ThreadLocalUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static biz.baijing.common.ErrorMessage.*;

@RestController
@RequestMapping("/user")
@Validated
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 注册用户
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{3,10}$") String username,@Pattern(regexp = "^\\S{3,10}$") String password) {
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
    public Result<String> login(@Pattern(regexp = "^\\S{3,10}$") String username,@Pattern(regexp = "^\\S{3,10}$") String password) {
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

            ValueOperations<String, String> svOps = stringRedisTemplate.opsForValue();
            svOps.set(token,token,12, TimeUnit.HOURS);  // 和 JWT 实效时间一致

            return Result.success(token);
        }

        // 密码错误
        return Result.error(ErrorMessage.LOGOIN_PASSWORD_ERROR);
    }

    /**
     * 查询用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
        // 获取当前用户名 （或 id，存 token 的时候存了 id）
//        log.info("userInfo - 获取的token: {}",token);
/*        Map<String, Object> map =  JwtUtil.parseToken(token);
        String username = (String) map.get("username");*/

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findByUsername(username);

        return Result.success(user);
    }

    /**
     * 修改个人信息
     * @param user
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        log.info("用户数据 {}", user);
        userService.update(user);

        return Result.success();
    }

    /**
     * 更新头像
     * @param avatarUrl
     * @return
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam("avatar") @URL String avatarUrl) {

        userService.updateAvatar(avatarUrl);

        return Result.success();
    }

    /**
     * 修改密码
     * @param params
     * @return
     */
    @PatchMapping("/updatePwd")
    public Result updatePW(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String token) {
        // 或者 表的数据
        log.info("params:{}", params);
        log.info("修改密码 Controller - token:{}", token);

        System.out.println(params.get("new_pwd"));

        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");


        if (StringUtils.isBlank(oldPwd) || StringUtils.isBlank(newPwd) || StringUtils.isBlank(rePwd)) {
            return  Result.error(PASSWORD_BLANK);
        }

        if (!newPwd.equals(rePwd)) {
            return   Result.error(TWO_NEW_PASSWORD_NOT_EQUALS);
        }

        if (oldPwd.equals(newPwd)) {
            return  Result.error(OLD_NEW_PASSWORD_EQUALS);
        }

        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        String password = loginUser.getPassword();

        String md5OldPwd = Md5Util.getMD5String(oldPwd);

        log.info("就密码 和 数据库已存入密码:{},{}", md5OldPwd , password);

        if (!md5OldPwd.equals(password)) {
            return  Result.error(OLD_PASSWORD_ERROR);
        }

        String nowPwd = Md5Util.getMD5String(newPwd);

        userService.updatePW(nowPwd);

        // 密码修改成功，redis 的 token 实效
        ValueOperations<String, String> svOps = stringRedisTemplate.opsForValue();
        svOps.getOperations().delete(token);


        return Result.success();
    }
}
