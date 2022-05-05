package com.alkemy.ong.util;

public class PaginationUtil {

    public static int resolvePageNumber(Integer page) {
        if (page == null) return 0;
        if (page < 1) return 0;
        return page - 1;
    }

}
