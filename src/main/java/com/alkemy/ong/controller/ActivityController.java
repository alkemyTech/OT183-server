package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.service.IActivityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/activities")
@Api(tags = "Activities")
public class ActivityController {

    @Autowired
    private IActivityService service;

    @PostMapping
    public ResponseEntity<ActivityDto> createTestimonial(@Valid @RequestBody ActivityDto activityDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createActivity(activityDto));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ActivityDto> updateActivity(@Valid @PathVariable Long id, @RequestBody ActivityDto activityDto){

        ActivityDto  updatedActivity = service.updateActivity(id,activityDto);

        return ResponseEntity.ok().body(updatedActivity);
    }
}
