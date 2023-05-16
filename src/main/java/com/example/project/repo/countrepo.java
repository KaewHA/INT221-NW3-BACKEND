package com.example.project.repo;

import com.example.project.Entity.announcement;
import com.example.project.Entity.count;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface countrepo extends JpaRepository<count,Integer> {

}
