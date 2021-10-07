package com.cafein.dao;

import com.cafein.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TagsRepository extends JpaRepository<Tags, Integer> {
    Optional<Tags> findByCafeId(int cafeId);
}
