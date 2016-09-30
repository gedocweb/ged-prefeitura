package br.com.ged.service.audit;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.domain.DepartamentoEnum;
import br.com.ged.domain.Tempo;
import br.com.ged.domain.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroBalanceteAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;
import br.com.ged.dto.audit.RetornoMonitoramentoUsuarioDTO;
import br.com.ged.entidades.Balancete;
import br.com.ged.entidades.auditoria.BalanceteAudit;
import br.com.ged.generics.ConsultasDaoJpa;
import br.com.ged.generics.service.GenericService;
import br.com.ged.generics.service.GenericServiceAudit;
import br.com.ged.service.UsuarioService;

@Stateless
public class BalanceteAuditServiceImpl implements BalanceteAuditService{
	
	@EJB
	private ConsultasDaoJpa<BalanceteAudit> reposiroty;
	
	@EJB
	private UsuarioService usuarioService;
	
	@EJB
	private MonitoramentoAuditService monitoramentoService;
	
	@EJB
	private GenericServiceAudit<BalanceteAudit, Long> serviceBalanceteAudit;
	
	@EJB
	private GenericService<Balancete, Long> serviceBalancete;

	@Override
	public List<BalanceteAudit> pesquisar(FiltroBalanceteAuditDTO filtro, String... hbInitialize) {
		return reposiroty.filtrarPesquisa(filtro, BalanceteAudit.class, hbInitialize);
	}

	@Override
	public RetornoMonitoramentoUsuarioDTO monitoramento (FiltroMonitoramentoAuditDTO filtroMonitoramento) {
		
		RetornoMonitoramentoUsuarioDTO retornoMonitoramento = new RetornoMonitoramentoUsuarioDTO();
		
		retornoMonitoramento.setDeparamento(DepartamentoEnum.BALANCETE);
		retornoMonitoramento.setNomeUsuarioPessoa(usuarioService.nomeUsuarioPorId(filtroMonitoramento.getIdUsuario()));
		
		retornoMonitoramento.setQntAlterados(monitoramentoService.countAlterados(filtroMonitoramento));
		retornoMonitoramento.setQntInseridos(monitoramentoService.countInseridos(filtroMonitoramento));
		retornoMonitoramento.setQntBaixados(monitoramentoService.countBaixados(filtroMonitoramento));
		retornoMonitoramento.setQntVisualizados(monitoramentoService.countVisualizados(filtroMonitoramento));
		retornoMonitoramento.setQntExcluidos(monitoramentoService.countExcluidos(filtroMonitoramento));

		return retornoMonitoramento;
	}

	@Override
	public Integer countBalanceteAudit(FiltroBalanceteAuditDTO filtroBalanceteAuditDTO) {
		
		return reposiroty.countPesquisa(filtroBalanceteAuditDTO, BalanceteAudit.class);
	}

	@Override
	public void auditoriaBalancete(Balancete balancete, TipoOperacaoAudit tipoOperacaoAuditParam) {
		
		if (auditoriaOperacaoAlterar(balancete)){
			
			if (TipoOperacaoAudit.ALTERACAO.equals(tipoOperacaoAuditParam)){
				
				auditoriaOpAlterar(balancete);
			}
		}
		
		if (auditoriaOperacaoCadastrar(balancete)){
			
			if (TipoOperacaoAudit.CADASTRO.equals(tipoOperacaoAuditParam)){
				
				finalizaAuditoria(balancete, tipoOperacaoAuditParam);
			}
		}
		
		finalizaAuditoria(balancete, tipoOperacaoAuditParam);
	}

	private void finalizaAuditoria(Balancete balancete, TipoOperacaoAudit tipoOperacaoAuditParam) {
		
		BalanceteAudit balanceteAudit = new BalanceteAudit(balancete, tipoOperacaoAuditParam);
		serviceBalanceteAudit.salvar(balanceteAudit);
	}

	private void auditoriaOpAlterar(Balancete balancete) {
		
		Balancete balanceteAntes = serviceBalancete.getById(Balancete.class, balancete.getId());
		Balancete balanceteApos = balancete;
		TipoOperacaoAudit tipoOperacaoAudit = TipoOperacaoAudit.ALTERACAO;

		BalanceteAudit balanceteAuditAntesAlteracao = new BalanceteAudit(balanceteAntes, TipoOperacaoAudit.ALTERACAO);
		BalanceteAudit balanceteAuditDepoisAlteracao = new BalanceteAudit(balanceteApos, TipoOperacaoAudit.ALTERACAO);
		
		balanceteAuditAntesAlteracao.setTempo(Tempo.ANTES);
		balanceteAuditAntesAlteracao.setTipoOperacaoAudit(tipoOperacaoAudit);
		
		serviceBalanceteAudit.salvar(balanceteAuditAntesAlteracao);
		
		balanceteAuditAntesAlteracao.setTempo(Tempo.DEPOIS);
		balanceteAuditAntesAlteracao.setTipoOperacaoAudit(tipoOperacaoAudit);
		
		serviceBalanceteAudit.salvar(balanceteAuditDepoisAlteracao);
	}

	private boolean auditoriaOperacaoCadastrar(Balancete balancete) {
		return !auditoriaOperacaoAlterar(balancete);
	}

	private boolean auditoriaOperacaoAlterar(Balancete balancete) {
		return balancete.getId() != null;
	}
}