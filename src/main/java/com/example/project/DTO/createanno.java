package com.example.project.DTO;

import com.example.project.validation.ValidDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
@Validated
@Getter@Setter
public class createanno {
    @NotBlank(message = "title is null") @Size(min = 1,max = 200,message = "title  max 200")
    private  String announcementTitle;
    @NotBlank(message = "Description is null") @Size(min = 1,max = 10000,message = "description max 10000")
    private  String announcementDescription;
    @Future(message = "publish date is part")
    private ZonedDateTime publishDate;

    @Future(message = "closedate is part")
    private ZonedDateTime closeDate;
    private  int categoryId;

    @Pattern(regexp = "[YN]", message = "Invalid value. Only 'Y' or 'N' allowed.")
    private  String announcementDisplay;

    @JsonIgnore
    @ValidDate(message = "close date is part from public date!")
    private  boolean getCloseDateispart(){
        if(this.publishDate==null){
            if(this.closeDate!=null){
                ZonedDateTime x=this.closeDate;
                long cl=x.toEpochSecond();
                long now=ZonedDateTime.now().toEpochSecond();
                if(now>cl){
                    return  false;
                }
            }
        }else {
            if(this.publishDate.toEpochSecond() < ZonedDateTime.now().toEpochSecond()){
                return  false;
            }
            if(this.closeDate!=null){
                ZonedDateTime x=this.closeDate;
                ZonedDateTime y=this.publishDate;
                long cl=x.toEpochSecond();
                long now=ZonedDateTime.now().toEpochSecond();
                long pb=y.toEpochSecond();
                if(now>cl || cl<pb){
                    return  false;
                }
            }
        }
        return true;
    }
}
