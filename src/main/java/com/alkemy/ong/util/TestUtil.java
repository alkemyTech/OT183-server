package com.alkemy.ong.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtil {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
