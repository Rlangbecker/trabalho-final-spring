package com.vemser.geekers.service;


//import com.vemser.geekers.exceptions.BancoDeDadosException;
import com.vemser.geekers.entity.Hobbie;
import com.vemser.geekers.repository.HobbieRepository;

public class HobbieService {

    private HobbieRepository hobbiesRepository;

    public HobbieService() {
        hobbiesRepository = new HobbieRepository();
    }

    public HobbieRepository getHobbiesRepository() {
        return hobbiesRepository;
    }

    public void setHobbiesRepository(HobbieRepository hobbiesRepository) {
        this.hobbiesRepository = hobbiesRepository;
    }

    public void adicionarHobbie(Hobbie hobbie) {
//        try {
//            Hobbie hobbieAdicionado = hobbiesRepository.adicionar(hobbie);
//            System.out.println("Hobbie adicionado com sucesso! " + hobbieAdicionado);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("ERRO: " + e.getMessage());
//        }
    }

    public void remover(Integer id) {
//        try {
//            boolean conseguiuRemover = hobbiesRepository.remover(id);
//            System.out.println("Hobbie removido? " + conseguiuRemover + "| com id= " + id);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    public void editar(Integer id, Hobbie hobbie) {
//        try {
//            boolean conseguiuEditar = hobbiesRepository.editar(id, hobbie);
//            System.out.println("Hobbie editado? " + conseguiuEditar + "| com id= " + id);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    public void listar() {
//        try {
//            hobbiesRepository.listar().forEach(System.out::println);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

    public void listarPorUsuario(Integer id) {
//        try {
//            hobbiesRepository.listarHobbiePorUsuario(id).forEach(System.out::println);
//        } catch (BancoDeDadosException e) {
//            e.printStackTrace();
//        }
    }

}