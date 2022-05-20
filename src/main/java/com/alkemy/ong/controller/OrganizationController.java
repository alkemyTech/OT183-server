package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDetailedDto;
import com.alkemy.ong.dto.OrganizationSocialAddressesDto;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.service.IOrganizationService;
import com.alkemy.ong.service.impl.OrganizationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("organization")
public class OrganizationController {

    private final IOrganizationService service;

    @GetMapping("/public")
    public ResponseEntity<Object> getOrganizationPublicData() {
        return ResponseEntity.ok(service.getOrganizationPublicData());
    }

    @PutMapping("/public")
    public ResponseEntity<OrganizationDetailedDto> update(@Valid @RequestBody OrganizationDetailedDto dto) {
        OrganizationDetailedDto result = null;
        try {
            result = service.update(dto);
        } catch (Exception e) {
            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.ok().body(result);
        
    }
}
