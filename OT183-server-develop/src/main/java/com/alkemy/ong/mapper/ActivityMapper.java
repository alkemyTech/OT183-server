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
}
