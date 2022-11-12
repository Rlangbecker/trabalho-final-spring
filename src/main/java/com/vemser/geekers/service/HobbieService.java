package com.vemser.geekers.service;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.geekers.dto.HobbieCreateDTO;
import com.vemser.geekers.dto.HobbieDTO;
import com.vemser.geekers.dto.LoginWithIdDTO;
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
    private final UsuarioLoginService usuarioLoginService;

    // create - remover - editar - listar(ListHobbieByIdUsuario)

    public HobbieDTO create(HobbieCreateDTO hobbieCreateDTO) throws RegraDeNegocioException {
        LoginWithIdDTO login = usuarioLoginService.getLoggedUser();
        HobbieEntity hobbieEntity = objectMapper.convertValue(hobbieCreateDTO, HobbieEntity.class);
        UsuarioEntity usuario = usuarioService.findById(login.getIdUsuario());
        hobbieEntity.setUsuario(usuario);
        hobbieEntity.getUsuario().setIdUsuario(usuario.getIdUsuario());

        HobbieDTO hDTO = objectMapper.convertValue(hobbieRepository.save(hobbieEntity), HobbieDTO.class);

        hDTO.setIdHobbie(hobbieEntity.getIdHobbie());
        hDTO.setIdUsuario(hobbieEntity.getUsuario().getIdUsuario());
        return hDTO;
    }

    public void remover() throws RegraDeNegocioException {
        LoginWithIdDTO login = usuarioLoginService.getLoggedUser();
        HobbieEntity hobbie = objectMapper.convertValue(findByIdUsuario(login.getIdUsuario()),HobbieEntity.class);
        hobbieRepository.delete(hobbie);
    }

    public HobbieDTO editar(HobbieCreateDTO hobbieCreateDTO) throws RegraDeNegocioException {
        LoginWithIdDTO login = usuarioLoginService.getLoggedUser();
        HobbieDTO hobbieDTOAntigo = findByIdUsuario(login.getIdUsuario());

        HobbieEntity hobbieEntity = objectMapper.convertValue(hobbieDTOAntigo, HobbieEntity.class);

        hobbieEntity.setDescricao(hobbieCreateDTO.getDescricao());
        hobbieEntity.setTipoHobbie(hobbieEntity.getTipoHobbie());
        hobbieEntity.setUsuario(usuarioService.findById(login.getIdUsuario()));
        hobbieEntity.getUsuario().setIdUsuario(login.getIdUsuario());

        HobbieDTO hobbieDTO = objectMapper.convertValue(hobbieRepository.save(hobbieEntity), HobbieDTO.class);
        hobbieDTO.setIdUsuario(login.getIdUsuario());

        return hobbieDTO;
    }

    public HobbieDTO findHobbieById(Integer id) {
        HobbieEntity hobbieEntity=hobbieRepository.findHobbieEntityByIdHobbie(id);
        HobbieDTO hDTO = objectMapper.convertValue(hobbieEntity,HobbieDTO.class);
        hDTO.setIdUsuario(hobbieEntity.getUsuario().getIdUsuario());

        return hDTO;
    }

    public HobbieDTO findByIdUsuario(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuario=usuarioService.findById(id);
        HobbieEntity hobbie = hobbieRepository.findHobbieEntityByUsuario(usuario);
        HobbieDTO hobbieDTO=objectMapper.convertValue(hobbie, HobbieDTO.class);

        hobbieDTO.setIdUsuario(usuario.getIdUsuario());
        return hobbieDTO;
    }

}