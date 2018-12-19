package br.com.codenation.negocio;

import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.model.Time;
import br.com.codenation.repositorio.TimeRepositorio;

public class TimeNegocio{
    public void salvar(Time t) throws Exception{
        this.validarTimeExistente(t.getId());
        this.validarCamposObrigatorios(t);
        TimeRepositorio.getInstance().add(t);
    }

    private void validarTimeExistente(Long id) {
        if(TimeRepositorio.getInstance().timeExistente(id)){
            throw new IdentificadorUtilizadoException("Identificador do time inexistente!");
        }
    }

    private void validarCamposObrigatorios(Time t) throws Exception{
        if(t.getId() == null || t.getId() == 0){
            throw new Exception("Id invalido!");
        }

        if(t.getNome() == null || t.getNome().isEmpty()){
            throw new Exception("Nome invalido!");
        }

        if(t.getCorUniformePrincipal() == null || t.getCorUniformePrincipal().isEmpty()){
            throw new Exception("Cor uniforme principal invalido!");
        }

        if(t.getCorUniformeSecundario() == null || t.getCorUniformeSecundario().isEmpty()){
            throw new Exception("Cor uniforme secundario invalido!");
        }
    }
}
