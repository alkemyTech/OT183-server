package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.model.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public Activity dtoToEntity(ActivityDto activityDto) {
        Activity activity = new Activity();
        activity.setName(activityDto.getName());
        activity.setContent(activityDto.getContent());
        activity.setImage(activityDto.getImage());
        return activity;
    }

    public ActivityDto Entity2Dto(Activity model){
        ActivityDto dto = new ActivityDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setContent(model.getContent());
        dto.setImage(model.getImage());

        return dto;
    }

    public Activity updateActivity(Activity model,ActivityDto dto){

        model.setName(dto.getName());
        model.setContent(dto.getContent());
        model.setImage(dto.getImage());

        return model;
    }
}
