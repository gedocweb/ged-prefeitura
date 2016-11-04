package br.com.ged.service.audit;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.ged.domain.entidade.DepartamentoEnum;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.dto.audit.FiltroDocumentoAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;
import br.com.ged.dto.audit.RetornoMonitoramentoUsuarioDTO;
import br.com.ged.entidades.Categoria;
import br.com.ged.entidades.Documento;
import br.com.ged.entidades.auditoria.DocumentoAudit;
import br.com.ged.entidades.auditoria.DocumentoAuditPK;
import br.com.ged.generics.ConsultasDaoJpa;
import br.com.ged.generics.service.GenericService;
import br.com.ged.generics.service.GenericServiceAudit;
import br.com.ged.service.UsuarioService;

@Stateless
public class DocumentoAuditServiceImpl implements DocumentoAuditService {

	@EJB
	private ConsultasDaoJpa<DocumentoAudit> reposiroty;

	@EJB
	private UsuarioService usuarioService;

	@EJB
	private MonitoramentoAuditService monitoramentoService;

	@EJB
	private GenericServiceAudit<DocumentoAudit, Long> serviceDocumentoAudit;

	@EJB
	private GenericService<Documento, Long> serviceDocumento;

	@EJB
	private GenericService<Categoria, Long> serviceCategoria;

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoAudit> detalharOperacao(FiltroDocumentoAuditDTO filtroDocumentoAuditDTO,
			TipoOperacaoAudit tipoOperacao) {

		Date dataInicio = filtroDocumentoAuditDTO.getDataBetween().getDataInicio();
		Date dataFim = filtroDocumentoAuditDTO.getDataBetween().getDataFim();

		Long dtInicioFormat = dataInicio.getTime();
		Long dtFimFormat = dataFim.getTime();

		Long idUsuario = filtroDocumentoAuditDTO.getIdUsuario();

		Query qr = reposiroty.getEm().createNativeQuery(
				"SELECT * FROM tb_doc_audit WHERE data_hora >= :dataInicio and data_hora <= :dataFim and id_usr_audit = :idUsuario and tp_operacao = :tipoOperacao",
				DocumentoAudit.class);
		qr.setParameter("dataInicio", dtInicioFormat);
		qr.setParameter("dataFim", dtFimFormat);
		qr.setParameter("idUsuario", idUsuario);
		qr.setParameter("tipoOperacao", tipoOperacao.name());

		return qr.getResultList();
	}

	@Override
	public RetornoMonitoramentoUsuarioDTO monitoramento(FiltroMonitoramentoAuditDTO filtroMonitoramento) {

		RetornoMonitoramentoUsuarioDTO retornoMonitoramento = new RetornoMonitoramentoUsuarioDTO();

		retornoMonitoramento.setDeparamento(DepartamentoEnum.OUTROS);
		retornoMonitoramento.setNomeUsuarioPessoa(usuarioService.nomeUsuarioPorId(filtroMonitoramento.getIdUsuario()));

		retornoMonitoramento
				.setQntAlterados(monitoramentoService.countAlterados(filtroMonitoramento, DepartamentoEnum.OUTROS));
		retornoMonitoramento
				.setQntInseridos(monitoramentoService.countInseridos(filtroMonitoramento, DepartamentoEnum.OUTROS));
		retornoMonitoramento
				.setQntBaixados(monitoramentoService.countBaixados(filtroMonitoramento, DepartamentoEnum.OUTROS));
		retornoMonitoramento.setQntVisualizados(
				monitoramentoService.countVisualizados(filtroMonitoramento, DepartamentoEnum.OUTROS));
		retornoMonitoramento
				.setQntExcluidos(monitoramentoService.countExcluidos(filtroMonitoramento, DepartamentoEnum.OUTROS));
		retornoMonitoramento
				.setQntExportados(monitoramentoService.countExportados(filtroMonitoramento, DepartamentoEnum.OUTROS));

		return retornoMonitoramento;
	}

	@Override
	public Long countAudit(FiltroMonitoramentoAuditDTO filtroDocumentoAuditDTO, TipoOperacaoAudit tipoOperacao) {

		Date dataInicio = filtroDocumentoAuditDTO.getDataFiltroBetween().getDataInicio();
		Date dataFim = filtroDocumentoAuditDTO.getDataFiltroBetween().getDataFim();

		Long dtInicioFormat = dataInicio.getTime();
		Long dtFimFormat = dataFim.getTime();

		Long idUsuario = filtroDocumentoAuditDTO.getIdUsuario();

		org.hibernate.Query qr = reposiroty.getSession().createSQLQuery(
				"SELECT COUNT(tp_operacao) FROM tb_doc_audit WHERE data_hora >= :dataInicio and data_hora <= :dataFim and id_usr_audit = :idUsuario and tp_operacao = :tipoOperacao");
		qr.setParameter("dataInicio", dtInicioFormat);
		qr.setParameter("dataFim", dtFimFormat);
		qr.setParameter("idUsuario", idUsuario);
		qr.setParameter("tipoOperacao", tipoOperacao.name());

		return Long.parseLong(qr.uniqueResult().toString());
	}

	@Override
	public void auditoria(Documento documento, TipoOperacaoAudit tipoOperacaoAuditParam) {

		DocumentoAudit documentoAudit = new DocumentoAudit(documento, tipoOperacaoAuditParam, new Date().getTime());

		serviceDocumentoAudit.salvar(documentoAudit);
	}

	public void auditoriaAlterar(Documento documentoAntes, Documento documentoApos) {

		if (documentoApos.getArquivo().getId() == null) {
			/**
			 * Caso na auditoria o usuario altere o upload, será criado um novo
			 * objeto de arquivo sem ID para não ter objetos soltos a chave
			 * composta da auditoria vai identificar o arquivo pelo tipo de
			 * Operacao ALTERACAO_PRE e ALTERACAO_POS ao inves de indentificar
			 * só pelo id, por isso não precisa criar mais arquivos no banco de
			 * dados.
			 */
			documentoApos.getArquivo().setId(documentoAntes.getArquivo().getId());
		}

		Long dateMili = new Date().getTime();

		DocumentoAudit documentoAuditAntesAlteracao = new DocumentoAudit(documentoAntes, TipoOperacaoAudit.ALTERADO_PRE,
				dateMili);
		DocumentoAudit documentoAuditDepoisAlteracao = new DocumentoAudit(documentoApos, TipoOperacaoAudit.ALTERADO_POS,
				dateMili);

		serviceDocumentoAudit.salvar(documentoAuditAntesAlteracao);
		serviceDocumentoAudit.salvar(documentoAuditDepoisAlteracao);
	}

	@Override
	public DocumentoAudit getById(DocumentoAuditPK documentoAuditPK) {

		if (documentoAuditPK == null) {
			return null;
		}

		Query qr = reposiroty.getEm().createNativeQuery(
				"SELECT * FROM tb_doc_audit WHERE tp_operacao = :tipoOperacao and data_hora = :dataHora and id_documento = :idEntidade",
				DocumentoAudit.class);
		qr.setParameter("dataHora", documentoAuditPK.getDataHora());
		qr.setParameter("tipoOperacao", documentoAuditPK.getTipoOperacaoAudit().name());
		qr.setParameter("idEntidade", documentoAuditPK.getIdEntidade());

		return (DocumentoAudit) qr.getSingleResult();
	}
}