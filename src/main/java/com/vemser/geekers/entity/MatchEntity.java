package com.vemser.geekers.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vemser.geekers.enums.TipoAtivo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "match")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MATCH_SEQ")
    @SequenceGenerator(name = "MATCH_SEQ", sequenceName = "seq_match", allocationSize = 1)
    @Column(name = "id_match")
    private Integer idMatch;
    @Column(name = "id_usuario", insertable = false, updatable = false)
    private Integer idUsuario;
    @Column(name = "id_usuario_main")
    private Integer usuarioMain;
    @Column(name = "ativo")
    private TipoAtivo ativo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioEntity usuario;
}
