package com.alkemy.ong.util.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination <T> {

    private Integer pages;

    private Integer currentPage;

    private String previousPage;

    private String nextPage;

    private Page<T> list;
}
