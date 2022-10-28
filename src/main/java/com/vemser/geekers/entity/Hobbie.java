package com.vemser.geekers.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hobbie {

    private Integer idUsuario;

    private Integer idHobbies;

    private String descricao;

    private String tipoHobbie;


    @Override
    public String toString() {
        return "ID_Hobbies : " + idHobbies + "\n"
                + " Descricao : " + descricao;
    }
}
