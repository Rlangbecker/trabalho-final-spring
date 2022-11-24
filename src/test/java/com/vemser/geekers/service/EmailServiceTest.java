package com.vemser.geekers.service;

import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.dto.UsuarioSeguroDTO;
import com.vemser.geekers.enums.TipoAtivo;
import com.vemser.geekers.enums.TipoEmail;
import com.vemser.geekers.service.EmailService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest{

    @InjectMocks
    private EmailService emailService;

    @Value("${spring.mail.username}")
    private String from;

    @Mock
    private freemarker.template.Configuration fmConfiguration;

//    @Test
//    public void deveTestarGetTemplateComSucesso() throws IOException, TemplateException {
//        LocalDate data = LocalDate.of(2002,03,14);
//        Template template = new Template("", Reader.nullReader());
//
//        UsuarioDTO usuarioDTO = new UsuarioDTO();
//        usuarioDTO.setIdUsuario(1);
//        usuarioDTO.setEmail("kaioaar@gmail.com");
//        usuarioDTO.setAtivo(TipoAtivo.ATIVO);
//        usuarioDTO.setLogin("kaio");
//        usuarioDTO.setSenha("123");
//        usuarioDTO.setSexo("M");
//        usuarioDTO.setDataNascimento(data);
//
//        UsuarioDTO usuarioDTO2 = new UsuarioDTO();
//        usuarioDTO2.setIdUsuario(1);
//        usuarioDTO2.setEmail("kaioaar2@gmail.com");
//        usuarioDTO2.setAtivo(TipoAtivo.ATIVO);
//        usuarioDTO2.setLogin("kaio");
//        usuarioDTO2.setSenha("123");
//        usuarioDTO2.setSexo("M");
//        usuarioDTO2.setDataNascimento(data);
//
//        Map<String, Object> dados = new HashMap<>();
//        dados.put("nome", usuarioDTO.getNome());
//        dados.put("email", from);
//        dados.put("msg1", "Estamos muito contentes por você fazer parte da nossa comunidade!");
//        dados.put("msg2", " Seu cadastro foi realizado com sucesso, seu identificador é " + usuarioDTO.getIdUsuario());
//        dados.put("msg3","");
//
//        when(fmConfiguration.getTemplate(anyString())).thenReturn(template);
//
//        //ACT
//        String html = emailService.geContentFromTemplate(usuarioDTO, usuarioDTO2, TipoEmail.CADASTRO);
//        template = fmConfiguration.getTemplate("email-template-universal.ftl");
//        FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
//
//        //ASSERT
//        assertNotNull(html);
//    }


    @Test
    public void deveTestarGetTemplateSeguroComSucesso() throws IOException, TemplateException {
        LocalDate data = LocalDate.of(2002,03,14);
        Template template = new Template("", Reader.nullReader());

        UsuarioSeguroDTO usuarioDTO = new UsuarioSeguroDTO();
        usuarioDTO.setEmail("kaioaar@gmail.com");
        usuarioDTO.setNome("kaio");
        usuarioDTO.setToken("123");

        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuarioDTO.getNome());
        dados.put("email", from);

        dados.put("nome", usuarioDTO.getNome());
        dados.put("msg1", "Foi solicitada uma troca de senha, caso não tenha sido você \n" +
                "apenas ignore esta mensagem");
        dados.put("msg2", "Utilize esse token para fazer o login e trocar sua senha: ");
        dados.put("msg3",usuarioDTO.getToken());

        when(fmConfiguration.getTemplate(anyString())).thenReturn(template);

        //ACT
        String html = emailService.geContentFromTemplateUsuarioSeguro(usuarioDTO, TipoEmail.CADASTRO);
        template = fmConfiguration.getTemplate("email-template-universal.ftl");
        FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

        //ASSERT
        assertNotNull(html);
    }
}