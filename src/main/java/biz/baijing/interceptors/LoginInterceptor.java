package biz.baijing.interceptors;

import biz.baijing.utils.JwtUtil;
import biz.baijing.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authorization");

        // 验证 token
        try {
            log.info("拦截器获取 token {}", token);
            ValueOperations<String, String> sValueOps = stringRedisTemplate.opsForValue();
            String redisToken = sValueOps.get(token);
            log.info("redis 修改密码中获取 {}" ,redisToken);
            if (redisToken == null) {
                throw new RuntimeException();
            }

            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 当前线程设置 user 数据 —— id and username
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);      // int SC_UNAUTHORIZED = 401
            return false;
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清空线程数据，防止内存泄漏
        log.info("清空线程数据 {}", (Object) ThreadLocalUtil.get());
        ThreadLocalUtil.remove();
        log.info("清空线程数据 {}", (Object) ThreadLocalUtil.get());
    }

}
