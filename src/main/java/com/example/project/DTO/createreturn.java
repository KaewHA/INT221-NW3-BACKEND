package com.example.project.DTO;

import com.example.project.Entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
@Getter@Setter
public class createreturn {
    private int id;

    private  String announcementTitle;
    private  String announcementDescription;
    private ZonedDateTime publishDate;

    private ZonedDateTime closeDate;
    @JsonIgnore
    private Category category;

    public String getannouncementCategory() {return category.getCategoryName();}

    public int getcategoryId() {return category.getCategoryID();}
    private  String announcementDisplay;
}
