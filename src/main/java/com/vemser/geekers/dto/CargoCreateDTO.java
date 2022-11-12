package com.vemser.geekers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CargoCreateDTO {
    @NotBlank
    private String nome ;

}
