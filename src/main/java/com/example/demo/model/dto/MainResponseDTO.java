package com.example.demo.model.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainResponseDTO {
    private static final int RESPONSE_200 = HttpStatus.OK.value();

    private int responseCode;
    private String description;

    public static MainResponseDTO getOK(String description) {
        MainResponseDTO mainResponseDTO = new MainResponseDTO();
        mainResponseDTO.setResponseCode(RESPONSE_200);
        mainResponseDTO.setDescription(description);
        return mainResponseDTO;
    }
}
