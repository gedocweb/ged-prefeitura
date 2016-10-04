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
	Long countAlterados(FiltroMonitoramentoAuditDTO filtro);
	Long countInseridos(FiltroMonitoramentoAuditDTO filtro);
	Long countBaixados(FiltroMonitoramentoAuditDTO filtro);
	Long countVisualizados(FiltroMonitoramentoAuditDTO filtro);
	Long countExcluidos(FiltroMonitoramentoAuditDTO filtro);
	Long countExportados(FiltroMonitoramentoAuditDTO filtro);
}
