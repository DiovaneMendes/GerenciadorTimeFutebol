package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.model.*;
import br.com.codenation.negocio.*;

public class DesafioMeuTimeApplication implements MeuTimeInterface {
	TimeNegocio timeNegocio = new TimeNegocio();
	JogadorNegocio jogadorNegocio = new JogadorNegocio();


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
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		throw new UnsupportedOperationException();
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
