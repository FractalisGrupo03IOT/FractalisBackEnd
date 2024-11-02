package com.fractalis.greentoolswebservice.account.infrastructure.persistence.jpa.repositories;

import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
