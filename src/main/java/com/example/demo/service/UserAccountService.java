package com.example.demo.service;

import com.example.demo.model.UserAccount;

public interface UserAccountService {

    UserAccount save(UserAccount user);

    UserAccount validateUser(String email, String password);
}
