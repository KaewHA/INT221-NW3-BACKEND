package com.example.project.Service;

import com.example.project.Entity.count;
import com.example.project.repo.countrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class countservice {
    @Autowired
    private countrepo repo;


    public Optional<count> findCount(int id){
        return Optional.ofNullable(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(" count Announcement id :" + id + " does not exist !!!")));
    }

    public Optional<count> updateCount(int id){
        count mycount = findCount(id).get();
        mycount.setCount(mycount.getCount()+1);
        return Optional.of(repo.saveAndFlush(mycount));
    }

    public Optional<count> initCount(int id){
        count countinit = new count(id,0);
        return Optional.of(repo.saveAndFlush(countinit));
    }

}
