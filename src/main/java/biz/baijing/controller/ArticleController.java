package biz.baijing.controller;


import biz.baijing.common.ErrorMessage;
import biz.baijing.pojo.Result;
import biz.baijing.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/list")
    public Result<String> list(@RequestHeader(name = "Authorization") String token, HttpServletResponse response) {
        // 从 请求头 获得token 数据
        // 验证 token
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);      // int SC_UNAUTHORIZED = 401
            return Result.error(ErrorMessage.UNLOGIN);
        }

        return Result.success("list");
    }
}
