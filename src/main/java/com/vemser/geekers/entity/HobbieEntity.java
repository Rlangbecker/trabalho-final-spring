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
@Entity(name = "hobbie")
public class HobbieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOBBIE_SEQ")
    @SequenceGenerator(name = "HOBBIE_SEQ", sequenceName = "SEQ_HOBBIE", allocationSize = 1)
    @Column(name = "ID_HOBBIE")
    private Integer idHobbie;

    @Column(name="DESCRICAO")
    private String descricao;

    @Column(name="TIPO_HOBBIE")
    private String tipoHobbie;

    @JsonIgnore
    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="ID_USUARIO",referencedColumnName = "ID_USUARIO")
    private UsuarioEntity usuario;
}
