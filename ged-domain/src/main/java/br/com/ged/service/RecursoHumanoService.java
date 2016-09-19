package br.com.ged.service;

import java.util.List;

import br.com.ged.dto.FiltroRecursoHumanoDTO;
import br.com.ged.entidades.RecursoHumano;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface RecursoHumanoService {

	/**
	 * 
	 * @param filtro
	 * @param hibernateInitialize 
	 * @return
	 */
	List<RecursoHumano> pesquisar(FiltroRecursoHumanoDTO filtro, String...strings);
}
