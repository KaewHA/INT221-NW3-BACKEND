package com.example.project.repo;

import com.example.project.Entity.Category;
import com.example.project.Entity.announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface annorepo extends JpaRepository<announcement,Integer> {

}
