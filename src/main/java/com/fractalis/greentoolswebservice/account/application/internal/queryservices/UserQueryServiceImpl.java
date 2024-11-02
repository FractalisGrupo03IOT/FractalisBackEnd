package com.fractalis.greentoolswebservice.account.queryservices;

import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;
import com.fractalis.greentoolswebservice.account.domain.services.UserQueryService;
import com.fractalis.greentoolswebservice.account.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Autowired
    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
