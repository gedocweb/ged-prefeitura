package br.com.ged.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.dto.FiltroBalanceteDTO;
import br.com.ged.entidades.Balancete;
import br.com.ged.generics.ConsultasDaoJpa;

@Stateless
public class BalanceteServiceImpl implements BalanceteService{
	
	@EJB
	private ConsultasDaoJpa<Balancete> reposiroty;

	@Override
	public List<Balancete> pesquisar(FiltroBalanceteDTO filtro, String... hbInitialize) {
		return reposiroty.filtrarPesquisa(filtro, Balancete.class, hbInitialize);
	}
}