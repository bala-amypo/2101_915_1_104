package com.example.demo.service;

import com.example.demo.model.UserAccount;

public interface UserAccountService {
    UserAccount save(UserAccount user);
    boolean validateUser(String username, String password);
}
