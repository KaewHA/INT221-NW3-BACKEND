package com.example.project.repo;

import com.example.project.Entity.Category;
import com.example.project.Entity.announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface annorepo extends JpaRepository<announcement,Integer> {
 Page<announcement> findByCategory_CategoryID (int cate,Pageable pageable);
 List<announcement> findByCategory_CategoryIDAndAnnouncementDisplay (int cate,String display);
 List<announcement> findByCategory_CategoryID (int cate);
 List<announcement> findByAnnouncementDisplay (String display);

}
