package com.vemser.geekers.entity;

public class Hobbie {

    private Integer idHobbies;
    private String descricao;

    private String tipoHobbie;

//    private Usuario usuario;


    public Integer getIdHobbies() {
        return idHobbies;
    }

    public void setIdHobbies(Integer idHobbies) {
        this.idHobbies = idHobbies;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoHobbie() {
        return tipoHobbie;
    }

    public void setTipoHobbie(String tipoHobbie) {
        this.tipoHobbie = tipoHobbie;
    }

//    public Usuario getUsuario() {
//       return usuario;
//    }

//    public void setUsuario(Usuario usuario) {
//       this.usuario = usuario;
//    }

    @Override
    public String toString() {
        return "ID_Hobbies : " + idHobbies + "\n"
                +" Descricao : " + descricao;
    }
}
