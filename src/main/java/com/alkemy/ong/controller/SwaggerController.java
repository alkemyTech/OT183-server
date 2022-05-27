package com.alkemy.ong.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/docs")
@ApiIgnore
public class SwaggerController {

    @GetMapping
    public ModelAndView swaggerPage(HttpServletResponse response) {
        ModelAndView  model = new ModelAndView("redirect:/swagger-ui.html");
        return model;
    }
}

