package br.com.ged.service.audit;

import java.util.List;

import br.com.ged.domain.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroBalanceteAuditDTO;
import br.com.ged.entidades.Balancete;
import br.com.ged.entidades.auditoria.BalanceteAudit;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface BalanceteAuditService extends IResultadoMonitoramento{

	/**
	 * 
	 * @param filtro
	 * @param hibernateInitialize 
	 * @return
	 */
	List<BalanceteAudit> pesquisar(FiltroBalanceteAuditDTO filtroDoc, String...strings);

	Integer countBalanceteAudit(FiltroBalanceteAuditDTO filtroBalanceteAuditDTO);

	void auditoriaBalancete(Balancete balancete, TipoOperacaoAudit alteracao);
}
