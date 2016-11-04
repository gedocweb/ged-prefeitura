package br.com.ged.entidades.auditoria;
 
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.ged.domain.entidade.Situacao;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.entidades.Lei;
import br.com.ged.generics.EntidadeBasicaAudit;
 
@Entity
@Table(name = "tb_lei_audit")
public class LeiAudit extends EntidadeBasicaAudit{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@EmbeddedId
	private LeiAuditPK id;
	
	@Column(name="objeto")
	private String objeto;

	@Column(name="ano")
	private Integer ano;
	
	@Column(name="num_lei")
	private String numeroLei;
	
	@Enumerated(EnumType.STRING)
	@Column(name="situacao")
	private Situacao situacao;
	
	@Column(name="observacao")
	private String observacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_indexacao")
	private Date dataIndexacao;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumns({
		@JoinColumn(name = "tipo_op_audit_arq", referencedColumnName="tp_operacao"),
	  	@JoinColumn(name = "id_ent_arq", referencedColumnName="id_arq_lei"),
	  	@JoinColumn(name = "id_dt_hora_arq", referencedColumnName="data_hora")
	})
	private ArquivoLeiAudit arquivo;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumns({
  	  @JoinColumn(name = "tipo_op_usr", referencedColumnName="tp_operacao"),
  	  @JoinColumn(name = "id_ent_usr_audit", referencedColumnName="id_usuario"),
  	  @JoinColumn(name = "id_dt_hora_usr", referencedColumnName="data_hora")
	})
	private UsuarioAudit usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_ultima_alteracao")
	private Date dataUltimaAlteracao;
	
	public LeiAudit(Lei lei, TipoOperacaoAudit tipoOperacaoAuditParam, long dataMili) {
		
		LeiAuditPK pk = new LeiAuditPK();
		
		pk.setDataHora(dataMili);
		pk.setIdEntidade(lei.getId());
		pk.setTipoOperacaoAudit(tipoOperacaoAuditParam);
		
		this.setId(pk);
		
		this.setAno(lei.getAno());
		this.setArquivo(new ArquivoLeiAudit(lei.getArquivo(), tipoOperacaoAuditParam, dataMili));
		this.setDataIndexacao(lei.getDataIndexacao());
		this.setDataUltimaAlteracao(lei.getDataUltimaAlteracao());
		this.setNumeroLei(lei.getNumeroLei());
		this.setObjeto(lei.getObjeto());
		this.setObservacao(lei.getObservacao());
		this.setSituacao(lei.getSituacao());
		this.setUsuario(new UsuarioAudit(lei.getUsuario(), tipoOperacaoAuditParam, dataMili));
	}
	
	public LeiAudit() {
	}
	
	@Transient
	public String getDataUltimaAlteracaoFormat() {
		
		String dataFormat = null;
		
		if (this.getDataUltimaAlteracao() != null){
			
			dataFormat = formataData(this.getDataUltimaAlteracao(), "dd/MM/yyyy hh:mm:ss");
		}
		
		return dataFormat;
	}
	
	@Transient
	public String getDataIndexacaoFormat() {
		
		String dataFormat = null;
		
		if (this.getDataIndexacao() != null){
			
			dataFormat = formataData(this.getDataIndexacao(),"dd/MM/yyyy");
		}
		
		return dataFormat;
	}
	
	private String formataData(Date date, String pattern) {
		String dataUltimaAlteracaoFormat;
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		
		dataUltimaAlteracaoFormat = dateFormat.format(date);
		return dataUltimaAlteracaoFormat;
	}

	public LeiAuditPK getId() {
		return id;
	}

	public void setId(LeiAuditPK id) {
		this.id = id;
	}

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataIndexacao() {
		return dataIndexacao;
	}

	public void setDataIndexacao(Date dataIndexacao) {
		this.dataIndexacao = dataIndexacao;
	}

	public ArquivoLeiAudit getArquivo() {
		return arquivo;
	}

	public void setArquivo(ArquivoLeiAudit arquivo) {
		this.arquivo = arquivo;
	}

	public UsuarioAudit getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioAudit usuario) {
		this.usuario = usuario;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public String getNumeroLei() {
		return numeroLei;
	}

	public void setNumeroLei(String numeroLei) {
		this.numeroLei = numeroLei;
	}
}