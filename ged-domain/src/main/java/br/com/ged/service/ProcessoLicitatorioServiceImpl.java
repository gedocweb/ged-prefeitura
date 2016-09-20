package br.com.ged.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.dto.FiltroProcessoLicitatorioDTO;
import br.com.ged.entidades.ProcessoLicitatorio;
import br.com.ged.generics.ConsultasDaoJpa;

@Stateless
public class ProcessoLicitatorioServiceImpl implements ProcessoLicitatorioService{
	
	@EJB
	private ConsultasDaoJpa<ProcessoLicitatorio> reposiroty;

	@Override
	public List<ProcessoLicitatorio> pesquisar(FiltroProcessoLicitatorioDTO filtro, String... hbInitialize) {
		return reposiroty.filtrarPesquisa(filtro, ProcessoLicitatorio.class, hbInitialize);
	}
}