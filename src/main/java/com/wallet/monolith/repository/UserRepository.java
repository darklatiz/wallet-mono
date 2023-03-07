package com.wallet.monolith.repository;

import com.wallet.monolith.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    boolean existById(Integer id);
}
