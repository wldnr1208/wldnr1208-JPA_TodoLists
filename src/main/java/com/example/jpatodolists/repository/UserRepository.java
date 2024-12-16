package com.example.jpatodolists.repository;

import com.example.jpatodolists.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
