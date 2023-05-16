package com.example.project.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "count")
@AllArgsConstructor
@NoArgsConstructor
public class count {
    @Id
    @Column(name = "announcementID" ,nullable = false)
    private int announcementID;
    @Column(name = "count" ,nullable = false)
    private  int count;

}
