package com.example.lys.controller;

import com.example.lys.entity.User;
import com.example.lys.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public void add(@RequestBody User user) {

        userService.addUser(user);

    }

    @PostMapping("/delete")
    public void delete(@RequestParam Long id) {

        userService.deleteById(id);

    }

    @GetMapping("/get")
    public List<User> getAllUser(@RequestParam("current_page") int current_page, @RequestParam("page_size") int page_size) {

        return userService.findByPage(current_page - 1, page_size).getContent();
    }

    @GetMapping("/search")
    public List<User> getUserByKeyword(@RequestParam("keyword") String keyword, @RequestParam("current_page") int current_page, @RequestParam("page_size") int page_size) {

        return userService.findByPageWithKeyword(current_page - 1, page_size, keyword).getContent();

    }

    @PostMapping("/edit")
    public void modify(@RequestBody User user) {
        User editUser = userService.findById(user.getId());

        editUser.setUserName(user.getUserName());
        editUser.setGender(user.getGender());
        editUser.setPhone(user.getPhone());
        editUser.setAge(user.getAge());

        userService.addUser(editUser);

    }
}
