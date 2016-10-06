package br.com.ged.service.audit;

import java.math.BigInteger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroBalanceteAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;

@Stateless
public class MonitoramentoAuditServiceImpl implements MonitoramentoAuditService{
	
	@EJB
	private BalanceteAuditService balanceteAuditService;

	@Override
	public Long countAlterados(FiltroMonitoramentoAuditDTO filtro) {
		
		return countBalancete(filtro, TipoOperacaoAudit.ALTERADO_POS);
	}

	@Override
	public Long countInseridos(FiltroMonitoramentoAuditDTO filtro) {

		return countBalancete(filtro, TipoOperacaoAudit.CADASTRADO);
	}

	@Override
	public Long countBaixados(FiltroMonitoramentoAuditDTO filtro) {

		return countBalancete(filtro, TipoOperacaoAudit.BAIXADO);
	}

	@Override
	public Long countVisualizados(FiltroMonitoramentoAuditDTO filtro) {

		return countBalancete(filtro, TipoOperacaoAudit.VISUALIZADO);
	}

	@Override
	public Long countExcluidos(FiltroMonitoramentoAuditDTO filtro) {

		return countBalancete(filtro, TipoOperacaoAudit.EXCLUIDO);
	}
	
	@Override
	public Long countExportados(FiltroMonitoramentoAuditDTO filtro) {
		return countBalancete(filtro, TipoOperacaoAudit.EXPORTADO);
	}

	private Long countBalancete(FiltroMonitoramentoAuditDTO filtro, TipoOperacaoAudit tipoOp) {
		
		FiltroBalanceteAuditDTO filtroBalanceteAuditDTO = converteFiltroMonitoramentoParaFiltroAuditBalanceteDTO(filtro);
		
		Long resultadoConsulta = balanceteAuditService.countBalanceteAudit(filtroBalanceteAuditDTO,tipoOp);
		
		Long count = BigInteger.ZERO.longValue();
		
		if (resultadoConsulta != null){
			
			count  = resultadoConsulta;
		}
		return count;
	}
	
	private FiltroBalanceteAuditDTO converteFiltroMonitoramentoParaFiltroAuditBalanceteDTO(FiltroMonitoramentoAuditDTO filtro) {
		
		FiltroBalanceteAuditDTO filtroBalanceteAuditDTO = new FiltroBalanceteAuditDTO();
		
		filtroBalanceteAuditDTO.setDataBetween(filtro.getDataFiltroBetween());
		filtroBalanceteAuditDTO.setIdUsuario(filtro.getIdUsuario());
		filtroBalanceteAuditDTO.setTipoOperacaoAudit(filtro.getTipoOperacaoAudit());
		
		return filtroBalanceteAuditDTO;
	}
}