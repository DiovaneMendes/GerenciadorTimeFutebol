package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.*;
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

		if(jogadorRepositorio.jogadorExistente(idJogador)){
			jogadorRepositorio.mostraLista()
								.stream()
								.filter(jogador -> jogador.getCapitao() == true)
								.peek(jogador -> jogador.setCapitao(false));

			jogadorRepositorio.mostraLista()
								.stream()
								.filter(jogador -> jogador.getId() == idJogador)
								.peek(jogador -> jogador.setCapitao(true));
		}else{
			throw new JogadorNaoEncontradoException("Jogador não encontrado!");
		}
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {

		if(timeRepositorio.timeExistente(idTime)){
			OptionalLong idJogador = jogadorRepositorio.mostraLista()
														.stream()
														.filter(jogador -> jogador.getIdTime() == idTime &&
																			jogador.getCapitao() == true)
														.mapToLong(Jogador::getId)
														.findFirst();

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
										.filter(jogador -> jogador.getIdTime() == idTime &&
														   jogador.getNivelHabilidade() == maior)
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

		if(timeRepositorio.timeExistente(idTime)){
			int maisVelho = jogadorRepositorio.mostraLista()
											.stream()
											.filter(jogador -> jogador.getIdTime() == idTime)
											.mapToInt(jogador -> Period.between(jogador.getDataNascimento(), LocalDate.now()).getYears())
											.max()
											.getAsInt();

			return jogadorRepositorio.mostraLista()
										.stream()
										.filter(jogador -> jogador.getIdTime() == idTime &&
												Period.between(jogador.getDataNascimento(), LocalDate.now()).getYears() == maisVelho)
										.mapToLong(Jogador::getId)
										.boxed()
										.findFirst()
										.get();
		}else{
			throw new TimeNaoEncontradoException("Time não encontrado!");
		}
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {

		if(!timeRepositorio.mostraLista().isEmpty()) {
			return timeRepositorio.mostraLista()
									.stream()
									.mapToLong(Time::getId)
									.sorted()
									.boxed()
									.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {

		if(timeRepositorio.timeExistente(idTime)){
			BigDecimal maiorSalario = jogadorRepositorio.mostraLista()
														.stream()
														.filter(jogador -> jogador.getIdTime() == idTime)
														.map(Jogador::getSalario)
														.max(Comparator.comparingDouble(BigDecimal::doubleValue))
														.get();

			List<Jogador> apoio = jogadorRepositorio.mostraLista()
													.stream()
													.filter(jogador -> jogador.getIdTime() == idTime && jogador.getSalario() == maiorSalario)
													.collect(Collectors.toList());

			return jogadorRepositorio.mostraLista()
										.stream()
										.filter(jogador -> jogador.getIdTime() == idTime &&
														   jogador.getId() == apoio.stream()
																				   .mapToLong(Jogador::getId)
																				   .min()
																				   .getAsLong())
										.mapToLong(Jogador::getId)
										.findFirst()
										.getAsLong();
		}else{
			throw new TimeNaoEncontradoException("Time não encontrado!");
		}
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		if(jogadorRepositorio.jogadorExistente(idJogador)){
			return jogadorRepositorio.mostraLista()
										.stream()
										.filter(jogador -> jogador.getId() == idJogador)
										.map(Jogador::getSalario)
										.findFirst()
										.get();
		}else{
			throw new JogadorNaoEncontradoException("Jogador não encontrado");
		}
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {

		List<Jogador> topJogadores = new ArrayList<>();
		List<Integer> niveisAdicionados = new ArrayList<>();

		for(int i=0; i<top; i++){

			int maiorNivel = jogadorRepositorio.mostraLista()
												.stream()
												.filter(jogador -> !topJogadores.contains(jogador) &&
																	!niveisAdicionados.contains(jogador.getNivelHabilidade()))
												.mapToInt(Jogador::getNivelHabilidade)
												.max()
												.getAsInt();

			List<Jogador> apoio = jogadorRepositorio.mostraLista()
													.stream()
													.filter(jogador -> jogador.getNivelHabilidade() == maiorNivel)
													.collect(Collectors.toList());

			apoio.forEach(jogador -> niveisAdicionados.add(jogador.getNivelHabilidade()));

			topJogadores.add(
				apoio.stream()
					 .filter(jogador -> jogador.getId() == apoio.stream()
																.mapToLong(Jogador::getId)
																.min()
																.getAsLong())
					 .findFirst()
					 .get()
			);
		}

		if(jogadorRepositorio.mostraLista().isEmpty()) return new ArrayList<>();

		return topJogadores.stream()
							.mapToLong(Jogador::getId)
							.boxed()
							.collect(Collectors.toList());
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {

		Time timeCasa = timeRepositorio.mostraLista()
										.stream()
										.filter(time -> time.getId() == timeDaCasa)
										.findFirst()
										.get();

		Time timeFora = timeRepositorio.mostraLista()
										.stream()
										.filter(time -> time.getId() == timeDeFora)
										.findFirst()
										.get();

		if(timeCasa.getCorUniformePrincipal().equals(timeFora.getCorUniformePrincipal())){
			return timeFora.getCorUniformeSecundario();
		}

		return timeFora.getCorUniformePrincipal();
	}
}