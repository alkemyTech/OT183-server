package com.alkemy.ong.util.pagination;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;


@AllArgsConstructor
@Component
public class PagUtil {

    private final MessageSource messageSource;

    public Integer setCurrentPage(Integer page,Integer totalPages){
        if (page == null || page < 0){
            return 0;
        }

        return page;
    }

    public String nextPage(Integer currentPage,Integer totalPages){

        return currentPage < totalPages - 1 ? "http://localhost:8080/testimonials?page=" + (currentPage + 1) : "This is the last page.";

    }

    public String previousPage(Integer currentPage){

        return currentPage == 0 ? "This is the first page." : "http://localhost:8080/testimonials?page=" + (currentPage - 1);
    }
}
