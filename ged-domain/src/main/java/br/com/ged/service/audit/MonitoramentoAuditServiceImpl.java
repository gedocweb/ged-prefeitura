package br.com.ged.service.audit;

import java.math.BigInteger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.domain.entidade.DepartamentoEnum;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroBalanceteAuditDTO;
import br.com.ged.dto.audit.FiltroDocumentoAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;

@Stateless
public class MonitoramentoAuditServiceImpl implements MonitoramentoAuditService{
	
	@EJB
	private BalanceteAuditService balanceteAuditService;
	
	@EJB
	private DocumentoAuditService documentoAuditService;

	@Override
	public Long countAlterados(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum) {
		
		if (departamentoEnum.isBalancete()){
			return countBalancete(filtro, TipoOperacaoAudit.ALTERADO_POS);
		}
		
		if (departamentoEnum.isOutros()){
			return countDocumento(filtro, TipoOperacaoAudit.ALTERADO_POS);
		}
		
		return null;
	}

	@Override
	public Long countInseridos(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum) {

		if (departamentoEnum.isBalancete()){
			return countBalancete(filtro, TipoOperacaoAudit.CADASTRADO);
		}
		
		if (departamentoEnum.isOutros()){
			return countDocumento(filtro, TipoOperacaoAudit.CADASTRADO);
		}
		
		return null;
	}

	@Override
	public Long countBaixados(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum) {

		if (departamentoEnum.isBalancete()){
			return countBalancete(filtro, TipoOperacaoAudit.BAIXADO);
		}
		
		if (departamentoEnum.isOutros()){
			return countDocumento(filtro, TipoOperacaoAudit.BAIXADO);
		}
		
		return null;
	}

	@Override
	public Long countVisualizados(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum) {

		if (departamentoEnum.isBalancete()){
			return countBalancete(filtro, TipoOperacaoAudit.VISUALIZADO);
		}
		
		if (departamentoEnum.isOutros()){
			return countDocumento(filtro, TipoOperacaoAudit.VISUALIZADO);
		}
		
		return null;
	}

	@Override
	public Long countExcluidos(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum) {

		if (departamentoEnum.isBalancete()){
			return countBalancete(filtro, TipoOperacaoAudit.EXCLUIDO);
		}
		
		if (departamentoEnum.isOutros()){
			return countDocumento(filtro, TipoOperacaoAudit.EXCLUIDO);
		}
		
		return null;
	}
	
	@Override
	public Long countExportados(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum) {
		
		if (departamentoEnum.isBalancete()){
			return countBalancete(filtro, TipoOperacaoAudit.EXPORTADO);
		}
		
		if (departamentoEnum.isOutros()){
			return countDocumento(filtro, TipoOperacaoAudit.EXPORTADO);
		}
		
		return null;
	}
	
	private Long countDocumento(FiltroMonitoramentoAuditDTO filtro, TipoOperacaoAudit tipoOp) {
		
		FiltroDocumentoAuditDTO filtroDocumentoAuditDTO = converteFiltroMonitoramentoParaFiltroAuditDocumentoDTO(filtro);
		
		Long resultadoConsulta = documentoAuditService.countDocumentoAudit(filtroDocumentoAuditDTO,tipoOp);		
		Long count = BigInteger.ZERO.longValue();
		
		if (resultadoConsulta != null){
			
			count  = resultadoConsulta;
		}
		return count;
	}

	private FiltroDocumentoAuditDTO converteFiltroMonitoramentoParaFiltroAuditDocumentoDTO(FiltroMonitoramentoAuditDTO filtro) {
		FiltroDocumentoAuditDTO filtroDTO = new FiltroDocumentoAuditDTO();
		
		filtroDTO.setDataBetween(filtro.getDataFiltroBetween());
		filtroDTO.setIdUsuario(filtro.getIdUsuario());
		filtroDTO.setTipoOperacaoAudit(filtro.getTipoOperacaoAudit());
		
		return filtroDTO;
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