package com.vemser.geekers.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vemser.geekers.dto.ComentarioCreateDTO;
import com.vemser.geekers.repository.ComentarioRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ComentarioServiceTest {

    @InjectMocks // clase principal de testes.
    private ComentarioService comentarioService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EmailService emailService;

    @Mock
    private ComentarioRepository comentarioRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(comentarioService, "objectMapper", objectMapper);
    }

    private static ComentarioCreateDTO getComentarioCreateDTO() {
        ComentarioCreateDTO comentarioCreateDTO = new ComentarioCreateDTO();
        comentarioCreateDTO.setComentario("eu também gosto de dragon ball!");
        comentarioCreateDTO.setIdUsuario()

    }


}
