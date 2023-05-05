package com.example.project.Service;

import com.example.project.Entity.announcement;
import com.example.project.repo.annorepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Announcement id :" + id + " does not exist !!!")
        );
        repo.deleteById(id);
    }

    public Optional<announcement> updateannouncement(int id, announcement announcement) {
        repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Announcement id :" + id + " does not exist !!!"));
        announcement anno=repo.findById(id).get();
        anno.setAnnouncementTitle(announcement.getAnnouncementTitle());
        anno.setAnnouncementDescription(announcement.getAnnouncementDescription());
        anno.setPublishDate(announcement.getPublishDate());
        anno.setCloseDate(announcement.getCloseDate());
        anno.setAnnouncementDisplay(announcement.getAnnouncementDisplay());
        anno.setCategory(announcement.getCategory());
        return Optional.of(repo.saveAndFlush(anno));
    }

    public Optional<announcement> addannouncement(announcement news) {
        System.out.println(news.getAnnouncementDisplay());
      if(news.getAnnouncementTitle().length()>200){
          throw new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, "TITLE >200");
      }else if(news.getAnnouncementDescription().length()>10000){
          throw new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, "DES >10000");
      }else if(!news.getAnnouncementDisplay().equals("Y") && !news.getAnnouncementDisplay().equals("N") ){
          throw new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, "DISPLAY ");
      }
       return Optional.of(repo.saveAndFlush(news));
    }
}
