package com.cafein.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafein.entity.Cafe;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Integer>, CafeRepositoryCustom {
}
