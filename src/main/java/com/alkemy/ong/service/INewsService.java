package com.alkemy.ong.service;


import com.alkemy.ong.dto.NewsDto;

public interface INewsService {
    NewsDto getById(Long id);
}
