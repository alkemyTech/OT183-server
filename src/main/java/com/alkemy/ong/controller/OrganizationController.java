package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationSocialAddressesDto;
import com.alkemy.ong.service.impl.OrganizationServiceImpl;
import lombok.AllArgsConstructor;
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

    @PutMapping(path = "/contactSocial/{id}")
    public ResponseEntity<OrganizationSocialAddressesDto> putSocialAddres
            (@PathVariable Long id, @RequestBody OrganizationSocialAddressesDto dto){

        OrganizationSocialAddressesDto result = service.putSocialAddres(id,dto);
        return ResponseEntity.ok().body(result);
    }
}
