package br.com.ged.service.audit;

import br.com.ged.domain.entidade.DepartamentoEnum;
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
	Long countAlterados(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum);
	Long countInseridos(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum);
	Long countBaixados(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum);
	Long countVisualizados(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum);
	Long countExcluidos(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum);
	Long countExportados(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum);
}
