package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vemser.geekers.dto.HobbieCreateDTO;
import com.vemser.geekers.dto.HobbieDTO;
import com.vemser.geekers.dto.LoginWithIdDTO;
import com.vemser.geekers.entity.HobbieEntity;
import com.vemser.geekers.entity.UsuarioEntity;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.exception.RegraDeNegocioException;
import com.vemser.geekers.repository.HobbieRepository;
import com.vemser.geekers.repository.UsuarioRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HobbieServiceTest {

    @InjectMocks
    private HobbieService hobbieService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private HobbieRepository hobbieRepository;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private UsuarioLoginService usuarioLoginService;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(hobbieService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() throws RegraDeNegocioException {
        HobbieEntity hobbieEntity = getHobbieEntity();
        HobbieCreateDTO hobbieCreateDTO = getHobbieCreateDTO();

        when(usuarioLoginService.getLoggedUser()).thenReturn(getLogin());
        when(usuarioService.findById(getLogin().getIdUsuario())).thenReturn(getUsuarioEntity());
        when(hobbieRepository.save(any())).thenReturn(hobbieEntity);

        HobbieDTO hobbieDTO = hobbieService.create(hobbieCreateDTO);


        assertNotNull(hobbieDTO);
        assertEquals("valorant", hobbieDTO.getDescricao());

    }

    @Test
    public void deveTestarFindByIdUsuarioComSucesso() throws RegraDeNegocioException {
        //SETUP
        HobbieDTO hobbieDTO = getHobbieDTO();
        when(usuarioService.findById(anyInt())).thenReturn(getUsuarioEntity());
        when(hobbieRepository.findHobbieEntityByUsuario(any())).thenReturn(getHobbieEntity());

        // ACT
        hobbieService.findByIdUsuario(getUsuarioEntity().getIdUsuario());

        // ASSERT
        assertNotNull(hobbieDTO);
        assertEquals(1, hobbieDTO.getIdUsuario());
    }

    @Test
    public void deveTestarFindHobbieByIdComSucesso() throws RegraDeNegocioException {
        //SETUP
        Integer busca = 1;
        HobbieEntity hobbieEntity = getHobbieEntity();
        when(hobbieRepository.findById(anyInt())).thenReturn(Optional.of(hobbieEntity));

        //ACT
        HobbieEntity hobbie = hobbieService.findHobbieById(busca);
        //ASSERT
        assertNotNull(hobbie);
        assertNotNull(hobbie.getIdHobbie());
        assertEquals(1, hobbie.getIdHobbie());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(hobbieRepository.findById(anyInt())).thenReturn(Optional.empty());


        // Ação (ACT)
        HobbieEntity hobbie = hobbieService.findHobbieById(busca);

        //Assert
        assertNull(hobbie);
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        //SETUP
        HobbieEntity hobbieEntity = getHobbieEntity();
        hobbieEntity.setIdHobbie(10);
        when(usuarioLoginService.getLoggedUser()).thenReturn(getLogin());
        when(usuarioService.findById(getLogin().getIdUsuario())).thenReturn(getUsuarioEntity());
        when(hobbieRepository.findHobbieEntityByUsuario(any())).thenReturn(hobbieEntity);

        //ACT
        hobbieService.remover();
        //ASSERT
        verify(hobbieRepository, times(1)).delete(any());

    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
        //Setup
        HobbieCreateDTO hobbieCreateDTO = getHobbieCreateDTO();
        HobbieEntity hobbieEntity = getHobbieEntity();
        hobbieEntity.setDescricao("Call of duty");
        when(usuarioLoginService.getLoggedUser()).thenReturn(getLogin());
        when(usuarioService.findById(getLogin().getIdUsuario())).thenReturn(getUsuarioEntity());
        when(hobbieRepository.findHobbieEntityByUsuario(any())).thenReturn(hobbieEntity);
        HobbieEntity hobbie = getHobbieEntity();
        when(hobbieRepository.save(any())).thenReturn(hobbie);

        //ACT
        HobbieDTO hobbieDTO = hobbieService.editar(hobbieCreateDTO);

        //Assert
        assertNotNull(hobbieDTO);
        assertNotEquals("Call of duty", hobbieDTO.getDescricao());
    }

    private static HobbieEntity getHobbieEntity(){
        HobbieEntity hobbieEntity = new HobbieEntity();
        hobbieEntity.setIdHobbie(1);
        hobbieEntity.setTipoHobbie("1");
        hobbieEntity.setUsuario(getUsuarioEntity());
        hobbieEntity.setDescricao("valorant");
        return hobbieEntity;
    }

    private static HobbieCreateDTO getHobbieCreateDTO(){
        HobbieCreateDTO hobbieCreateDTO = new HobbieCreateDTO();
        hobbieCreateDTO.setTipoHobbie("1");
        hobbieCreateDTO.setDescricao("valorant");
        hobbieCreateDTO.setIdUsuario(getUsuarioEntity().getIdUsuario());
        return  hobbieCreateDTO;
    }

    private static HobbieDTO getHobbieDTO(){
        HobbieDTO hobbieDTO = new HobbieDTO();
        hobbieDTO.setIdHobbie(1);
        hobbieDTO.setTipoHobbie("1");
        hobbieDTO.setDescricao("valorant");
        hobbieDTO.setIdUsuario(getUsuarioEntity().getIdUsuario());
        return  hobbieDTO;
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
