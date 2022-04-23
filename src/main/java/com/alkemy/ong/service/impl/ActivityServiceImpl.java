package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements IActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityMapper activityMapper;
    @Override
    public ActivityDto createActivity(ActivityDto activityDto) {
        Activity activity = activityRepository.save(activityMapper.dtoToEntity(activityDto));
        activityDto.setId(activity.getId());
        return activityDto;
    }
}
