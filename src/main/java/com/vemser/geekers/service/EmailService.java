package com.vemser.geekers.service;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.enums.TipoEmail;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private static final String TO = "suporte@geekers.com";

    private final JavaMailSender emailSender;

    public void sendSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(TO);
        message.setSubject("Assunto");
        message.setText("Teste \n minha mensagem \n\nAtt,\nSistema.");
        emailSender.send(message);
    }

    public void sendWithAttachment() throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,
                true);

        helper.setFrom(from);
        helper.setTo(TO);
        helper.setSubject("Subject");
        helper.setText("Teste\n minha mensagem \n\nAtt,\nSistema.");

        File file1 = new File("imagem.jpg");
        FileSystemResource file
                = new FileSystemResource(file1);
        helper.addAttachment(file1.getName(), file);

        emailSender.send(message);
    }

    public void sendEmail(UsuarioDTO usuario,UsuarioDTO usuario2, TipoEmail tipoEmail) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            if(tipoEmail==TipoEmail.CADASTRO){
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setTo(TO);
                mimeMessageHelper.setSubject("subject");
                mimeMessageHelper.setText(geContentFromTemplate(usuario,null,tipoEmail), true);

                emailSender.send(mimeMessageHelper.getMimeMessage());
            } else {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setTo(TO);
                mimeMessageHelper.setSubject("subject");
                mimeMessageHelper.setText(geContentFromTemplate(usuario,usuario2,tipoEmail), true);

                emailSender.send(mimeMessageHelper.getMimeMessage());
            }


        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public String geContentFromTemplate(UsuarioDTO usuario,UsuarioDTO usuario2, TipoEmail tipoEmail) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuario.getNome());
        dados.put("email", from);

        if (tipoEmail==TipoEmail.CADASTRO){
            dados.put("msg1", "Estamos muito contentes por você fazer parte da nossa comunidade!");
            dados.put("msg2", " Seu cadastro foi realizado com sucesso, seu identificador é " + usuario.getIdUsuario());
        }else{
            dados.put("nome", usuario.getNome());
            dados.put("msg1", "Estamos muito contentes por você fazer parte da nossa comunidade!");
            dados.put("msg2", "Você deu match com " + usuario2.getNome().toUpperCase() + " 8)");
        }

        Template template = fmConfiguration.getTemplate("email-template-universal.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }
}