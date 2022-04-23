package com.alkemy.ong.controller;

import com.alkemy.ong.service.impl.OrganizationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("organization")
public class OrganizationController {

    private final OrganizationServiceImpl service;

    @GetMapping("/public")
    public ResponseEntity<Object> getOrganizationPublicData() {
        return ResponseEntity.ok(service.getOrganizationPublicData());
    }

}
