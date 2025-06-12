package biz.baijing.service.impl;

import biz.baijing.mapper.UserMapper;
import biz.baijing.pojo.User;
import biz.baijing.service.UserService;
import biz.baijing.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查找用户根据用户名
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        User user = userMapper.findByUsername(username);

        return user;
    }

    /**
     * 注册用户
     * @param username
     * @param password
     */
    public void register(String username, String password) {
        userMapper.add(username,password);
    }

    /**
     * 更新用户信息
     * @param user
     */
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    /**
     * 更新头像
     * @param avatarUrl
     */
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        // updatetime 在 Mapper 中处理， now()
        userMapper.updateAvatar(id,avatarUrl);
    }
}
