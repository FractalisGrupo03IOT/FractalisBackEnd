package com.fractalis.greentoolswebservice.account.domain.services;

import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    Optional<User> getUserById(Long userId);
    List<User> getAllUsers();
}
