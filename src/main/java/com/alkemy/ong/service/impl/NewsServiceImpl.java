package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.NullModelException;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
public class NewsServiceImpl implements INewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private  MessageSource message;


    @Override
    public NewsDto getById(Long id) {
        News news = newsRepository.getById(id);
        return newsMapper.entityToDto(news);
    }

    public void deleteNews(Long id){

        if (!newsRepository.existsById(id)){
            throw new EntityNotFoundException("News", "id", id);
        }

        newsRepository.deleteById(id);

    }
}
