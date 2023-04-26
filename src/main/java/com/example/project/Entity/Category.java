package com.example.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
public class Category {
@Id
@Column(name = "categoryID" ,nullable = false)
    private int categoryID;
@Column(name = "categoryName" ,nullable = false)
   private  String categoryName;

}
