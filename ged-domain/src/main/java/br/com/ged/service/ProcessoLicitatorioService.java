package br.com.ged.service;

import java.util.List;

import br.com.ged.dto.FiltroProcessoLicitatorioDTO;
import br.com.ged.entidades.ProcessoLicitatorio;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface ProcessoLicitatorioService {

	/**
	 * 
	 * @param filtro
	 * @param hibernateInitialize 
	 * @return
	 */
	List<ProcessoLicitatorio> pesquisar(FiltroProcessoLicitatorioDTO filtroDoc, String...strings);
}
