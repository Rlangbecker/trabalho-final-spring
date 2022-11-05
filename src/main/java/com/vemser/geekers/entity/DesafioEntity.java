package com.vemser.geekers.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "desafio")
public class DesafioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESAFIO_SEQ")
    @SequenceGenerator(name = "DESAFIO_SEQ", sequenceName = "seq_desafio", allocationSize = 1)
    @Column(name = "id_desafio")
    private Integer idDesafio;
    @Column(name = "pergunta")
    private String pergunta;
    @Column(name = "resposta")
    private Integer resposta;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioEntity usuario;

}