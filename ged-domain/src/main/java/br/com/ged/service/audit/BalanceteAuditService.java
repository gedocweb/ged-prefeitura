package br.com.ged.service.audit;

import java.util.List;

import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroBalanceteAuditDTO;
import br.com.ged.entidades.Balancete;
import br.com.ged.entidades.auditoria.BalanceteAudit;
import br.com.ged.entidades.auditoria.BalanceteAuditPK;

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
	List<BalanceteAudit> detalharOperacao(FiltroBalanceteAuditDTO filtroDoc, TipoOperacaoAudit tipoOp);

	Long countBalanceteAudit(FiltroBalanceteAuditDTO filtroBalanceteAuditDTO, TipoOperacaoAudit tipoOp);

	void auditoriaBalancete(Balancete balancete, TipoOperacaoAudit alteracao);

	void auditoriaBalancete(Balancete balancetePreAlteracao, Balancete balancetePosAlteracao);
	
	BalanceteAudit getById(BalanceteAuditPK balanceteAuditPK);
}
