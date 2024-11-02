package com.fractalis.greentoolswebservice.account.domain.services;

import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;
import com.fractalis.greentoolswebservice.account.domain.model.valueobjects.EmailAddress;

public interface UserCommandService {
    User createUser(String firstName, String lastName, String email, String street, String number, String city, String zipCode, String country, String profileImage);
    User updateUser(Long userId, String firstName, String lastName, String email, String street, String number, String city, String zipCode, String country);
    void deleteUser(Long id);
}
