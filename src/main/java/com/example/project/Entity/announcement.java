package com.example.project.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Setter
@Getter
@Entity
@Table(name = "Announcements")
public class  announcement  {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "announcementID" ,nullable = false)
    private int announcementID;
    @Column(name = "announcementTitle" ,nullable = false)
    private  String announcementTitle;
    @Column(name = "announcementDescription" ,nullable = false)
    private  String announcementDescription;

    @Column(name = "publishDate" ,nullable = true)
    private  ZonedDateTime publishDate;
    @Column(name = "closeDate" ,nullable = true)
    private ZonedDateTime closeDate;
   // @JsonIgnore
    @ManyToOne
    @JoinColumn(name="CategoryID", nullable=false)
    private Category category;
    @Column(name = "announcementDisplay" ,nullable = false)
    private  String announcementDisplay;
}

