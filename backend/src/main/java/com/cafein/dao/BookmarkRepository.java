package com.cafein.dao;

import com.cafein.entity.Bookmark;
import com.cafein.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer>, BookmarkRepositoryCustom {
}
