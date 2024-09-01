package com.practice.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.practice.entities.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByEmail(String email);

}
