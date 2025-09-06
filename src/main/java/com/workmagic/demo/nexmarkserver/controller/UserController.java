package com.workmagic.demo.nexmarkserver.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.workmagic.demo.nexmarkserver.common.PageQuery;
import com.workmagic.demo.nexmarkserver.common.PageResult;
import com.workmagic.demo.nexmarkserver.common.Result;
import com.workmagic.demo.nexmarkserver.entity.User;
import com.workmagic.demo.nexmarkserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Result<Boolean> createUser(@RequestBody User user) {
        if (user == null) {
            return Result.error("用户信息不能为空");
        }
        
        if (!StringUtils.hasText(user.getUsername())) {
            return Result.error("用户名不能为空");
        }
        
        if (userService.checkUsernameExists(user.getUsername())) {
            return Result.error("用户名已存在");
        }
        
        if (StringUtils.hasText(user.getEmail()) && userService.checkEmailExists(user.getEmail())) {
            return Result.error("邮箱已存在");
        }
        
        if (StringUtils.hasText(user.getPhone()) && userService.checkPhoneExists(user.getPhone())) {
            return Result.error("手机号已存在");
        }
        
        boolean success = userService.createUser(user);
        return success ? Result.success(true) : Result.error("创建用户失败");
    }

    @PutMapping("/{id}")
    public Result<Boolean> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (id == null || user == null) {
            return Result.error("参数不能为空");
        }
        
        user.setId(id);
        
        if (StringUtils.hasText(user.getEmail())) {
            User existingUser = userService.getUserByEmail(user.getEmail());
            if (existingUser != null && !existingUser.getId().equals(id)) {
                return Result.error("邮箱已存在");
            }
        }
        
        if (StringUtils.hasText(user.getPhone())) {
            User existingUser = userService.getUserByPhone(user.getPhone());
            if (existingUser != null && !existingUser.getId().equals(id)) {
                return Result.error("手机号已存在");
            }
        }
        
        boolean success = userService.updateUser(user);
        return success ? Result.success(true) : Result.error("更新用户失败");
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteUser(@PathVariable Long id) {
        if (id == null) {
            return Result.error("用户ID不能为空");
        }
        
        boolean success = userService.deleteUser(id);
        return success ? Result.success(true) : Result.error("删除用户失败");
    }

    @DeleteMapping("/batch")
    public Result<Boolean> deleteUsers(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.error("用户ID列表不能为空");
        }
        
        boolean success = userService.removeByIds(ids);
        return success ? Result.success(true) : Result.error("批量删除用户失败");
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        if (id == null) {
            return Result.error("用户ID不能为空");
        }
        
        User user = userService.getUserById(id);
        return user != null ? Result.success(user) : Result.error("用户不存在");
    }

    @GetMapping("/username/{username}")
    public Result<User> getUserByUsername(@PathVariable String username) {
        if (!StringUtils.hasText(username)) {
            return Result.error("用户名不能为空");
        }
        
        User user = userService.getUserByUsername(username);
        return user != null ? Result.success(user) : Result.error("用户不存在");
    }

    @GetMapping
    public Result<PageResult<User>> getUserPage(User user, PageQuery pageQuery) {
        if (pageQuery == null) {
            pageQuery = new PageQuery();
        }
        
        Page<User> page = new Page<>(pageQuery.getCurrent(), pageQuery.getSize());
        IPage<User> userPage = userService.getUserPage(page, user);
        
        PageResult<User> pageResult = PageResult.build(userPage);
        return Result.success(pageResult);
    }

    @GetMapping("/list")
    public Result<List<User>> getUserList(User user) {
        List<User> userList = userService.list();
        return Result.success(userList);
    }

    @GetMapping("/check-username")
    public Result<Boolean> checkUsername(@RequestParam String username) {
        if (!StringUtils.hasText(username)) {
            return Result.error("用户名不能为空");
        }
        
        boolean exists = userService.checkUsernameExists(username);
        return Result.success(!exists);
    }

    @GetMapping("/check-email")
    public Result<Boolean> checkEmail(@RequestParam String email) {
        if (!StringUtils.hasText(email)) {
            return Result.error("邮箱不能为空");
        }
        
        boolean exists = userService.checkEmailExists(email);
        return Result.success(!exists);
    }

    @GetMapping("/check-phone")
    public Result<Boolean> checkPhone(@RequestParam String phone) {
        if (!StringUtils.hasText(phone)) {
            return Result.error("手机号不能为空");
        }
        
        boolean exists = userService.checkPhoneExists(phone);
        return Result.success(!exists);
    }

    @PutMapping("/{id}/status")
    public Result<Boolean> updateUserStatus(@PathVariable Long id, @RequestParam Integer status) {
        if (id == null || status == null) {
            return Result.error("参数不能为空");
        }
        
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        
        boolean success = userService.updateUser(user);
        return success ? Result.success(true) : Result.error("更新用户状态失败");
    }

}