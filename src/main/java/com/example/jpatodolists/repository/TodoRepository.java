package com.example.jpatodolists.repository;

import com.example.jpatodolists.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {}