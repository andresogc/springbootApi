package com.andresogc.apialmacenspringboot.repository;

import com.andresogc.apialmacenspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
