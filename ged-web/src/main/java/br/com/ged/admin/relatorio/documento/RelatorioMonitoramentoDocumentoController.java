package br.com.ged.admin.relatorio.documento;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ged.admin.relatorio.RelatorioSuperController;
import br.com.ged.domain.Pagina;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.entidades.auditoria.DocumentoAudit;
import br.com.ged.entidades.auditoria.DocumentoAuditPK;
import br.com.ged.service.audit.DocumentoAuditService;

@ManagedBean(name="painelRelatorioMonitoramentoDocumento")
@ViewScoped
public class RelatorioMonitoramentoDocumentoController extends RelatorioSuperController{
	
	private DocumentoAudit documentoSelecionado;
	
	private DocumentoAudit documentoSelecionadoPreAlteracao;
	private DocumentoAudit documentoSelecionadoPosAlteracao;
	
	@EJB
	private DocumentoAuditService documentoAuditService;
	
	@PostConstruct
	public void inicio(){
		documentoSelecionado = new DocumentoAudit();
		documentoSelecionadoPreAlteracao = new DocumentoAudit();
		documentoSelecionadoPosAlteracao = new DocumentoAudit();
	}
	
	public void preDossieAlteracao(){
		
		DocumentoAuditPK pk = documentoSelecionado.getId();
		
		pk.setTipoOperacaoAudit(TipoOperacaoAudit.ALTERADO_PRE);
		
		documentoSelecionadoPreAlteracao = documentoAuditService.getById(pk);
		
		pk.setTipoOperacaoAudit(TipoOperacaoAudit.ALTERADO_POS);
		
		documentoSelecionadoPosAlteracao = documentoAuditService.getById(pk);
	}

	public DocumentoAudit getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(DocumentoAudit documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}
	
	public DocumentoAudit getDocumentoSelecionadoPreAlteracao() {
		return documentoSelecionadoPreAlteracao;
	}

	public void setDocumentoSelecionadoPreAlteracao(DocumentoAudit documentoSelecionadoPreAlteracao) {
		this.documentoSelecionadoPreAlteracao = documentoSelecionadoPreAlteracao;
	}

	public DocumentoAudit getDocumentoSelecionadoPosAlteracao() {
		return documentoSelecionadoPosAlteracao;
	}

	public void setDocumentoSelecionadoPosAlteracao(DocumentoAudit documentoSelecionadoPosAlteracao) {
		this.documentoSelecionadoPosAlteracao = documentoSelecionadoPosAlteracao;
	}

	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.RELATORIO_MONITORAMENTO;
	}
}