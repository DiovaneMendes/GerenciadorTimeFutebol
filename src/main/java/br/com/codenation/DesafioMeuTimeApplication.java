package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;
import br.com.codenation.model.*;
import br.com.codenation.negocio.*;
import br.com.codenation.repositorio.*;

public class DesafioMeuTimeApplication implements MeuTimeInterface {
	TimeNegocio timeNegocio = new TimeNegocio();
	JogadorNegocio jogadorNegocio = new JogadorNegocio();

	private TimeRepositorio timeRepositorio;
	private JogadorRepositorio jogadorRepositorio;

	public DesafioMeuTimeApplication(TimeRepositorio timeRepositorio, JogadorRepositorio jogadorRepositorio){
		this.timeRepositorio = timeRepositorio;
		this.jogadorRepositorio = jogadorRepositorio;
	}


	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		try {
			timeNegocio.salvar(new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario));
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		try {
			jogadorNegocio.salvar(new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario));
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		boolean validador = jogadorRepositorio.mostraLista()
												.stream()
												.anyMatch(j -> j.getId() == idJogador);

		if(validador){
			jogadorRepositorio.mostraLista()
								.stream()
								.filter(jogador -> jogador.getCapitao() == true)
								.peek(jogador -> jogador.setCapitao(false))
								.filter(jogador -> jogador.getId() == idJogador)
								.peek(jogador -> jogador.setCapitao(true));
		}else{
			throw new JogadorNaoEncontradoException("Jogador não encontrado!");
		}
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		OptionalLong idJogador;

		if(timeRepositorio.timeExistente(idTime)){
			idJogador = jogadorRepositorio.mostraLista()
											.stream()
											.filter(jogador -> jogador.getIdTime() == idTime && jogador.getCapitao() == true)
											.mapToLong(Jogador::getId)
											.findAny();

			if(idJogador.isPresent()){
				return idJogador.getAsLong();
			}else{
				throw new CapitaoNaoInformadoException("Capitão não informado!");
			}

		}else{
			throw new TimeNaoEncontradoException("Time inexistente!");
		}
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		if(jogadorRepositorio.jogadorExistente(idJogador)){
			return jogadorRepositorio.mostraLista()
										.stream()
										.filter(jogador -> jogador.getId() == idJogador)
										.map(Jogador::getNome)
										.findFirst()
										.get();
		}else{
			throw new JogadorNaoEncontradoException("Jogador não encontrado!");
		}
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		if(timeRepositorio.timeExistente(idTime)){
			return timeRepositorio.mostraLista()
									.stream()
									.filter(time -> time.getId() == idTime)
									.map(Time::getNome)
									.findFirst()
									.get();
		}else{
			throw new TimeNaoEncontradoException("Time não encontrado!");
		}
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		if(timeRepositorio.timeExistente(idTime)){
			return jogadorRepositorio.mostraLista()
										.stream()
										.filter(jogador -> jogador.getIdTime() == idTime)
										.mapToLong(Jogador::getId)
										.boxed()
										.collect(Collectors.toList());
		}else{
			throw new TimeNaoEncontradoException("Time não encontrado!");
		}
	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		if(timeRepositorio.timeExistente(idTime)){
			int maior = jogadorRepositorio.mostraLista()
											.stream()
											.filter(jogador -> jogador.getIdTime() == idTime)
											.mapToInt(Jogador::getNivelHabilidade)
											.max()
											.getAsInt();

			return jogadorRepositorio.mostraLista()
										.stream()
										.filter(jogador -> jogador.getNivelHabilidade() == maior)
										.mapToLong(Jogador::getId)
										.boxed()
										.findFirst()
										.get();

		}else{
			throw new TimeNaoEncontradoException("Time não encontrado!");
		}
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		throw new UnsupportedOperationException();
	}
}
