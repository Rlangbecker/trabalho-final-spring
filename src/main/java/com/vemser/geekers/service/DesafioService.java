package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.DesafioCreateDTO;
import com.vemser.geekers.dto.DesafioDTO;
import com.vemser.geekers.dto.LoginWithIdDTO;
import com.vemser.geekers.entity.DesafioEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.DesafioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DesafioService {

    private final DesafioRepository desafioRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final UsuarioLoginService usuarioLoginService;

    //TODO Ajustar esse metodo que esta com erro
    public DesafioDTO create(DesafioCreateDTO desafioCreateDTO) throws RegraDeNegocioException {
        try {
            LoginWithIdDTO login = usuarioLoginService.getLoggedUser();
            UsuarioEntity usuario = usuarioService.findById(login.getIdUsuario());
            DesafioEntity desafio = objectMapper.convertValue(desafioCreateDTO, DesafioEntity.class);
            desafio.setUsuario(usuario);
            usuario.setIdUsuario(usuario.getIdUsuario());

            desafioRepository.save(desafio);

            DesafioDTO desafioDTO = objectMapper.convertValue(desafio, DesafioDTO.class);
            desafioDTO.setIdUsuario(usuario.getIdUsuario());
            return desafioDTO;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<DesafioDTO> list(){
        List<DesafioEntity> all = desafioRepository.findAll();
        return all.stream()
                .map(desafio -> {
                    DesafioDTO desafioDTO = objectMapper.convertValue(desafio, DesafioDTO.class);
                    desafioDTO.setIdUsuario(desafio.getUsuario().getIdUsuario());
                    return desafioDTO;
                })
                .toList();
    }

    public DesafioDTO findByUsuario(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioService.findById(id);
        DesafioEntity desafioEntity = desafioRepository.findDesafioEntityByUsuario(usuarioEntity);
        DesafioDTO desafioDTO = objectMapper.convertValue(desafioEntity, DesafioDTO.class);
        desafioDTO.setIdUsuario(desafioEntity.getUsuario().getIdUsuario());
        return desafioDTO;
    }

    public DesafioDTO findById(Integer id) throws RegraDeNegocioException {
        DesafioEntity desafio = desafioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Desafio não encontrado!"));
        DesafioDTO desafioDTO = objectMapper.convertValue(desafio, DesafioDTO.class);
        desafioDTO.setIdUsuario(desafio.getUsuario().getIdUsuario());
        return desafioDTO;
    }

    public DesafioDTO edit(DesafioCreateDTO desafioCreateDTO) throws RegraDeNegocioException {
        LoginWithIdDTO login = usuarioLoginService.getLoggedUser();
        if (login.getIdUsuario() != desafioCreateDTO.getIdUsuario()) {
            throw new RegraDeNegocioException("Este desafio não pertence ao seu usuário!");
        }
        DesafioDTO desafio = findByUsuario(login.getIdUsuario());
        DesafioEntity desafioEntity = objectMapper.convertValue(desafio, DesafioEntity.class);
        desafioEntity.setPergunta(desafioCreateDTO.getPergunta());
        desafioEntity.setResposta(desafioCreateDTO.getResposta());

        desafioRepository.save(desafioEntity);

        return objectMapper.convertValue(desafioEntity, DesafioDTO.class);
    }

    public void delete() throws RegraDeNegocioException{
        LoginWithIdDTO login = usuarioLoginService.getLoggedUser();
        DesafioDTO desafio = findByUsuario(login.getIdUsuario());
        DesafioEntity desafioEntity = objectMapper.convertValue(desafio, DesafioEntity.class);
        desafioRepository.delete(desafioEntity);
    }


}
