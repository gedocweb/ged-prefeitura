package br.com.ged.service;

import java.util.List;

import br.com.ged.dto.FiltroBalanceteDTO;
import br.com.ged.entidades.Balancete;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface BalanceteService {

	/**
	 * 
	 * @param filtro
	 * @param hibernateInitialize 
	 * @return
	 */
	List<Balancete> pesquisar(FiltroBalanceteDTO filtroDoc, String...strings);
}
