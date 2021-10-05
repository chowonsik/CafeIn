package com.cafein.dao;

import com.cafein.entity.Bookmark;
import com.cafein.entity.Cafe;
import com.cafein.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer>, BookmarkRepositoryCustom {
    Optional<Bookmark> findByUserAndCafe(User user, Cafe cafe);
    Optional<Bookmark> findByUserIdAndCafeId(int userId, int cafeId);
}
