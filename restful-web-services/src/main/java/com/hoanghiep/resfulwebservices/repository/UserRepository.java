package com.hoanghiep.resfulwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoanghiep.resfulwebservices.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
