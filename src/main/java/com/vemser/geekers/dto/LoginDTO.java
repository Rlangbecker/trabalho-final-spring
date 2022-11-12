package com.vemser.geekers.dto;
import com.vemser.geekers.enums.TipoAtivo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {
    @NotNull
    @Schema(example = "user")
    private String login;
    @NotNull
    @Schema(example = "123")
    private String senha;
}
