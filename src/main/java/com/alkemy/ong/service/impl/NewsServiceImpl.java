package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.NewsResponseDto;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.dto.response.UpdateNewsDTO;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.PaginationSizeOutOfBoundsException;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.INewsService;
import com.alkemy.ong.util.PaginationUtil;
import com.alkemy.ong.util.pagination.Pagination;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import javax.transaction.Transactional;
import java.util.Optional;
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
    @Transactional
    public NewsResponseDto createNews(NewsDto newsDto) {
        News news = newsMapper.toEntity(newsDto);
        news.setCategory(findCategoryById(newsDto.getCategoryId()));
        news = newsRepository.save(news);
        return newsMapper.toNewsResponseDto(news);
    }

    @Override
    public void findById(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isEmpty()) throw new EntityNotFoundException("News", "id", id);
    }
    @Override
    public Pagination<NewsResponseDto> getAllPages(Pageable pageable, Integer page) {

        Page<NewsResponseDto> listNews = newsRepository.findAll(pageable).map(newsMapper::toNewsResponseDto);
        Pagination<NewsResponseDto> pagination = new Pagination<>();

        pagination.setPages(listNews.getTotalPages());
        pagination.setCurrentPage(PaginationUtil.resolvePageNumber(page));
        pagination.setList(listNews);
        pagination.setUrls(PaginationUtil.getPreviousAndNextPage(pagination.getCurrentPage(),pagination.getPages(),"news"));

        if (pagination.getCurrentPage() > pagination.getPages()) {
            throw new PaginationSizeOutOfBoundsException(
                    message.getMessage("error.pagination_size", null, Locale.US)
            );
        }
        return pagination;
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

    private Category findCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category", "categoryId", categoryId)
        );
        return category;
    }

}
