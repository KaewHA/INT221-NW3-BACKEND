package com.example.project.Controler;
import com.example.project.DTO.announcementdetail;
import com.example.project.DTO.annowithdetail;
import com.example.project.DTO.createanno;
import com.example.project.DTO.createreturn;
import com.example.project.Entity.Category;
import com.example.project.Entity.announcement;
import com.example.project.Service.announservice;
import com.example.project.Service.cateservice;
import com.example.project.pagedto.PageDTO;
import com.example.project.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/announcements")
@CrossOrigin
public class annoucontrol {
    @Autowired
    private announservice service;
    @Autowired
    private cateservice cateservice;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;
    @GetMapping("/full")
    public List<announcement> getall(){
        return service.getall();
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
    @GetMapping("")
    public List<announcementdetail> getalldetail(@RequestParam(defaultValue = "admin") String mode){
        if(mode.equals("admin")){
            return service.getall().stream().map(e -> modelMapper.map( e, announcementdetail.class)).collect(Collectors.toList());
        }else if(mode.equals("close")){
            List<announcement>myanno=service.getall();
             return filterclose(myanno).stream().map(e -> modelMapper.map( e, announcementdetail.class)).collect(Collectors.toList());
        }else if(mode.equals("active")){
            List<announcement>myanno=service.getall();
            return filteractive(myanno).stream().map(e -> modelMapper.map( e, announcementdetail.class)).collect(Collectors.toList());
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "CATEGORT CODE OR MODE IS NOT FOUND ");
    }
    @GetMapping("/{id}")
    public Optional<annowithdetail> getID(@PathVariable int id){
        Optional<announcement> announcement= service.getbyid(id);
       return announcement.map(e -> modelMapper.map( e, annowithdetail.class));
    }
    @GetMapping("/{id}/data")
    public Optional<announcement> getIDcate(@PathVariable int id){
        return  service.getbyid(id);
    }

    @DeleteMapping("/{id}")
    public void removeAnnouncement(@PathVariable int id) {
        service.removeannocemment(id);
    }
    @PutMapping("/{id}")
    public Optional<annowithdetail> updateAnnouncement(@RequestBody createanno anno, @PathVariable int id) {
        Optional<Category> cate = cateservice.getcategoryByid(anno.getCategoryId());
        announcement myanno = modelMapper.map(anno,announcement.class);
        myanno.setCategory(cate.get());
          return service.updateannouncement(id,myanno).map(e -> modelMapper.map( e, annowithdetail.class));
    }
    @PostMapping("")
    public Optional<createreturn> createAnnouncement(@RequestBody createanno anno) {
        System.out.println(anno.getAnnouncementDescription().length());
        Optional<Category> cate = cateservice.getcategoryByid(anno.getCategoryId());
        announcement myanno = modelMapper.map(anno,announcement.class);
        myanno.setCategory(cate.get());
        return service.addannouncement(myanno).map(e -> modelMapper.map( e, createreturn.class));
    }
    @GetMapping("/pages")
    public PageDTO<announcementdetail> getAnnouncesPage(
            @RequestParam(defaultValue = "active") String mode,
            @RequestParam(defaultValue = "0") Integer category,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        Page<announcement> mypage=service.getpagenodetail(page, size, category, mode);
    return  listMapper.toPageDTO(mypage,announcementdetail.class,modelMapper);

    }
}
