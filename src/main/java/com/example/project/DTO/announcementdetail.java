package com.example.project.DTO;

import com.example.project.Entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class announcementdetail {

    private int announcementID;
    private  String announcementName;
    private  String announcementDes;
    private  String publishDate;

    private  String closeDate;
    @JsonIgnore
    private Category category;

    public String getcategoryName() {return category.getCategoryName();}
    private  String annonuncementDisplay;
}
