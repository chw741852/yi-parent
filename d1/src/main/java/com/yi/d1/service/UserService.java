package com.yi.d1.service;

import com.google.common.base.Strings;
import com.yi.d1.domain.User;
import com.yi.d1.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void save(User user) {
        Date now = new Date();
        if (Strings.isNullOrEmpty(user.getId())) {
            user.setCreatedAt(now);
            userMapper.insertSelective(user);
        } else {
            user.setUpdatedAt(now);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }
}
