package com.example.project.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
public class Category {
@Id
@Column(name = "categoryID" ,nullable = false)
@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int categoryID;
@Column(name = "categoryName" ,nullable = false)
   private  String categoryName;

}
