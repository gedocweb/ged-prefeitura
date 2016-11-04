package br.com.ged.service.audit;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.ged.domain.entidade.DepartamentoEnum;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroLeiAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;
import br.com.ged.dto.audit.RetornoMonitoramentoUsuarioDTO;
import br.com.ged.entidades.Lei;
import br.com.ged.entidades.auditoria.LeiAudit;
import br.com.ged.entidades.auditoria.LeiAuditPK;
import br.com.ged.generics.ConsultasDaoJpa;
import br.com.ged.generics.service.GenericService;
import br.com.ged.generics.service.GenericServiceAudit;
import br.com.ged.service.UsuarioService;

@Stateless
public class LeiAuditServiceImpl implements LeiAuditService{
	
	@EJB
	private ConsultasDaoJpa<LeiAudit> reposiroty;
	
	@EJB
	private UsuarioService usuarioService;
	
	@EJB
	private MonitoramentoAuditService monitoramentoService;
	
	@EJB
	private GenericServiceAudit<LeiAudit, Long> serviceLeiAudit;
	
	@EJB
	private GenericService<Lei, Long> serviceLei;

	@SuppressWarnings("unchecked")
	@Override
	public List<LeiAudit> detalharOperacao(FiltroLeiAuditDTO filtroLeiAuditDTO, TipoOperacaoAudit tipoOperacao) {
		
		Date dataInicio = filtroLeiAuditDTO.getDataBetween().getDataInicio();
		Date dataFim = filtroLeiAuditDTO.getDataBetween().getDataFim();
		
		Long dtInicioFormat = dataInicio.getTime();
		Long dtFimFormat = dataFim.getTime();
		
		Long idUsuario = filtroLeiAuditDTO.getIdUsuario();
		
		Query qr = reposiroty.getEm().createNativeQuery("SELECT * FROM tb_lei_audit WHERE data_hora >= :dataInicio and data_hora <= :dataFim and id_ent_usr = :idUsuario and tp_operacao = :tipoOperacao", LeiAudit.class);
		qr.setParameter("dataInicio", dtInicioFormat);
		qr.setParameter("dataFim", dtFimFormat);
		qr.setParameter("idUsuario", idUsuario);
		qr.setParameter("tipoOperacao", tipoOperacao.name());
		
		return qr.getResultList();
	}

	@Override
	public RetornoMonitoramentoUsuarioDTO monitoramento (FiltroMonitoramentoAuditDTO filtroMonitoramento) {
		
		RetornoMonitoramentoUsuarioDTO retornoMonitoramento = new RetornoMonitoramentoUsuarioDTO();
		
		retornoMonitoramento.setDeparamento(DepartamentoEnum.LEI);
		retornoMonitoramento.setNomeUsuarioPessoa(usuarioService.nomeUsuarioPorId(filtroMonitoramento.getIdUsuario()));
		
		retornoMonitoramento.setQntAlterados(monitoramentoService.countAlterados(filtroMonitoramento, DepartamentoEnum.LEI));
		retornoMonitoramento.setQntInseridos(monitoramentoService.countInseridos(filtroMonitoramento, DepartamentoEnum.LEI));
		retornoMonitoramento.setQntBaixados(monitoramentoService.countBaixados(filtroMonitoramento, DepartamentoEnum.LEI));
		retornoMonitoramento.setQntVisualizados(monitoramentoService.countVisualizados(filtroMonitoramento, DepartamentoEnum.LEI));
		retornoMonitoramento.setQntExcluidos(monitoramentoService.countExcluidos(filtroMonitoramento, DepartamentoEnum.LEI));
		retornoMonitoramento.setQntExportados(monitoramentoService.countExportados(filtroMonitoramento, DepartamentoEnum.LEI));

		return retornoMonitoramento;
	}

	@Override
	public Long countLeiAudit(FiltroMonitoramentoAuditDTO filtroLeiAuditDTO, TipoOperacaoAudit tipoOperacao) {
		
		Date dataInicio = filtroLeiAuditDTO.getDataFiltroBetween().getDataInicio();
		Date dataFim = filtroLeiAuditDTO.getDataFiltroBetween().getDataFim();
		
		Long dtInicioFormat = dataInicio.getTime();
		Long dtFimFormat = dataFim.getTime();
		
		Long idUsuario = filtroLeiAuditDTO.getIdUsuario();
		
		org.hibernate.Query qr = reposiroty.getSession().createSQLQuery("SELECT COUNT(tp_operacao) FROM tb_lei_audit WHERE data_hora >= :dataInicio and data_hora <= :dataFim and id_ent_usr = :idUsuario and tp_operacao = :tipoOperacao");
		qr.setParameter("dataInicio", dtInicioFormat);
		qr.setParameter("dataFim", dtFimFormat);
		qr.setParameter("idUsuario", idUsuario);
		qr.setParameter("tipoOperacao", tipoOperacao.name());
		
		return Long.parseLong(qr.uniqueResult().toString());
	}

	@Override
	public void auditoria(Lei lei, TipoOperacaoAudit tipoOperacaoAuditParam) {
		
		LeiAudit leiAudit = new LeiAudit(lei, tipoOperacaoAuditParam, new Date().getTime());
		serviceLeiAudit.salvar(leiAudit);
	}
	
	public void auditoriaAlteracao(Lei leiAntes, Lei leiApos) {
		
		if (leiApos.getArquivo().getId() == null){
			/**
			 * Caso na auditoria o usuario altere o upload, será criado um novo objeto de arquivo sem ID
			 * para não ter objetos soltos a chave composta da auditoria vai identificar o arquivo pelo tipo de Operacao
			 * ALTERACAO_PRE e ALTERACAO_POS ao inves de indentificar só pelo id, por isso não precisa criar mais arquivos no banco de dados.
			 */
			leiApos.getArquivo().setId(leiAntes.getArquivo().getId());
		}
		
		Long dateMili = new Date().getTime();
		
		LeiAudit leiAuditAntesAlteracao = new LeiAudit(leiAntes, TipoOperacaoAudit.ALTERADO_PRE, dateMili);
		LeiAudit leiAuditDepoisAlteracao = new LeiAudit(leiApos, TipoOperacaoAudit.ALTERADO_POS, dateMili);
		
		serviceLeiAudit.salvar(leiAuditAntesAlteracao);
		serviceLeiAudit.salvar(leiAuditDepoisAlteracao);
	}

	@Override
	public LeiAudit getById(LeiAuditPK leiAuditPK) {
		
		Query qr = reposiroty.getEm().createNativeQuery("SELECT * FROM tb_lei_audit WHERE tp_operacao = :tipoOperacao and data_hora = :dataHora and id_lei = :idEntidade", LeiAudit.class);
		qr.setParameter("dataHora", leiAuditPK.getDataHora());
		qr.setParameter("tipoOperacao", leiAuditPK.getTipoOperacaoAudit().name());
		qr.setParameter("idEntidade", leiAuditPK.getIdEntidade());
		
		return (LeiAudit) qr.getSingleResult();
	}
}