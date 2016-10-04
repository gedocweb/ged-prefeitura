package br.com.ged.admin.relatorio;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ged.domain.Pagina;
import br.com.ged.domain.TipoOperacaoAudit;
import br.com.ged.entidades.auditoria.BalanceteAudit;
import br.com.ged.entidades.auditoria.BalanceteAuditPK;
import br.com.ged.service.audit.BalanceteAuditService;

@ManagedBean(name="painelRelatorioMonitoramentoBalancete")
@ViewScoped
public class RelatorioMonitoramentoBalanceteController extends RelatorioSuperController{
	
	private BalanceteAudit balanceteSelecionado;
	
	private BalanceteAudit balanceteSelecionadoPreAlteracao;
	private BalanceteAudit balanceteSelecionadoPosAlteracao;
	
	@EJB
	private BalanceteAuditService balanceteAuditService;
	
	@PostConstruct	
	public void inicio(){
		
		balanceteSelecionado = new BalanceteAudit();
	}
	
	public void preDossieAlteracao(){
		
		BalanceteAuditPK pk = balanceteSelecionado.getId();
		
		pk.setTipoOperacaoAudit(TipoOperacaoAudit.ALTERADO_PRE);
		
		balanceteSelecionadoPreAlteracao = balanceteAuditService.getById(pk);
		
		pk.setTipoOperacaoAudit(TipoOperacaoAudit.ALTERADO_POS);
		
		balanceteSelecionadoPosAlteracao = balanceteAuditService.getById(pk);
	}

	public BalanceteAudit getBalanceteSelecionado() {
		return balanceteSelecionado;
	}

	public void setBalanceteSelecionado(BalanceteAudit balanceteSelecionado) {
		this.balanceteSelecionado = balanceteSelecionado;
	}
	
	public BalanceteAudit getBalanceteSelecionadoPreAlteracao() {
		return balanceteSelecionadoPreAlteracao;
	}

	public void setBalanceteSelecionadoPreAlteracao(BalanceteAudit balanceteSelecionadoPreAlteracao) {
		this.balanceteSelecionadoPreAlteracao = balanceteSelecionadoPreAlteracao;
	}

	public BalanceteAudit getBalanceteSelecionadoPosAlteracao() {
		return balanceteSelecionadoPosAlteracao;
	}

	public void setBalanceteSelecionadoPosAlteracao(BalanceteAudit balanceteSelecionadoPosAlteracao) {
		this.balanceteSelecionadoPosAlteracao = balanceteSelecionadoPosAlteracao;
	}

	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.RELATORIO_MONITORAMENTO_USUARIO;
	}
}