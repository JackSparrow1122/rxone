package com.gryphon.rxone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gryphon.rxone.model.Users;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	Optional<Users> findByEmail(String email);

}