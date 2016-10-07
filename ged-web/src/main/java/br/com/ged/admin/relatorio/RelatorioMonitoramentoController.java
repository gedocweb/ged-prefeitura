package br.com.ged.admin.relatorio;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ged.domain.Pagina;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroBalanceteAuditDTO;
import br.com.ged.dto.audit.FiltroDocumentoAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;
import br.com.ged.dto.audit.RetornoMonitoramentoUsuarioDTO;
import br.com.ged.entidades.auditoria.BalanceteAudit;
import br.com.ged.entidades.auditoria.DocumentoAudit;
import br.com.ged.excecao.NegocioException;
import br.com.ged.service.audit.BalanceteAuditService;
import br.com.ged.service.audit.DocumentoAuditService;
import br.com.ged.util.DataUtil;

@ManagedBean(name="painelRelatorioMonitoramento")
@ViewScoped
public class RelatorioMonitoramentoController extends RelatorioSuperController{
	
	@EJB
	private BalanceteAuditService balanceteAuditService;
	
	@EJB
	private DocumentoAuditService documentoAuditService;
	
	private List<BalanceteAudit> listDetalheBalanceteAudit;
	private List<DocumentoAudit> listDetalheDocumentoAudit;
	
	@PostConstruct
	public void inicio(){
		
		periodoMesAtual = Boolean.TRUE;
		
		filtroMonitoramento = new FiltroMonitoramentoAuditDTO();
		filtroBalanceteAuditDTO = new FiltroBalanceteAuditDTO();
		
		retornoMonitoramento = new RetornoMonitoramentoUsuarioDTO();
		renderedDetalhar = Boolean.FALSE;
	}
	
	public void monitorar(){
		
		renderedDetalhar = Boolean.FALSE;
		retornoMonitoramento = new RetornoMonitoramentoUsuarioDTO();
		
		try {
			
			validadorView.valida(idGrupoSelecionado, idUsuarioSelecionado, departamentoSelecionado);
			
			FiltroMonitoramentoAuditDTO filtroMonitoramento  = montaFiltroConsulta();
			
			if (departamentoSelecionado.isBalancete() && filtroMonitoramento.getIdUsuario() != null){
				
				retornoMonitoramento = balanceteAuditService.monitoramento(filtroMonitoramento);
			}
			
			if (departamentoSelecionado.isOutros() && filtroMonitoramento.getIdUsuario() != null){
				
				retornoMonitoramento = documentoAuditService.monitoramento(filtroMonitoramento);
			}

		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}

	private FiltroMonitoramentoAuditDTO montaFiltroConsulta() {
		
		if (!periodoMesAtual){
			
			filtroMonitoramento.getDataFiltroBetween().setDataInicio(periodoInicial);
			filtroMonitoramento.getDataFiltroBetween().setDataFim(periodoFinal);
			
		}else{
			
			periodoInicial = null;
			periodoFinal = null;
			
			filtroMonitoramento.getDataFiltroBetween().setDataInicio(DataUtil.primeiraDataDoMesAtual());
			filtroMonitoramento.getDataFiltroBetween().setDataFim(DataUtil.ultimaDataDoMesAtual());
		}
		
		filtroMonitoramento.setIdUsuario(idUsuarioSelecionado);
		
		return filtroMonitoramento;
	}

	public void detalhar(TipoOperacaoAudit operacaoAudit){
		
		if (departamentoSelecionado.isBalancete()){
			
			FiltroBalanceteAuditDTO filtroBalanceteAuditDTO = new FiltroBalanceteAuditDTO();
			
			filtroBalanceteAuditDTO.setDataBetween(filtroMonitoramento.getDataFiltroBetween());
			filtroBalanceteAuditDTO.setIdUsuario(idUsuarioSelecionado);

			listDetalheBalanceteAudit = balanceteAuditService.detalharOperacao(filtroBalanceteAuditDTO, operacaoAudit);
			renderedDetalhar = Boolean.TRUE;
		}
		
		if (departamentoSelecionado.isOutros()){
			
			FiltroDocumentoAuditDTO filtroDocumentoAuditDTO = new FiltroDocumentoAuditDTO();
			
			filtroDocumentoAuditDTO.setDataBetween(filtroMonitoramento.getDataFiltroBetween());
			filtroDocumentoAuditDTO.setIdUsuario(idUsuarioSelecionado);

			listDetalheDocumentoAudit = documentoAuditService.detalharOperacao(filtroDocumentoAuditDTO, operacaoAudit);
			renderedDetalhar = Boolean.TRUE;
		}
	}

	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.RELATORIO_MONITORAMENTO;
	}

	public List<BalanceteAudit> getListDetalheBalanceteAudit() {
		return listDetalheBalanceteAudit;
	}

	public List<DocumentoAudit> getListDetalheDocumentoAudit() {
		return listDetalheDocumentoAudit;
	}
}