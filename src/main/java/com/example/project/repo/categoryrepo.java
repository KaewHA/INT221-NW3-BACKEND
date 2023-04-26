package com.example.project.repo;

import com.example.project.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface categoryrepo extends JpaRepository<Category,Integer> {

}
