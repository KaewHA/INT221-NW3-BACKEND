package com.example.project.Controler;

import com.example.project.Entity.Category;
import com.example.project.Service.cateservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class catecontroller {
    @Autowired
    private cateservice service;
    @GetMapping("")
    public List<Category> getcategoriesall(){
        return service.getcategoriesall();
    }

    @PostMapping("")
    public Optional<Category> addcategory(@RequestBody Category category){
        return service.addcategory(category);
    }
}
