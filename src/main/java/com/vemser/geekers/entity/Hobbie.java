package com.vemser.geekers.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hobbie {

    private Integer idHobbies;
    @NotBlank
    private String descricao;
    @NotBlank
    private String tipoHobbie;

    private Usuario usuario;


    @Override
    public String toString() {
        return "ID_Hobbies : " + idHobbies + "\n"
                + " Descricao : " + descricao;
    }
}
