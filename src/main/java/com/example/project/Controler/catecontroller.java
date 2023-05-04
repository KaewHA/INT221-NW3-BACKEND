package com.example.project.Controler;

import com.example.project.Entity.Category;
import com.example.project.Service.cateservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class catecontroller {
    @Autowired
    private cateservice service;
    @GetMapping("")
    public List<Category> getcategoriesall(){
        return service.getcategoriesall();
    }
}
