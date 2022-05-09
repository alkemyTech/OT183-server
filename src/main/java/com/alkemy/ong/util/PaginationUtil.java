package com.alkemy.ong.util;

import com.alkemy.ong.dto.PaginationUrlDto;

public class PaginationUtil {

    public static int resolvePageNumber(Integer page) {
        if (page == null) return 0;
        if (page < 1) return 0;
        return page - 1;
    }

    public static PaginationUrlDto getPreviousAndNextPage(int page, int maximumPageNumber) {
        if (page == 0 && maximumPageNumber == 0) {
            return new PaginationUrlDto(getPaginationUrl(1), getPaginationUrl(1));
        }
        if (page == 0) {
            return new PaginationUrlDto(getPaginationUrl(1), getPaginationUrl(2));
        }
        if(page == maximumPageNumber) {
            return new PaginationUrlDto(
                    getPaginationUrl(maximumPageNumber),
                    getPaginationUrl(maximumPageNumber + 1)
            );
        }
        return new PaginationUrlDto(getPaginationUrl(page), getPaginationUrl(page + 2));
    }

    public static String getPaginationUrl(int page) {
        return "http://localhost:8080/categories?page=" + page;
    }

}
