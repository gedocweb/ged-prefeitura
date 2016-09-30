package br.com.ged.service.audit;

import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface MonitoramentoAuditService {

	/**
	 * 
	 * @param filtro
	 * @param hibernateInitialize 
	 * @return
	 */
	Integer countAlterados(FiltroMonitoramentoAuditDTO filtro);
	Integer countInseridos(FiltroMonitoramentoAuditDTO filtro);
	Integer countBaixados(FiltroMonitoramentoAuditDTO filtro);
	Integer countVisualizados(FiltroMonitoramentoAuditDTO filtro);
	Integer countExcluidos(FiltroMonitoramentoAuditDTO filtro);
}
