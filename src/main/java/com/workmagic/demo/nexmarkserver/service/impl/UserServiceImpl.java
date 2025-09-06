package com.workmagic.demo.nexmarkserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.workmagic.demo.nexmarkserver.entity.User;
import com.workmagic.demo.nexmarkserver.mapper.UserMapper;
import com.workmagic.demo.nexmarkserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean createUser(User user) {
        if (user == null) {
            return false;
        }
        
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setDeleted(0);
        user.setStatus(1);
        
        if (StringUtils.hasText(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        return save(user);
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getId() == null) {
            return false;
        }
        
        User existingUser = getById(user.getId());
        if (existingUser == null) {
            return false;
        }
        
        user.setUpdateTime(LocalDateTime.now());
        
        if (StringUtils.hasText(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(existingUser.getPassword());
        }
        
        return updateById(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (id == null) {
            return false;
        }
        
        return removeById(id);
    }

    @Override
    public User getUserById(Long id) {
        if (id == null) {
            return null;
        }
        return getById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        if (!StringUtils.hasText(username)) {
            return null;
        }
        return userMapper.selectByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return null;
        }
        return userMapper.selectByEmail(email);
    }

    @Override
    public User getUserByPhone(String phone) {
        if (!StringUtils.hasText(phone)) {
            return null;
        }
        return userMapper.selectByPhone(phone);
    }

    @Override
    public IPage<User> getUserPage(Page<User> page, User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        
        if (user != null) {
            if (StringUtils.hasText(user.getUsername())) {
                queryWrapper.like(User::getUsername, user.getUsername());
            }
            if (StringUtils.hasText(user.getEmail())) {
                queryWrapper.like(User::getEmail, user.getEmail());
            }
            if (StringUtils.hasText(user.getNickname())) {
                queryWrapper.like(User::getNickname, user.getNickname());
            }
            if (user.getStatus() != null) {
                queryWrapper.eq(User::getStatus, user.getStatus());
            }
        }
        
        queryWrapper.orderByDesc(User::getCreateTime);
        
        return page(page, queryWrapper);
    }

    @Override
    public boolean checkUsernameExists(String username) {
        if (!StringUtils.hasText(username)) {
            return false;
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean checkEmailExists(String email) {
        if (!StringUtils.hasText(email)) {
            return false;
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean checkPhoneExists(String phone) {
        if (!StringUtils.hasText(phone)) {
            return false;
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        return count(queryWrapper) > 0;
    }

}