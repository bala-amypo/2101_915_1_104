package com.example.demo.service.impl;

import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.service.UserAccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service  // 🔴 THIS WAS MISSING
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository repo;
    private final PasswordEncoder encoder;

    public UserAccountServiceImpl(UserAccountRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public UserAccount register(UserAccount user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("USER");
        return repo.save(user);
    }

    @Override
    public boolean validateUser(String email, String rawPassword) {
        UserAccount user = repo.findByEmail(email).orElse(null);
        if (user == null) {
            return false;
        }
        return encoder.matches(rawPassword, user.getPassword());
    }
}
