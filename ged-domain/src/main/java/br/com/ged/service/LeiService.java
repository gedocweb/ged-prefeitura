package br.com.ged.service;

import java.util.List;

import br.com.ged.dto.FiltroLeiDTO;
import br.com.ged.entidades.Lei;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface LeiService {

	/**
	 * 
	 * @param filtro
	 * @param hibernateInitialize 
	 * @return
	 */
	List<Lei> pesquisar(FiltroLeiDTO filtroDoc, String...strings);
}
