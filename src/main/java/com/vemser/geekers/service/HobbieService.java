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

    public HobbieDTO create(HobbieCreateDTO hobbieCreateDTO) throws RegraDeNegocioException {

        Hobbie hobbieEntity = objectMapper.convertValue(hobbieCreateDTO, Hobbie.class);

        HobbieDTO hDTO = objectMapper.convertValue(hobbieRepository.create(hobbieEntity), HobbieDTO.class);

        return hDTO;
    }

    public void remover(Integer id) {
        try {
            boolean conseguiuRemover = hobbieRepository.remover(id);
            System.out.println("Hobbie removido? " + conseguiuRemover + "| com id= " + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public Hobbie editar(Integer id, HobbieCreateDTO hobbieCreateDTO) {
        try {
            Hobbie hobbieEntity = objectMapper.convertValue(hobbieCreateDTO, Hobbie.class);

            //verificar se existe usuario
            //verificar se existe hobbie


            hobbieRepository.editar(id, hobbieEntity);
            return hobbieEntity;
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
    }

    public List<HobbieDTO> list() throws RegraDeNegocioException {

        return hobbieRepository.list().stream()
                .map(hobbie -> objectMapper.convertValue(hobbie, HobbieDTO.class))
                .toList();

    }

    public List<Hobbie> listByIdUsuario(Integer id) {
        try {
            List<Hobbie> hobbieList = hobbieRepository.listHobbieByIdUsuario(id);
            return hobbieList;
        } catch (RegraDeNegocioException e) {
            e.printStackTrace();
        }
        return null;
    }

}