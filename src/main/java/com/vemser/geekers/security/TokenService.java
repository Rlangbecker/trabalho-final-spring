package com.vemser.geekers.security;

import com.vemser.geekers.entity.CargoEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoEmail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor

public class TokenService {

    private static final String CHAVE_CARGOS = "CARGOS";
    @Value("${jwt.secret}")
    private String secret;

    public String getToken(UsuarioEntity usuarioEntity) {
        LocalDateTime dataAtualLD = LocalDateTime.now();
        Date dataAtual = Date.from(dataAtualLD.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime dataExpLD = dataAtualLD.plusDays(1);
        Date dataExp = Date.from(dataExpLD.atZone(ZoneId.systemDefault()).toInstant());

        List<String> cargosDoUsuario = usuarioEntity.getCargos().stream()
                .map(CargoEntity::getAuthority)
                .toList();

        return Jwts.builder()
                .setIssuer("vemser-api")
                .claim(Claims.ID, usuarioEntity.getIdUsuario().toString())
                .claim(CHAVE_CARGOS, cargosDoUsuario)
                .setIssuedAt(dataAtual)
                .setExpiration(dataExp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

//    public String getToken(UsuarioEntity usuarioEntity) {
//        LocalDateTime dataAtualLD = LocalDateTime.now();
//        Date dataAtual = Date.from(dataAtualLD.atZone(ZoneId.systemDefault()).toInstant());
//        LocalDateTime dataExpLD = dataAtualLD.plusDays(1);
//        Date dataExp = Date.from(dataExpLD.atZone(ZoneId.systemDefault()).toInstant());
//        List<String> cargosDoUsuario = usuarioEntity.getCargos().stream()
//                .map(CargoEntity::getAuthority)
//                .toList();
//        if(TipoEmail.TROCA_SENHA) {
//            dataExpLD = dataAtualLD.plusMinutes(10);
//            dataExp = Date.from(dataExpLD.atZone(ZoneId.systemDefault()).toInstant());
//            return GetTokenSenha(usuarioEntity, cargosDoUsuario, dataAtual, dataExp);
//        }
//       return GetTokenSenha(usuarioEntity, cargosDoUsuario, dataAtual, dataExp);
//
//    }
//
//    public String GetTokenSenha(UsuarioEntity usuarioEntity, List<String> listaCargos,Date dataAtual,Date dataExpTokenSenha){
//        return Jwts.builder()
//                .setIssuer("vemser-api")
//                .claim(Claims.ID, usuarioEntity.getIdUsuario().toString())
//                .claim(CHAVE_CARGOS, listaCargos)
//                .setIssuedAt(dataAtual)
//                .setExpiration(dataExpTokenSenha)
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
//    }

    public UsernamePasswordAuthenticationToken isValid(String token) {

        if(token == null) {
            return null;
        }
        token = token.replace("Bearer ", ""); // token, example: dXZfbFgEH=

        Claims keys = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();


        String idLoginUsuario = keys.get(Claims.ID, String.class);
        List<String> cargos = keys.get(CHAVE_CARGOS, List.class);

        List<SimpleGrantedAuthority> listaDeCargos = cargos.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        UsernamePasswordAuthenticationToken dtoSecurityObject =
                new UsernamePasswordAuthenticationToken(idLoginUsuario,
                        null,
                        listaDeCargos);

        return dtoSecurityObject;
    }
}
