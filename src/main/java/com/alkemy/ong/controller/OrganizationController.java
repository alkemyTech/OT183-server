package com.alkemy.ong.controller;

import com.alkemy.ong.model.Organization;
import com.alkemy.ong.service.impl.OrganizationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("organization")
public class OrganizationController {

    private final OrganizationServiceImpl service;

    @GetMapping("/public")
    public ResponseEntity<Object> getOrganizationPublicData() {
        return ResponseEntity.ok(service.getOrganizationPublicData());
    }


    @PostMapping("/public")
    ResponseEntity<HttpStatus> updateOrganizationData (@RequestBody Organization organization, @PathVariable Long organizationId){
        this.service.update(organization, organizationId);
        return (ResponseEntity<HttpStatus>) ResponseEntity.ok();
    }
}
