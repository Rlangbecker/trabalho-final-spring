package com.vemser.geekers.service;


//import com.vemser.geekers.exceptions.BancoDeDadosException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.HobbieCreateDTO;
import com.vemser.geekers.dto.HobbieDTO;
import com.vemser.geekers.entity.Hobbie;
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

    // create - remover - editar - listar(ListHobbieByIdUsuario)

    public HobbieDTO create(Integer idUsuario,HobbieCreateDTO hobbieCreateDTO) throws RegraDeNegocioException {
        Hobbie hobbieEntity = objectMapper.convertValue(hobbieCreateDTO, Hobbie.class);

        //verificar se usuario existe

        hobbieRepository.create(hobbieEntity);

        HobbieDTO hDTO = objectMapper.convertValue(hobbieRepository.create(hobbieEntity), HobbieDTO.class);
        return hDTO;
    }

    public void remover(Integer id) throws BancoDeDadosException {
       hobbieRepository.remover(id);
    }

    public Hobbie editar(Integer id, HobbieCreateDTO hobbieCreateDTO) throws RegraDeNegocioException {
            Hobbie hobbieEntity = objectMapper.convertValue(hobbieCreateDTO, Hobbie.class);
            listByIdUsuario(id);
            hobbieRepository.editar(id, hobbieEntity);
            return hobbieEntity;
    }

    public List<HobbieDTO> list() throws RegraDeNegocioException, BancoDeDadosException {
        return hobbieRepository.listar().stream()
                .map(hobbie -> objectMapper.convertValue(hobbie, HobbieDTO.class))
                .toList();
    }

    public List<Hobbie> listByIdUsuario(Integer id) throws RegraDeNegocioException {
            List<Hobbie> hobbieList = hobbieRepository.listHobbieByIdUsuario(id);
            return hobbieList;
    }

}