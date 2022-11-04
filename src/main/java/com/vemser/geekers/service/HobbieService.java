package com.vemser.geekers.service;


//import com.vemser.geekers.exceptions.BancoDeDadosException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.HobbieCreateDTO;
import com.vemser.geekers.dto.HobbieDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.entity.HobbieEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.HobbieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HobbieService {

    private final HobbieRepository hobbieRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;

    // create - remover - editar - listar(ListHobbieByIdUsuario)

    public HobbieDTO create(Integer idUsuario, HobbieCreateDTO hobbieCreateDTO) throws RegraDeNegocioException {

        HobbieEntity hobbieEntity = objectMapper.convertValue(hobbieCreateDTO, HobbieEntity.class);

        UsuarioEntity usuario =usuarioService.findById(idUsuario);
        usuario.setIdUsuario(idUsuario);
        hobbieEntity.setUsuario(usuario);

        hobbieRepository.save(hobbieEntity);

        HobbieDTO hDTO = objectMapper.convertValue(hobbieEntity, HobbieDTO.class);

        hDTO.setIdHobbie(hobbieEntity.getIdHobbie());
        hDTO.setIdUsuario(hobbieEntity.getUsuario().getIdUsuario());
        return hDTO;
    }

    public void remover(Integer id) throws RegraDeNegocioException {
       HobbieEntity hobbie = objectMapper.convertValue(findHobbieById(id),HobbieEntity.class);
        hobbieRepository.delete(hobbie);
    }

    public HobbieDTO editar(Integer idHobbie, HobbieCreateDTO hobbieCreateDTO) throws RegraDeNegocioException {
        HobbieEntity hobbieEntity = objectMapper.convertValue(hobbieCreateDTO, HobbieEntity.class);
        listByIdUsuario(idHobbie);

        hobbieEntity.setIdHobbie(idHobbie);
        hobbieEntity.setDescricao(hobbieCreateDTO.getDescricao());
        hobbieEntity.setTipoHobbie(hobbieEntity.getTipoHobbie());

        return objectMapper.convertValue(hobbieEntity, HobbieDTO.class);
    }

    public HobbieDTO findHobbieById(Integer id) {
        HobbieDTO hDTO = objectMapper.convertValue(hobbieRepository.findHobbieEntityByIdHobbie(id),HobbieDTO.class);

        return hDTO;
    }

    public HobbieDTO listByIdUsuario(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuario=usuarioService.findById(id);

        return objectMapper.convertValue(hobbieRepository.findHobbieEntityByUsuario(usuario),HobbieDTO.class);
    }

}