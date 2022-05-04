package com.alkemy.ong.service;

import com.alkemy.ong.dto.ActivityDto;

public interface IActivityService{

    ActivityDto createActivity(ActivityDto activityDto);

    ActivityDto updateActivity(Long id,ActivityDto dto);
}
