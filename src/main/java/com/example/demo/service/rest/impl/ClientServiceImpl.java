package com.example.demo.service.rest.impl;

import com.example.demo.model.dto.ClientDTO;
import com.example.demo.model.entity.ClientEntity;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.rest.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<ClientDTO> getAllClients() {
        List<ClientEntity> clientEntities = clientRepository.findAll();
        return clientEntities.stream()
                             .map(e -> modelMapper.map(e, ClientDTO.class))
                             .collect(Collectors.toList());
    }
}
