package com.example.project.repo;

import com.example.project.Entity.Category;
import com.example.project.Entity.announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface annorepo extends JpaRepository<announcement,Integer> {
}
