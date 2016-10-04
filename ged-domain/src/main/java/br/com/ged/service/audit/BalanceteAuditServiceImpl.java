package br.com.ged.service.audit;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.ged.domain.DepartamentoEnum;
import br.com.ged.domain.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroBalanceteAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;
import br.com.ged.dto.audit.RetornoMonitoramentoUsuarioDTO;
import br.com.ged.entidades.Balancete;
import br.com.ged.entidades.auditoria.BalanceteAudit;
import br.com.ged.entidades.auditoria.BalanceteAuditPK;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<BalanceteAudit> detalharOperacao(FiltroBalanceteAuditDTO filtroBalanceteAuditDTO, TipoOperacaoAudit tipoOperacao) {
		
		Date dataInicio = filtroBalanceteAuditDTO.getDataBetween().getDataInicio();
		Date dataFim = filtroBalanceteAuditDTO.getDataBetween().getDataFim();
		
		Long dtInicioFormat = dataInicio.getTime();
		Long dtFimFormat = dataFim.getTime();
		
		Long idUsuario = filtroBalanceteAuditDTO.getIdUsuario();
		
		Query qr = reposiroty.getEm().createNativeQuery("SELECT * FROM tb_balancete_audit WHERE data_hora >= :dataInicio and data_hora <= :dataFim and id_ent_usr = :idUsuario and tp_operacao = :tipoOperacao", BalanceteAudit.class);
		qr.setParameter("dataInicio", dtInicioFormat);
		qr.setParameter("dataFim", dtFimFormat);
		qr.setParameter("idUsuario", idUsuario);
		qr.setParameter("tipoOperacao", tipoOperacao.name());
		
		return qr.getResultList();
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
		retornoMonitoramento.setQntExportados(monitoramentoService.countExportados(filtroMonitoramento));

		return retornoMonitoramento;
	}

	@Override
	public Long countBalanceteAudit(FiltroBalanceteAuditDTO filtroBalanceteAuditDTO, TipoOperacaoAudit tipoOperacao) {
		
		Date dataInicio = filtroBalanceteAuditDTO.getDataBetween().getDataInicio();
		Date dataFim = filtroBalanceteAuditDTO.getDataBetween().getDataFim();
		
		Long dtInicioFormat = dataInicio.getTime();
		Long dtFimFormat = dataFim.getTime();
		
		Long idUsuario = filtroBalanceteAuditDTO.getIdUsuario();
		
		org.hibernate.Query qr = reposiroty.getSession().createSQLQuery("SELECT COUNT(tp_operacao) FROM tb_balancete_audit WHERE data_hora >= :dataInicio and data_hora <= :dataFim and id_ent_usr = :idUsuario and tp_operacao = :tipoOperacao");
		qr.setParameter("dataInicio", dtInicioFormat);
		qr.setParameter("dataFim", dtFimFormat);
		qr.setParameter("idUsuario", idUsuario);
		qr.setParameter("tipoOperacao", tipoOperacao.name());
		
		return Long.parseLong(qr.uniqueResult().toString());
	}

	@Override
	public void auditoriaBalancete(Balancete balancete, TipoOperacaoAudit tipoOperacaoAuditParam) {
		
		BalanceteAudit balanceteAudit = new BalanceteAudit(balancete, tipoOperacaoAuditParam, new Date().getTime());
		serviceBalanceteAudit.salvar(balanceteAudit);
	}
	
	public void auditoriaBalancete(Balancete balanceteAntes, Balancete balanceteApos) {
		
		if (balanceteApos.getArquivo().getId() == null){
			/**
			 * Caso na auditoria o usuario altere o upload, será criado um novo objeto de arquivo sem ID
			 * para não ter objetos soltos a chave composta da auditoria vai identificar o arquivo pelo tipo de Operacao
			 * ALTERACAO_PRE e ALTERACAO_POS ao inves de indentificar só pelo id, por isso não precisa criar mais arquivos no banco de dados.
			 */
			balanceteApos.getArquivo().setId(balanceteAntes.getArquivo().getId());
		}
		
		Long dateMili = new Date().getTime();
		
		BalanceteAudit balanceteAuditAntesAlteracao = new BalanceteAudit(balanceteAntes, TipoOperacaoAudit.ALTERADO_PRE, dateMili);
		BalanceteAudit balanceteAuditDepoisAlteracao = new BalanceteAudit(balanceteApos, TipoOperacaoAudit.ALTERADO_POS, dateMili);
		
		serviceBalanceteAudit.salvar(balanceteAuditAntesAlteracao);
		serviceBalanceteAudit.salvar(balanceteAuditDepoisAlteracao);
	}

	@Override
	public BalanceteAudit getById(BalanceteAuditPK balanceteAuditPK) {
		
		Query qr = reposiroty.getEm().createNativeQuery("SELECT * FROM tb_balancete_audit WHERE tp_operacao = :tipoOperacao and data_hora = :dataHora and id_balancete = :idEntidade", BalanceteAudit.class);
		qr.setParameter("dataHora", balanceteAuditPK.getDataHora());
		qr.setParameter("tipoOperacao", balanceteAuditPK.getTipoOperacaoAudit().name());
		qr.setParameter("idEntidade", balanceteAuditPK.getIdEntidade());
		
		return (BalanceteAudit) qr.getSingleResult();
	}
}