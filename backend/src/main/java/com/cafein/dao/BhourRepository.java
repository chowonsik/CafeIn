package com.cafein.dao;

import com.cafein.dto.bhour.selectbhour.SelectBhourOutput;
import com.cafein.entity.Bhour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BhourRepository extends JpaRepository<Bhour, Integer> {
    @Query("select new com.cafein.dto.bhour.selectbhour.SelectBhourOutput(b.type, b.weekType, " +
            "COALESCE(b.mon,0), COALESCE(b.tue,0), COALESCE(b.wed,0), COALESCE(b.thu,0), COALESCE(b.fri,0), COALESCE(b.sat,0), COALESCE(b.sun,0), " +
            "b.startTime, b.endTime, b.etc)"
            + " from Bhour b"
            + " where b.cafe.id = ?1"
    )
    List<SelectBhourOutput> findByCafeId(int cafeId);

}
