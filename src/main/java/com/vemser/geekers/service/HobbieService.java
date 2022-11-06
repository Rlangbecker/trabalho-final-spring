package com.vemser.geekers.service;


//import com.vemser.geekers.exceptions.BancoDeDadosException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.HobbieCreateDTO;
import com.vemser.geekers.dto.HobbieDTO;
import com.vemser.geekers.entity.HobbieEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.HobbieRepository;
import com.vemser.geekers.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HobbieService {

    private final HobbieRepository hobbieRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;

    // create - remover - editar - listar(ListHobbieByIdUsuario)

    public HobbieDTO create(Integer idUsuario, HobbieCreateDTO hobbieCreateDTO) throws RegraDeNegocioException {

        HobbieEntity hobbieEntity = objectMapper.convertValue(hobbieCreateDTO, HobbieEntity.class);

        UsuarioEntity usuario =usuarioService.findById(idUsuario);
        hobbieEntity.setUsuario(usuario);
        hobbieEntity.getUsuario().setIdUsuario(idUsuario);

        HobbieDTO hDTO = objectMapper.convertValue( hobbieRepository.save(hobbieEntity), HobbieDTO.class);

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
        listByIdUsuario(hobbieCreateDTO.getIdUsuario());

        hobbieEntity.setIdHobbie(idHobbie);
        hobbieEntity.setDescricao(hobbieCreateDTO.getDescricao());
        hobbieEntity.setTipoHobbie(hobbieEntity.getTipoHobbie());
        hobbieEntity.setUsuario(usuarioService.findById(hobbieCreateDTO.getIdUsuario()));
        hobbieEntity.getUsuario().setIdUsuario(hobbieCreateDTO.getIdUsuario());

        HobbieDTO hobbieDTO = objectMapper.convertValue(hobbieRepository.save(hobbieEntity), HobbieDTO.class);
        hobbieDTO.setIdUsuario(hobbieEntity.getUsuario().getIdUsuario());

        return hobbieDTO;
    }

    public HobbieDTO findHobbieById(Integer id) {
        HobbieEntity hobbieEntity=hobbieRepository.findHobbieEntityByIdHobbie(id);
        HobbieDTO hDTO = objectMapper.convertValue(hobbieEntity,HobbieDTO.class);
        hDTO.setIdUsuario(hobbieEntity.getUsuario().getIdUsuario());

        return hDTO;
    }

    public HobbieDTO listByIdUsuario(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuario=usuarioService.findById(id);
        HobbieEntity hobbie = hobbieRepository.findHobbieEntityByUsuario(usuario);
        HobbieDTO hobbieDTO=objectMapper.convertValue(hobbie, HobbieDTO.class);

        hobbieDTO.setIdUsuario(usuario.getIdUsuario());
        return hobbieDTO;
    }

}