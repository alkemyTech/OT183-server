package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.model.News;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {
    public NewsDto entityToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setName(news.getName());
        newsDto.setContent(news.getContent());
        newsDto.setImage(news.getImage());
        newsDto.setCategory(news.getCategory().getName());
        newsDto.setCreatedDate(news.getCreatedDate());
        newsDto.setUpdatedDate(news.getUpdatedDate());
        return newsDto;
    }
}
