package br.com.ged.admin.relatorio.lei;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ged.admin.relatorio.RelatorioSuperController;
import br.com.ged.domain.Pagina;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.entidades.auditoria.LeiAudit;
import br.com.ged.entidades.auditoria.LeiAuditPK;
import br.com.ged.service.audit.LeiAuditService;

@ManagedBean(name="painelRelatorioMonitoramentoLei")
@ViewScoped
public class RelatorioMonitoramentoLeiController extends RelatorioSuperController{
	
	private LeiAudit leiSelecionado;
	
	private LeiAudit leiSelecionadoPreAlteracao;
	private LeiAudit leiSelecionadoPosAlteracao;
	
	@EJB
	private LeiAuditService leiAuditService;
	
	@PostConstruct	
	public void inicio(){
		
		leiSelecionado = new LeiAudit();
	}
	
	public void preDossieAlteracao(){
		
		LeiAuditPK pk = leiSelecionado.getId();
		
		pk.setTipoOperacaoAudit(TipoOperacaoAudit.ALTERADO_PRE);
		
		leiSelecionadoPreAlteracao = leiAuditService.getById(pk);
		
		pk.setTipoOperacaoAudit(TipoOperacaoAudit.ALTERADO_POS);
		
		leiSelecionadoPosAlteracao = leiAuditService.getById(pk);
	}

	public LeiAudit getLeiSelecionado() {
		return leiSelecionado;
	}

	public void setLeiSelecionado(LeiAudit leiSelecionado) {
		this.leiSelecionado = leiSelecionado;
	}
	
	public LeiAudit getLeiSelecionadoPreAlteracao() {
		return leiSelecionadoPreAlteracao;
	}

	public void setLeiSelecionadoPreAlteracao(LeiAudit leiSelecionadoPreAlteracao) {
		this.leiSelecionadoPreAlteracao = leiSelecionadoPreAlteracao;
	}

	public LeiAudit getLeiSelecionadoPosAlteracao() {
		return leiSelecionadoPosAlteracao;
	}

	public void setLeiSelecionadoPosAlteracao(LeiAudit leiSelecionadoPosAlteracao) {
		this.leiSelecionadoPosAlteracao = leiSelecionadoPosAlteracao;
	}

	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.RELATORIO_MONITORAMENTO;
	}
}