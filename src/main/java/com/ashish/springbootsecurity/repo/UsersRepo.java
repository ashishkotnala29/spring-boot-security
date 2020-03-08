package com.ashish.springbootsecurity.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashish.springbootsecurity.models.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long>{

	Optional<Users> findByUserName(String userName);
}
