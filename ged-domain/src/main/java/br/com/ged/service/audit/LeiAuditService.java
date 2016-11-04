package br.com.ged.service.audit;

import java.util.List;

import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroLeiAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;
import br.com.ged.entidades.Lei;
import br.com.ged.entidades.auditoria.LeiAudit;
import br.com.ged.entidades.auditoria.LeiAuditPK;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface LeiAuditService extends IResultadoMonitoramento{

	/**
	 * 
	 * @param filtro
	 * @param hibernateInitialize 
	 * @return
	 */
	List<LeiAudit> detalharOperacao(FiltroLeiAuditDTO filtroDoc, TipoOperacaoAudit tipoOp);

	Long countLeiAudit(FiltroMonitoramentoAuditDTO filtroLeiAuditDTO, TipoOperacaoAudit tipoOp);

	void auditoria(Lei lei, TipoOperacaoAudit alteracao);

	void auditoriaAlteracao(Lei leiPreAlteracao, Lei leiPosAlteracao);
	
	LeiAudit getById(LeiAuditPK leiAuditPK);
}
