package com.cafein.dao;

import com.cafein.dto.menu.selectmenu.SelectMenuOutput;
import com.cafein.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("select new com.cafein.dto.menu.selectmenu.SelectMenuOutput(m.name, m.price)"
            + " from Menu m"
            + " where m.cafe.id = ?1"
    )
    List<SelectMenuOutput> findByCafeId(int cafeId);

}
