package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.dto.response.UpdateNewsDTO;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.NullModelException;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.util.Locale;

@AllArgsConstructor
@Service
public class NewsServiceImpl implements INewsService {


    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final CategoryRepository categoryRepository;
    private final  MessageSource message;

    @Override
    public NewsDto getById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("News", "id", id));
        return newsMapper.entityToDto(news);
    }

    public void deleteNews(Long id){

        if (!newsRepository.existsById(id)){
            throw new EntityNotFoundException("News", "id", id);
        }

        newsRepository.deleteById(id);

    }

    @Override
    public UpdateNewsDTO updateNews(Long id, NewsUpdateDTO newsUpdate) {
        News news = newsRepository.getById(id);

        if(news != null){
            news.setName(newsUpdate.getName());
            news.setContent(newsUpdate.getContent());
            news.setImage(newsUpdate.getImage());
        }

        if(newsUpdate.getCategory() != null){
            Category category = categoryRepository.getById(newsUpdate.getCategory());
            if(category != null){
                news.setCategory(category);
            }
        }
        
        newsRepository.save(news);

        UpdateNewsDTO updateDTO= new UpdateNewsDTO();
        updateDTO.setId(news.getId());
        updateDTO.setUrl("/news/"+news.getId());
        return updateDTO;
    }
}
