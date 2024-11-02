package com.fractalis.greentoolswebservice.account.application.internal.commandservices;
import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;
import com.fractalis.greentoolswebservice.account.domain.services.UserCommandService;
import com.fractalis.greentoolswebservice.account.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    @Autowired
    public UserCommandServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String firstName, String lastName, String email, String street, String number, String city, String zipCode, String country, String profileImage) {
        User user = new User(firstName, lastName, email, street, number, city, zipCode, country, profileImage);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, String firstName, String lastName, String email, String street, String number, String city, String zipCode, String country) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.updateName(firstName, lastName);
            existingUser.updateEmail(email);
            existingUser.updateAddress(street, number, city, zipCode, country);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}