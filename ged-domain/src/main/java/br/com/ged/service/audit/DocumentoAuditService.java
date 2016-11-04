package br.com.ged.service.audit;

import java.util.List;

import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroDocumentoAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;
import br.com.ged.entidades.Documento;
import br.com.ged.entidades.auditoria.DocumentoAudit;
import br.com.ged.entidades.auditoria.DocumentoAuditPK;

/**
 * 
 * @author pedro.oliveira
 * 
 *
 */
public interface DocumentoAuditService extends IResultadoMonitoramento{

	/**
	 * 
	 * @param filtro
	 * @param hibernateInitialize 
	 * @return
	 */
	List<DocumentoAudit> detalharOperacao(FiltroDocumentoAuditDTO filtroDoc, TipoOperacaoAudit tipoOp);

	Long countAudit(FiltroMonitoramentoAuditDTO filtroDocumentoAuditDTO, TipoOperacaoAudit tipoOp);

	void auditoria(Documento balancete, TipoOperacaoAudit alteracao);

	void auditoriaAlterar(Documento balancetePreAlteracao, Documento balancetePosAlteracao);
	
	DocumentoAudit getById(DocumentoAuditPK balanceteAuditPK);
}
