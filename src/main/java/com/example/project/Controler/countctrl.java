package com.example.project.Controler;

import com.example.project.Entity.count;
import com.example.project.Service.countservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/count")
@CrossOrigin
public class countctrl {
    @Autowired
    private countservice service;
    @GetMapping("/{id}")
    public Optional<count> getcount(@PathVariable Integer id){
        return service.findCount(id);
    }
}
