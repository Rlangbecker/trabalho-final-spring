package com.vemser.geekers.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;

@Getter
@Setter
public class Usuario {

    private Integer idUsuario;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private LocalDate dataNascimento;
    private String sexo;
    private boolean logado;



    @Override
    public String toString() {
        return "\n+---------------------------------+\n"
                +"Usuario ID:" + idUsuario+"\n"
                +"Nome: " + nome +"\n"+
                "Email: " + email +"\n" +
                "Telefone: " + telefone + "\n" +
                "Data Nascimento:" + dataNascimento + "\n" +
                "Sexo: " + sexo + "\n" +
                "Online: " + logado +"\n"
                +"+---------------------------------+ \n";
    }

}