package com.vemser.geekers.dto;

import com.vemser.geekers.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DesafioDTO extends DesafioCreateDTO{
    private Integer idDesafio;
}
