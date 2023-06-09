package com.wolfalone.gamecdbackend.repository;

import com.wolfalone.gamecdbackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

    @Query(nativeQuery = true, value = "select * from tbl_category where id in (:category)")
    List<Category> findByListId(@Param("category") List<Integer> category);
}
