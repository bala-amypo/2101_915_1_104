package com.example.demo.service.impl;

import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.service.UserAccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository repository;
    private final PasswordEncoder encoder;

    public UserAccountServiceImpl(UserAccountRepository repository,
                                  PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserAccount register(UserAccount user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public boolean validateLogin(String email, String rawPassword) {
        UserAccount user = repository.findByEmail(email).orElse(null);
        if (user == null) return false;

        return encoder.matches(rawPassword, user.getPassword()); // ✔ boolean
    }
}
