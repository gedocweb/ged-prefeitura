package br.com.ged.service.audit;

import java.math.BigInteger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.ged.domain.entidade.DepartamentoEnum;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;

@Stateless
public class MonitoramentoAuditServiceImpl implements MonitoramentoAuditService{
	
	@EJB
	private BalanceteAuditService balanceteAuditService;
	
	@EJB
	private DocumentoAuditService documentoAuditService;
	
	@EJB
	private LeiAuditService leiAuditService;

	@Override
	public Long countAlterados(FiltroMonitoramentoAuditDTO filtro, DepartamentoEnum departamentoEnum) {
		
		if (departamentoEnum.isBalancete()){
			return countBalancete(filtro, TipoOperacaoAudit.ALTERADO_POS);
		}
		
		if (departamentoEnum.isOutros()){
			return countDocumento(filtro, TipoOperacaoAudit.ALTERADO_POS);
		}
		
		if (departamentoEnum.isLei()){
			return countLei(filtro, TipoOperacaoAudit.ALTERADO_POS);
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
		
		if (departamentoEnum.isLei()){
			return countLei(filtro, TipoOperacaoAudit.CADASTRADO);
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
		
		if (departamentoEnum.isLei()){
			return countLei(filtro, TipoOperacaoAudit.BAIXADO);
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
		
		if (departamentoEnum.isLei()){
			return countLei(filtro, TipoOperacaoAudit.VISUALIZADO);
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
		
		if (departamentoEnum.isLei()){
			return countLei(filtro, TipoOperacaoAudit.EXCLUIDO);
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
		
		if (departamentoEnum.isLei()){
			return countLei(filtro, TipoOperacaoAudit.EXPORTADO);
		}
		
		return null;
	}
	
	private Long countDocumento(FiltroMonitoramentoAuditDTO filtro, TipoOperacaoAudit tipoOp) {
		
		Long resultadoConsulta = documentoAuditService.countAudit(filtro,tipoOp);		
		Long count = BigInteger.ZERO.longValue();
		
		if (resultadoConsulta != null){
			
			count  = resultadoConsulta;
		}
		return count;
	}

	private Long countLei(FiltroMonitoramentoAuditDTO filtro, TipoOperacaoAudit tipoOp) {
		
		Long resultadoConsulta = leiAuditService.countLeiAudit(filtro,tipoOp);
		
		Long count = BigInteger.ZERO.longValue();
		
		if (resultadoConsulta != null){
			
			count  = resultadoConsulta;
		}
		return count;
	}

	private Long countBalancete(FiltroMonitoramentoAuditDTO filtro, TipoOperacaoAudit tipoOp) {
		
		Long resultadoConsulta = balanceteAuditService.countBalanceteAudit(filtro,tipoOp);
		
		Long count = BigInteger.ZERO.longValue();
		
		if (resultadoConsulta != null){
			
			count  = resultadoConsulta;
		}
		return count;
	}
}