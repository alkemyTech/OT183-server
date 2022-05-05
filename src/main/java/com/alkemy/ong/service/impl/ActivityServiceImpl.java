package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Locale;

@Service
public class ActivityServiceImpl implements IActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private MessageSource messageSource;

    @Override
    public ActivityDto createActivity(ActivityDto activityDto) {
        Activity activity = activityRepository.save(activityMapper.dtoToEntity(activityDto));
        activityDto.setId(activity.getId());
        return activityDto;
    }

    @Transactional
    public ActivityDto updateActivity(Long id,ActivityDto dto){


        Activity activity = activityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Activity" ,"id" ,id));

        activity = activityMapper.updateActivity(activity,dto);


        return activityMapper.Entity2Dto(activity);
    }

}
