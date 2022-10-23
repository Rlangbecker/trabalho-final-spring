package com.vemser.geekers.service;


//import com.vemser.geekers.exceptions.BancoDeDadosException;
import com.vemser.geekers.entity.Hobbie;
import com.vemser.geekers.exception.BancoDeDadosException;
import com.vemser.geekers.repository.HobbieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HobbieService {

    private final HobbieRepository hobbiesRepository;

    public Hobbie create(Hobbie hobbie) {
        try {
            Hobbie hobbieAdicionado = hobbiesRepository.adicionar(hobbie);
            System.out.println("Hobbie adicionado com sucesso! " + hobbieAdicionado);
            return hobbieAdicionado;
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        return hobbie;
    }

    public void remover(Integer id) {
        try {
            boolean conseguiuRemover = hobbiesRepository.remover(id);
            System.out.println("Hobbie removido? " + conseguiuRemover + "| com id= " + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void editar(Integer id, Hobbie hobbie) {
        try {
            boolean conseguiuEditar = hobbiesRepository.editar(id, hobbie);
            System.out.println("Hobbie editado? " + conseguiuEditar + "| com id= " + id);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void listar() {
        try {
            hobbiesRepository.listar().forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

    public void listarPorUsuario(Integer id) {
        try {
            hobbiesRepository.listarHobbiePorUsuario(id).forEach(System.out::println);
        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        }
    }

}