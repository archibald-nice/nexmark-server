package com.workmagic.demo.nexmarkserver.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.workmagic.demo.nexmarkserver.entity.User;

public interface UserService extends IService<User> {

    /**
     * 创建用户
     */
    boolean createUser(User user);

    /**
     * 更新用户
     */
    boolean updateUser(User user);

    /**
     * 删除用户
     */
    boolean deleteUser(Long id);

    /**
     * 根据ID查询用户
     */
    User getUserById(Long id);

    /**
     * 根据用户名查询用户
     */
    User getUserByUsername(String username);

    /**
     * 根据邮箱查询用户
     */
    User getUserByEmail(String email);

    /**
     * 根据手机号查询用户
     */
    User getUserByPhone(String phone);

    /**
     * 分页查询用户
     */
    IPage<User> getUserPage(Page<User> page, User user);

    /**
     * 检查用户名是否存在
     */
    boolean checkUsernameExists(String username);

    /**
     * 检查邮箱是否存在
     */
    boolean checkEmailExists(String email);

    /**
     * 检查手机号是否存在
     */
    boolean checkPhoneExists(String phone);

}