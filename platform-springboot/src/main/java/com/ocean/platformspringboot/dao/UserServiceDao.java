package com.ocean.platformspringboot.dao;

import com.ocean.platformspringboot.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserServiceDao {

    List<User> getAll();
    User getUserById(Integer id);
    Integer saveUser(User user);
    Integer delUserById(Integer id);


}
