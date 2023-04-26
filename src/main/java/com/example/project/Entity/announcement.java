package com.example.project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "annonuncemment")
public class  announcement  {
    @Id
    @Column(name = "annonuncementID" ,nullable = false)
    private int announcementID;
    @Column(name = "announcementTitle" ,nullable = false)
    private  String announcementName;
    @Column(name = "announcementDescription" ,nullable = false)
    private  String announcementDes;
    @Column(name = "publishDate" ,nullable = false)
    private  String publishDate;

    @Column(name = "closeDate" ,nullable = false)
    private  String closeDate;
   // @JsonIgnore
    @ManyToOne
    @JoinColumn(name="CategoryID", nullable=false)
    private Category category;


    @Column(name = "annonuncementDisplay" ,nullable = false)
    private  String annonuncementDisplay;
}

