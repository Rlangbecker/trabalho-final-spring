package com.vemser.geekers.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @Column(name = "id_usuario", insertable = false, updatable = false)
    private Integer idUsuario;
    @Column(name = "comentario")
    private String comentario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private UsuarioEntity usuario;

}
