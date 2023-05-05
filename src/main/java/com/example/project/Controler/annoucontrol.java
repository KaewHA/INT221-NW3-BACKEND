package com.example.project.Controler;
import com.example.project.DTO.announcementdetail;
import com.example.project.DTO.annowithdetail;
import com.example.project.DTO.createanno;
import com.example.project.DTO.createreturn;
import com.example.project.Entity.Category;
import com.example.project.Entity.announcement;
import com.example.project.Service.announservice;
import com.example.project.Service.cateservice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping("/full")
    public List<announcement> getall(){
        return service.getall();
    }
    @GetMapping("")
    public List<announcementdetail> getalldetail(){
        List<announcement> myanno=service.getall();
        return myanno.stream().map(e -> modelMapper.map( e, announcementdetail.class)).collect(Collectors.toList());
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
        System.out.println(id);
        service.removeannocemment(id);
    }
    @PutMapping("/{id}")
    public Optional<announcement> updateAnnouncement(@RequestBody announcement anno, @PathVariable int id) {
          return service.updateannouncement(id, anno);
    }
    @PostMapping("/add")
    public Optional<createreturn> createAnnouncement(@RequestBody createanno anno) {
        Optional<Category> cate = cateservice.getcategoryByid(anno.getCategoryId());
        announcement myanno = modelMapper.map(anno,announcement.class);
        myanno.setCategory(cate.get());
        return service.addannouncement(myanno).map(e -> modelMapper.map( e, createreturn.class));
    }
}
