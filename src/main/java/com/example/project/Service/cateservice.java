package com.example.project.Service;

import com.example.project.Entity.Category;
import com.example.project.Entity.announcement;
import com.example.project.repo.categoryrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class cateservice {
    @Autowired
    private categoryrepo repo;

    public List<Category> getcategoriesall(){
        return repo.findAll();
    }
    public Optional<Category> getcategoryByid(int id){
        return repo.findById(id);
    }

    public Optional<Category> addcategory(Category category) {
        if(category.getCategoryName().length()>50){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "NAME >50");
        }
        return  Optional.of(repo.saveAndFlush(category));
    }
}
