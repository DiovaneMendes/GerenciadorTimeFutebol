package br.com.codenation.repositorio;

import br.com.codenation.model.Jogador;

import java.util.ArrayList;
import java.util.List;

public class JogadorRepositorio {
    private List<Jogador> jogadores;
    private static JogadorRepositorio instance = null;

    private JogadorRepositorio(){
        jogadores = new ArrayList<>();
    }

    public static JogadorRepositorio getInstance() {
        if(instance == null) instance = new JogadorRepositorio();
        return instance;
    }

    public boolean add(Jogador jogador) {
        return (jogadores.add(jogador));
    }

    public boolean jogadorExistente(Long id){
        return jogadores.stream()
                        .anyMatch(j -> j.getId() == id);
    }
}
