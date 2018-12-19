package br.com.codenation.negocio;

import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.model.Jogador;
import br.com.codenation.repositorio.*;

import java.time.LocalDate;
import java.time.Period;

public class JogadorNegocio {

    public void salvar(Jogador j) throws Exception{
        this.validarJogadorExistente(j.getId());
        this.validarTimeExistente(j.getIdTime());
        this.validarCamposObrigatorios(j);
        JogadorRepositorio.getInstance().add(j);
    }

    private void validarJogadorExistente(Long id) throws IdentificadorUtilizadoException{
        if(JogadorRepositorio.getInstance().jogadorExistente(id)){
            throw new IdentificadorUtilizadoException("Identificador jogador existente!");
        }
    }

    private void validarTimeExistente(Long id) throws IdentificadorUtilizadoException{
        if(TimeRepositorio.getInstance().timeExistente(id)){
            throw new IdentificadorUtilizadoException("Time inexistente!");
        }
    }

    private void validarCamposObrigatorios(Jogador j) throws Exception{
        int idade = Period.between(j.getDataNascimento(), LocalDate.now())
                        .getYears();

        if(j.getId() == 0 || j.getId() == null){
            throw new Exception("Id do jogador invalido!");
        }

        if(j.getIdTime() == 0 || j.getIdTime() == null){
            throw new Exception("Id do time invalido!");
        }

        if(j.getNome().isEmpty() || j.getNome() == null){
            throw new Exception("Nome invalido!");
        }

        if(j.getDataNascimento() == null || idade < 0){
            throw new Exception("Data nascimento invalida!");
        }

        if(j.getNivelHabilidade() == null || j.getNivelHabilidade() < 0){
            throw new Exception("Nivel habilidade invalida!");
        }

        if(j.getSalario() == null){
            throw new Exception("Salario invalido!");
        }
    }
}
