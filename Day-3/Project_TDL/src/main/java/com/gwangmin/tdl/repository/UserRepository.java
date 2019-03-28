package com.gwangmin.tdl.repository;

import com.gwangmin.tdl.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByemail(String email);
}
