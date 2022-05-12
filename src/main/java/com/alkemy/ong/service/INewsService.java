package com.alkemy.ong.service;


import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsResponseDto;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.dto.response.UpdateNewsDTO;
import com.alkemy.ong.util.pagination.Pagination;
import org.springframework.data.domain.Pageable;

public interface INewsService {
    NewsDto getById(Long id);
    UpdateNewsDTO updateNews(Long id, NewsUpdateDTO newsUpdate);
    void deleteNews(Long id);
    NewsResponseDto createNews(NewsDto newsDto);

    Pagination<NewsResponseDto> getAllPages(Pageable pageable, Integer page);
    void findById(Long id);
}
