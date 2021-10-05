package com.cafein.dao;

import com.cafein.dto.user.selectprofile.SelectProfileOutput;
import com.cafein.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByIdAndStatus(int userId, String status);
    Optional<User> findByEmailAndStatus(String email, String status);
    boolean existsByEmailAndStatus(String email, String status);

    boolean existsByNicknameAndStatus(String nickname, String status);

    @Query("select new com.cafein.dto.user.selectprofile.SelectProfileOutput(u.id, u.nickname)"
            + " from User u where u.id = ?1")
    SelectProfileOutput findUserProfile(int userId);

}