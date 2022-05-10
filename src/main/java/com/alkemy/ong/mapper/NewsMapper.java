package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsResponseDto;
import com.alkemy.ong.model.News;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class NewsMapper {
    public NewsDto entityToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setName(news.getName());
        newsDto.setContent(news.getContent());
        newsDto.setImage(news.getImage());
        newsDto.setCategoryId(news.getCategoryId());
        newsDto.setCreatedDate(news.getCreatedDate());
        newsDto.setUpdatedDate(news.getUpdatedDate());
        return newsDto;
    }

    public News toEntity(NewsDto newsDto){
        News news = new News();
        news.setName(newsDto.getName());
        news.setContent(newsDto.getContent());
        news.setImage(newsDto.getImage());
        news.setCategoryId(newsDto.getCategoryId());
        return news;
    }

    public NewsResponseDto toNewsResponseDto(News news){
        return new NewsResponseDto(
                news.getName(),
                news.getContent(),
                news.getImage(),
                news.getCategoryId()
        );
    }

    public List<Object> newsListModel2ListDto(List<News> listNews) {
        List<Object> listDto = new ArrayList<>();
        for(News model : listNews){
            listDto.add(toNewsResponseDto(model));
        }
        return listDto;
    }
}
