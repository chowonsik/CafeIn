package com.cafein.dao;

import com.cafein.entity.UserDB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDB, Integer> {
    Optional<UserDB> findByEmail(String email);

    List<UserDB> findByEmailAndStatus(String email, String status);
    boolean existsByEmailAndStatus(String email, String status);

    boolean existsByNicknameAndStatus(String nickname, String status);

}