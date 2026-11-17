package com.lelegspears.mural_fyi.repository;

import com.lelegspears.mural_fyi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUser(String user);
}
