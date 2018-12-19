package br.com.codenation.repositorio;

import br.com.codenation.model.Time;

import java.util.ArrayList;
import java.util.List;

public class TimeRepositorio {
    private List<Time> times;
    private static TimeRepositorio instance = null;

    private TimeRepositorio(){
        times = new ArrayList<>();
    }

    public static TimeRepositorio getInstance() {
        if(instance == null) instance = new TimeRepositorio();
        return instance;
    }

    public boolean add(Time time) {
        return (times.add(time));
    }

    public boolean timeExistente(Long id){
        return times.stream()
                    .anyMatch(j -> j.getId() == id);
    }

    public List<Time> mostraLista(){
        return times;
    }
}
