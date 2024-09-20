package com.example.demo.resource;

import com.example.demo.model.dto.ClientDTO;
import com.example.demo.service.rest.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/client/")
@RequiredArgsConstructor
public class ClientResource {

    private final ClientService clientService;

    @GetMapping(value = "get-all")
    public ResponseEntity<List<ClientDTO>> getClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }
}
