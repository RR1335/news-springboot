package biz.baijing.service;

import biz.baijing.pojo.User;

public interface UserService {
    /**
     * 查找用户根据用户名
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 注册用户
     * @param username
     * @param password
     */
    void register(String username, String password);

    /**
     * 更新用户信息
     * @param user
     */
    void update(User user);

    /**
     * 更新头像
     * @param avatarUrl
     */
    void updateAvatar(String avatarUrl);

    /**
     * 更新密码
     * @param nowPwd
     */
    void updatePW(String nowPwd);
}
