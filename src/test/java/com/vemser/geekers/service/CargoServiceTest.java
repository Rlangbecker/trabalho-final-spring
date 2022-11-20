package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vemser.geekers.dto.CargoCreateDTO;
import com.vemser.geekers.dto.CargoDTO;
import com.vemser.geekers.dto.LoginWithIdDTO;
import com.vemser.geekers.entity.CargoEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.CargoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CargoServiceTest {

    @InjectMocks
    private CargoService cargoService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UsuarioService usuarioService;
    @Mock
    private CargoRepository cargoRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(cargoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id = 1;
        CargoEntity cargoEntity = getCargoEntity();
        CargoCreateDTO cargoCreateDTO = getCargoCreateDTO();
        when(usuarioService.findById(getLogin().getIdUsuario())).thenReturn(getUsuarioEntity());
        when(cargoRepository.save(any())).thenReturn(cargoEntity);
        // ACT
        CargoDTO cargoDTO = cargoService.create(id, cargoCreateDTO);

        // ASSERT
        assertNotNull(cargoDTO);
        assertEquals("ADMIN", cargoDTO.getNome());
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        Integer busca = 1;
        CargoEntity cargoEntity = getCargoEntity();
        when(cargoRepository.findById(anyInt())).thenReturn(Optional.of(cargoEntity));

        CargoEntity cargo = cargoService.findById(busca);

        assertNotNull(cargo);
        assertNotNull(cargo.getIdCargo());
        assertEquals(1,cargo.getIdCargo());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(cargoRepository.findById(anyInt())).thenReturn(Optional.empty());


        // Ação (ACT)
        CargoEntity cargoEntity = cargoService.findById(busca);
        //ASSERT
        assertNull(cargoEntity);
    }



    private static CargoEntity getCargoEntity(){
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setIdCargo(1);
        cargoEntity.setNome("ADMIN");
        return cargoEntity;
    }

    private static UsuarioEntity getUsuarioEntity(){
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setAtivo(TipoAtivo.ATIVO);
        usuarioEntity.setNome("Kaio");
        usuarioEntity.setEmail("kaio@gmail.com");
        usuarioEntity.setLogin("kaio");
        usuarioEntity.setSenha("123");
        usuarioEntity.setSexo("M");
        return usuarioEntity;
    }

    private static CargoCreateDTO getCargoCreateDTO(){
        CargoCreateDTO cargoCreateDTO = new CargoCreateDTO();
        cargoCreateDTO.setNome("ADMIN");
        return cargoCreateDTO;

    }
    private static LoginWithIdDTO getLogin(){
        LoginWithIdDTO login = new LoginWithIdDTO();
        login.setIdUsuario(1);
        login.setNome("Kaio");
        login.setEmail("kaio@gmail.com");
        login.setLogin("kaio");
        login.setSenha("123");
        return login;
    }
}
