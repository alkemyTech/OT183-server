package com.alkemy.ong.service;


import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.dto.response.UpdateNewsDTO;

public interface INewsService {
    NewsDto getById(Long id);
    UpdateNewsDTO updateNews(Long id, NewsUpdateDTO newsUpdate);
    void deleteNews(Long id);
    NewsDto createNews(NewsDto newsDto);
}
