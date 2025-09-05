package com.productms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productms.entity.MyUser;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Integer>{

	MyUser findByUsername(String username);

}
