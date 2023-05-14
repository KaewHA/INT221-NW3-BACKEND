package com.example.project.DTO;

import com.example.project.validation.ValidDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
@Validated
@Getter@Setter

public class createanno {
    @JsonIgnore
    @ValidDate(value = "2" ,message = "publicdate or codedate is is part")
    private  boolean getcloseDateError(){
        if (this.closeDate == null || this.publishDate== null) {
            return true;
        }
        return this.closeDate.isAfter(this.publishDate);
    }
    @NotNull(message = "must not be blank")@NotBlank(message = "must not be blank") @Size(min = 1,max = 200,message = "size must be between 1 and 200")
    private  String announcementTitle;
    @NotNull(message = "must not be blank")@NotBlank(message = "must not be blank") @Size(min = 1,max = 10000,message = "size must be between 1 and 10000")
    private  String announcementDescription;
    @Future(message = "must be a date in the present or in the future")
    private ZonedDateTime publishDate;

    @Future(message = "must be a future date")
    private ZonedDateTime closeDate;

    @Min(value = 1,message = "must not be null")@Max(value = 4 ,message = "does not exists")
    private  int categoryId;

    @Pattern(regexp = "[YN]", message = "must be either 'Y' or 'N'")
    private  String announcementDisplay;


}
