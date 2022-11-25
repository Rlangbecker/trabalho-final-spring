package com.vemser.geekers.service;
import com.vemser.geekers.dto.EventoDTO;
import com.vemser.geekers.dto.UsuarioDTO;
import com.vemser.geekers.dto.UsuarioSeguroDTO;
import com.vemser.geekers.enums.TipoEmail;
import com.vemser.geekers.enums.TipoEvento;
import com.vemser.geekers.exception.RegraDeNegocioException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private static final String TO = "kaio.andradre@dbccompany.com.br";
    private final EventoService eventoService;
    private final UsuarioService usuarioService;
    private final JavaMailSender emailSender;

    @Scheduled(cron = "0 0 * * * *")
    public void enviarEmailTempoEvento() throws RegraDeNegocioException {
        List<UsuarioDTO> usuarios = usuarioService.list();
        for (var usuario : usuarios){
            sendEmail(usuario, null, TipoEmail.TEMPO_EVENTO);
        }
    }
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
        //caso for usar, alterar o setFrom e setTo
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

    public void sendEmail(UsuarioDTO usuario, UsuarioDTO usuario2, TipoEmail tipoEmail) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            if(tipoEmail==TipoEmail.CADASTRO){
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

                mimeMessageHelper.setFrom(TO);
                mimeMessageHelper.setTo(usuario.getEmail());
                mimeMessageHelper.setSubject("Novo Cadastro");
                mimeMessageHelper.setText(geContentFromTemplate(usuario,null,tipoEmail), true);

                emailSender.send(mimeMessageHelper.getMimeMessage());
            } else if (tipoEmail == TipoEmail.TEMPO_EVENTO) {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

                mimeMessageHelper.setFrom(TO);
                mimeMessageHelper.setTo(usuario.getEmail());
                mimeMessageHelper.setSubject("Tempo para o final do evento");
                mimeMessageHelper.setText(geContentFromTemplate(usuario,null,tipoEmail), true);

                emailSender.send(mimeMessageHelper.getMimeMessage());
            } else {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

                mimeMessageHelper.setFrom(TO);
                mimeMessageHelper.setBcc(usuario2.getEmail());
                mimeMessageHelper.setSubject("Novo Match");
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
        LocalDate diaAtual = LocalDate.now();
        EventoDTO evento =  eventoService.findEvento(TipoEvento.COPA_DO_MUNDO);
        Period period = Period.between(diaAtual, evento.getDataFim());
        if (tipoEmail==TipoEmail.CADASTRO){
            dados.put("msg1", "Estamos muito contentes por você fazer parte da nossa comunidade!");
            dados.put("msg2", " Seu cadastro foi realizado com sucesso, seu identificador é " + usuario.getIdUsuario());
            dados.put("msg3","");
        } else if (tipoEmail == TipoEmail.TEMPO_EVENTO) {
            dados.put("msg1", "Final para o termino do evento COPA DO MUNDO!:");
            dados.put("msg2", "tempo restante: " + period.getMonths() + " mês e" + period.getDays() + " dias");
            dados.put("msg3","");
        } else{
            dados.put("nome", usuario.getNome());
            dados.put("msg1", "Estamos muito contentes por você fazer parte da nossa comunidade!");
            dados.put("msg2", "Você deu match com " + usuario2.getNome().toUpperCase() + " 8)");
            dados.put("msg3","");
        }

        Template template = fmConfiguration.getTemplate("email-template-universal.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public String geContentFromTemplateUsuarioSeguro(UsuarioSeguroDTO usuario, TipoEmail tipoEmail) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", usuario.getNome());
        dados.put("email", from);

            dados.put("nome", usuario.getNome());
            dados.put("msg1", "Foi solicitada uma troca de senha, caso não tenha sido você \n" +
                    "apenas ignore esta mensagem");
            dados.put("msg2", "Utilize esse token para fazer o login e trocar sua senha: ");
            dados.put("msg3",usuario.getToken());

        Template template = fmConfiguration.getTemplate("email-template-universal.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public void sendEmailSeguro(UsuarioSeguroDTO usuario,TipoEmail tipoEmail) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

                mimeMessageHelper.setFrom(TO);
                mimeMessageHelper.setTo(usuario.getEmail());
                mimeMessageHelper.setSubject("Nova senha");
                mimeMessageHelper.setText(geContentFromTemplateUsuarioSeguro(usuario, tipoEmail), true);

                emailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

}