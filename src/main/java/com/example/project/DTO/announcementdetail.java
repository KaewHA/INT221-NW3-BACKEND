package com.example.project.DTO;

import com.example.project.Entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Getter@Setter
public class announcementdetail {
    private int id;

    private  String announcementTitle;
    private  ZonedDateTime publishDate;

    private ZonedDateTime closeDate;
    @JsonIgnore
    private Category category;

    public String getannouncementCategory() {return category.getCategoryName();}
    private  String announcementDisplay;
    private  int viewCount;
}
