package br.com.ged.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.dto.FiltroLeiDTO;
import br.com.ged.entidades.Lei;
import br.com.ged.generics.ConsultasDaoJpa;

@Stateless
public class LeiServiceImpl implements LeiService{
	
	@EJB
	private ConsultasDaoJpa<Lei> reposiroty;

	@Override
	public List<Lei> pesquisar(FiltroLeiDTO filtro, String... hbInitialize) {
		return reposiroty.filtrarPesquisa(filtro, Lei.class, hbInitialize);
	}
}