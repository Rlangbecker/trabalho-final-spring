package com.vemser.geekers.entity;

public class Comentario {

    private Integer idComentario;
    private String comentario;
//    private Usuario usuario;

    public Integer getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Integer idComentario) {
        this.idComentario = idComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

//    public Usuario getUsuario() {
//        return usuario;
//    }
//
//    public void setUsuario(Usuario usuario) {
//        this.usuario = usuario;
//    }

    @Override
    public String toString() {
        return "ID " + idComentario +
//                "\nUsuario: " + usuario.getNome() +
                " Comentario: " + comentario;
    }

}
