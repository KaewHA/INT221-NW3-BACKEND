package com.example.project.Service;

import com.example.project.Entity.Category;
import com.example.project.repo.categoryrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class cateservice {
    @Autowired
    private categoryrepo repo;

    public List<Category> getOffices(){
        return repo.findAll();
    }
}
