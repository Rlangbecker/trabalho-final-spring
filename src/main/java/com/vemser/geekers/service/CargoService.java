package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.CargoCreateDTO;
import com.vemser.geekers.dto.CargoDTO;
import com.vemser.geekers.entity.CargoEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;
    private final CargoRepository cargoRepository;

    public CargoDTO create(Integer idUsuario, CargoCreateDTO cargoCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuario = usuarioService.findById(idUsuario);
        CargoEntity cargoEntity = objectMapper.convertValue(cargoCreateDTO, CargoEntity.class);

        cargoEntity.setUsuarios(Set.of(usuario));
        usuario.setCargos(Set.of(cargoEntity));
        cargoRepository.save(cargoEntity);
        CargoDTO cargoDTO = objectMapper.convertValue(cargoEntity, CargoDTO.class);
        return cargoDTO;
    }

    public CargoEntity findById(Integer id) throws RegraDeNegocioException{
        return cargoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cargo não encontrado."));
    }
}
