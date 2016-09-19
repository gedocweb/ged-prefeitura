package br.com.ged.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.dto.FiltroRecursoHumanoDTO;
import br.com.ged.entidades.RecursoHumano;
import br.com.ged.generics.ConsultasDaoJpa;

@Stateless
public class RecursoHumanoServiceImpl implements RecursoHumanoService{
	
	@EJB
	private ConsultasDaoJpa<RecursoHumano> reposiroty;

	@Override
	public List<RecursoHumano> pesquisar(FiltroRecursoHumanoDTO filtro, String... strings) {
		return reposiroty.filtrarPesquisa(filtro, RecursoHumano.class, strings);
	}
}