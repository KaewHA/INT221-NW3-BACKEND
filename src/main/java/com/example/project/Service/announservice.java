package com.example.project.Service;

import com.example.project.Entity.announcement;
import com.example.project.repo.annorepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class announservice {
    @Autowired
    private annorepo repo;

    @Autowired
    private ModelMapper modelMapper;

    public List<announcement> getall(){

        return repo.findAll(Sort.by(Sort.Direction.DESC, "announcementID"));
    }

    public Optional<announcement> getbyid(int id){
        return Optional.ofNullable(repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Announcement id :" + id + " does not exist !!!")));
    }

    public void removeannocemment(int id) {
        announcement announcement = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Announcement id :" + id + " does not exist !!!")
        );
        repo.delete(announcement);
    }
}
