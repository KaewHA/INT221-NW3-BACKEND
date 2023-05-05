package com.example.project.DTO;

import com.example.project.Entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter@Setter
public class createanno {

    private  String announcementTitle;
    private  String announcementDescription;
    private ZonedDateTime publishDate;

    private ZonedDateTime closeDate;
    private  int categoryId;
    private  String announcementDisplay;
}
