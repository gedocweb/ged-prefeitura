package br.com.ged.service.audit;

import java.math.BigInteger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.domain.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroBalanceteAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;

@Stateless
public class MonitoramentoAuditServiceImpl implements MonitoramentoAuditService{
	
	@EJB
	private BalanceteAuditService balanceteAuditService;

	@Override
	public Integer countAlterados(FiltroMonitoramentoAuditDTO filtro) {
		
		Integer count = BigInteger.ZERO.intValue();
		
		if (validaFiltro(filtro, TipoOperacaoAudit.ALTERACAO)){
			
			FiltroBalanceteAuditDTO filtroBalanceteAuditDTO = converteFiltroMonitoramentoParaFiltroAuditBalanceteDTO(filtro);
			
			Integer resultadoConsulta = balanceteAuditService.countBalanceteAudit(filtroBalanceteAuditDTO);
			
			if (resultadoConsulta != null){
				
				final int METADE_DOS_REGISTROS_DO_ALTERAR = 2;
				
				//Está dividido pq a auditoria do modo alterar, 
				//salva o objeto antes da alteração e pós operação
				//deixando a auditoria duplicada.
				count = resultadoConsulta / METADE_DOS_REGISTROS_DO_ALTERAR;
			}
			
		}
		
		return count;
	}

	@Override
	public Integer countInseridos(FiltroMonitoramentoAuditDTO filtro) {

		Integer count = BigInteger.ZERO.intValue();
		
		if (validaFiltro(filtro, TipoOperacaoAudit.CADASTRO)){
			
			FiltroBalanceteAuditDTO filtroBalanceteAuditDTO = converteFiltroMonitoramentoParaFiltroAuditBalanceteDTO(filtro);
			
			Integer resultadoConsulta = balanceteAuditService.countBalanceteAudit(filtroBalanceteAuditDTO);
			
			if (resultadoConsulta != null){
				
				count = resultadoConsulta;
			}
			
		}
		
		return count;
	}

	@Override
	public Integer countBaixados(FiltroMonitoramentoAuditDTO filtro) {

		Integer count = BigInteger.ZERO.intValue();
		
		if (validaFiltro(filtro, TipoOperacaoAudit.BAIXADOS)){
			
			FiltroBalanceteAuditDTO filtroBalanceteAuditDTO = converteFiltroMonitoramentoParaFiltroAuditBalanceteDTO(filtro);
			
			Integer resultadoConsulta = balanceteAuditService.countBalanceteAudit(filtroBalanceteAuditDTO);
			
			if (resultadoConsulta != null){
				
				count = resultadoConsulta;
			}
			
		}
		
		return count;
	}

	@Override
	public Integer countVisualizados(FiltroMonitoramentoAuditDTO filtro) {

		Integer count = BigInteger.ZERO.intValue();
		
		if (validaFiltro(filtro, TipoOperacaoAudit.VISUALIZACAO)){
			
			FiltroBalanceteAuditDTO filtroBalanceteAuditDTO = converteFiltroMonitoramentoParaFiltroAuditBalanceteDTO(filtro);
			
			Integer resultadoConsulta = balanceteAuditService.countBalanceteAudit(filtroBalanceteAuditDTO);
			
			if (resultadoConsulta != null){
				
				count = resultadoConsulta;
			}
			
		}
		
		return count;
	}

	@Override
	public Integer countExcluidos(FiltroMonitoramentoAuditDTO filtro) {

		Integer count = BigInteger.ZERO.intValue();
		
		if (validaFiltro(filtro, TipoOperacaoAudit.EXCLUIR)){
			
			FiltroBalanceteAuditDTO filtroBalanceteAuditDTO = converteFiltroMonitoramentoParaFiltroAuditBalanceteDTO(filtro);
			
			Integer resultadoConsulta = balanceteAuditService.countBalanceteAudit(filtroBalanceteAuditDTO);
			
			if (resultadoConsulta != null){
				
				count = resultadoConsulta;
			}
			
		}
		
		return count;
	}
	
	private boolean validaFiltro(FiltroMonitoramentoAuditDTO filtro, TipoOperacaoAudit tipoOperacaoAudit) {
		return filtro != null && tipoOperacaoAudit.equals(filtro.getTipoOperacaoAudit());
	}

	private FiltroBalanceteAuditDTO converteFiltroMonitoramentoParaFiltroAuditBalanceteDTO(FiltroMonitoramentoAuditDTO filtro) {
		
		FiltroBalanceteAuditDTO filtroBalanceteAuditDTO = new FiltroBalanceteAuditDTO();
		
		filtroBalanceteAuditDTO.setDataBetween(filtro.getDataFiltroBetween());
		filtroBalanceteAuditDTO.setIdUsuario(filtro.getIdUsuario());
		filtroBalanceteAuditDTO.setTipoOperacaoAudit(filtro.getTipoOperacaoAudit());
		
		return filtroBalanceteAuditDTO;
	}
}