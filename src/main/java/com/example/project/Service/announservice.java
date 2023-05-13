package com.example.project.Service;

import com.example.project.Entity.announcement;
import com.example.project.pagedto.PageDTO;
import com.example.project.repo.annorepo;
import com.example.project.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class announservice {
    @Autowired
    private annorepo repo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

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

     public  void  validate (announcement news){
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
     }

    public Optional<announcement> updateannouncement(int id, announcement announcement) {
        repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Announcement id :" + id + " does not exist !!!"));
        validate(announcement);
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
//        validate(news);
      return  Optional.of(repo.saveAndFlush(news));
    }
    public List<announcement> filterclose (List<announcement> myanno){
        List<announcement> mylist= new ArrayList<>();
        myanno.stream().forEach((x)->{
            if(x.getCloseDate()!=null && x.getAnnouncementDisplay().equals("Y")){
                long now= ZonedDateTime.now().toEpochSecond();
                long close=x.getCloseDate().toEpochSecond();
                if(now>=close){
                    mylist.add(x);
                }
            }
        });
        return  mylist;
    }
    public  List<announcement> filteractive (List<announcement> myanno){
        List<announcement> mylist= new ArrayList<>();
        myanno.stream().forEach((x)->{
            if(x.getCloseDate()==null && x.getPublishDate()==null && x.getAnnouncementDisplay().equals("Y")){
                mylist.add(x);
            }else if(x.getCloseDate()==null && x.getPublishDate()!=null && x.getAnnouncementDisplay().equals("Y")){
                long now= ZonedDateTime.now().toEpochSecond();
                long publist=x.getPublishDate().toEpochSecond();
                if(publist<=now){
                    mylist.add(x);
                }
            }else if(x.getCloseDate()!=null && x.getPublishDate()!=null && x.getAnnouncementDisplay().equals("Y")){
                long now= ZonedDateTime.now().toEpochSecond();
                long publist=x.getPublishDate().toEpochSecond();
                long close=x.getCloseDate().toEpochSecond();
                if(publist<=now && now<close){
                    mylist.add(x);
                }
            }else if(x.getCloseDate()!=null && x.getPublishDate()==null && x.getAnnouncementDisplay().equals("Y")){
                long now= ZonedDateTime.now().toEpochSecond();
                long close=x.getCloseDate().toEpochSecond();
                if(close>now){
                    mylist.add(x);
                }
            }

        });
        return mylist;
    }
    public Page<announcement> getpagenodetail (int page, int pagesize, int category, String mode){
        Pageable pageable= PageRequest.of(page,pagesize,Sort.by(Sort.Direction.DESC, "announcementID"));
        if(category==0 && mode.equals("admin")){
            return   repo.findAll(pageable);
        }else if(category!=0 && mode.equals("admin")){
             return  repo.findByCategory_CategoryID(category,pageable);
        }
        else if(category==0 && mode.equals("active")){
            List<announcement> pagex=repo.findByAnnouncementDisplay("Y");
            List<announcement> filteractives=filteractive(pagex);
            Collections.reverse(filteractives);
           // Page<announcement> x=createPage(filteractives,pageable,filteractives.size());
            return  convertArrayListToPage((ArrayList<announcement>) filteractives,page,pagesize);
        }else if(category!=0 && mode.equals("active")){
            List<announcement> pagex=repo.findByCategory_CategoryIDAndAnnouncementDisplay(category,"Y");
            List<announcement> filteractives=filteractive(pagex);
            Collections.reverse(filteractives);
            return  convertArrayListToPage((ArrayList<announcement>) filteractives,page,pagesize);
        }else if(category!=0 && mode.equals("close")){
            List<announcement> pagex=repo.findByCategory_CategoryIDAndAnnouncementDisplay(category,"Y");
            List<announcement> filteractives=filterclose(pagex);
            Collections.reverse(filteractives);
            return  convertArrayListToPage((ArrayList<announcement>) filteractives,page,pagesize);
        }else if(category==0 && mode.equals("close")){
            List<announcement> pagex=repo.findByAnnouncementDisplay("Y");
            List<announcement> filteractives=filterclose(pagex);
            Collections.reverse(filteractives);
            return  convertArrayListToPage((ArrayList<announcement>) filteractives,page,pagesize);
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "CATEGORY CODE OR MODE IS NOT FOUND");
    }

//        public static <T> Page<T> createPage(List<T> content, Pageable pageable, long totalElements) {
//            return new PageImpl<>(content, pageable, totalElements);
//        }

    public Page<announcement> convertArrayListToPage(ArrayList<announcement> arrayList, int pageNumber, int pageSize) {
        int startIndex = pageNumber * pageSize;
        int endIndex = Math.min(startIndex + pageSize, arrayList.size());
        List<announcement> sublist = arrayList.subList(startIndex, endIndex);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return new PageImpl<>(sublist, pageable, arrayList.size());
    }
}
