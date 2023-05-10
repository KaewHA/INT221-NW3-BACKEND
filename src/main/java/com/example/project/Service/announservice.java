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

import java.time.ZonedDateTime;
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
        if(announcement.getAnnouncementTitle().length()>200){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "TITLE >200");
        }else if(announcement.getAnnouncementDescription().length()>10000){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "DES >10000");
        }else if(!announcement.getAnnouncementDisplay().equals("Y") && !announcement.getAnnouncementDisplay().equals("N") ){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "DISPLAY ");
        }else if(announcement.getAnnouncementDescription().trim().length()==0){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "DES is null");
        }else if(announcement.getAnnouncementTitle().trim().length()==0){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "TITLE is null");
        }
        if(announcement.getPublishDate()==null){
            if(announcement.getCloseDate()!=null){
                ZonedDateTime x=announcement.getCloseDate();
                long cl=x.toEpochSecond();
                long now=ZonedDateTime.now().toEpochSecond();
                if(now>cl){
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "CLOSE DATE IS PART");
                }
            }

        }else {
            if(announcement.getPublishDate().toEpochSecond() < ZonedDateTime.now().toEpochSecond()){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "CLOSE DATE IS PART");
            }
            if(announcement.getCloseDate()!=null){
                ZonedDateTime x=announcement.getCloseDate();
                ZonedDateTime y=announcement.getPublishDate();
                long cl=x.toEpochSecond();
                long now=ZonedDateTime.now().toEpochSecond();
                long pb=y.toEpochSecond();
                if(now>cl || cl<pb){
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "CLOSE DATE IS PART AND PAST FROM PB");
                }
            }
        }
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
      if(news.getAnnouncementTitle().length()>200){
          throw new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, "TITLE >200");
      }else if(news.getAnnouncementDescription().length()>10000){
          throw new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, "DES >10000");
      }else if(!news.getAnnouncementDisplay().equals("Y") && !news.getAnnouncementDisplay().equals("N") ){
          throw new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, "DISPLAY ");
      }else if(news.getAnnouncementDescription().trim().length()==0){
          throw new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, "DES is null");
      }else if(news.getAnnouncementTitle().trim().length()==0){
          throw new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, "TITLE is null");
      }
      if(news.getPublishDate()==null){
          if(news.getCloseDate()!=null){
              ZonedDateTime x=news.getCloseDate();
              long cl=x.toEpochSecond();
              long now=ZonedDateTime.now().toEpochSecond();
              if(now>cl){
                  throw new ResponseStatusException(
                          HttpStatus.BAD_REQUEST, "CLOSE DATE IS PART");
              }
          }

      }else {
          if(news.getPublishDate().toEpochSecond() < ZonedDateTime.now().toEpochSecond()){
              throw new ResponseStatusException(
                      HttpStatus.BAD_REQUEST, "CLOSE DATE IS PART");
          }
          if(news.getCloseDate()!=null){
              ZonedDateTime x=news.getCloseDate();
              ZonedDateTime y=news.getPublishDate();
              long cl=x.toEpochSecond();
              long now=ZonedDateTime.now().toEpochSecond();
              long pb=y.toEpochSecond();
              if(now>cl || cl<pb){
                  throw new ResponseStatusException(
                          HttpStatus.BAD_REQUEST, "CLOSE DATE IS PART AND PAST FROM PB");
              }
          }
      }
      return  Optional.of(repo.saveAndFlush(news));
    }
}
