package com.vemser.geekers.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "comentario")
public class ComentarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMENTARIO_SEQ")
    @SequenceGenerator(name = "COMENTARIO_SEQ", sequenceName = "SEQ_COMENTARIO", allocationSize = 1)
    @Column(name = "ID_COMENTARIO")
    private Integer idComentario;

    @Column(name = "comentario")
    private String comentario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private UsuarioEntity usuario;

}
