package com.example.project.Controler;

import com.example.project.DTO.announcementdetail;
import com.example.project.Entity.announcement;
import com.example.project.Service.announservice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/anno")
@CrossOrigin
public class annoucontrol {
    @Autowired
    private announservice service;

    @Autowired
    private ModelMapper modelMapper;
    @GetMapping("")
    public List<announcement> getall(){
        return service.getall();
    }
    @GetMapping("/dto")
    public List<announcementdetail> getalldetail(){
        List<announcement> myanno=service.getall();
        return myanno.stream().map(e -> modelMapper.map( e, announcementdetail.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<announcementdetail> getID(@PathVariable int id){
        Optional<announcement> announcement=service.getbyid(id);
       return announcement.map(e -> modelMapper.map( e, announcementdetail.class));

    }
}
