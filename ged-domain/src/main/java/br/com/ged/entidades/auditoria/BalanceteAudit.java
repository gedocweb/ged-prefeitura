package br.com.ged.entidades.auditoria;
 
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

import br.com.ged.domain.entidade.MesEnum;
import br.com.ged.domain.entidade.OrgaoEnum;
import br.com.ged.domain.entidade.Situacao;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.entidades.Balancete;
import br.com.ged.generics.EntidadeBasicaAudit;
import br.com.ged.util.DataUtil;
 
@Entity
@Table(name = "tb_balancete_audit")
public class BalanceteAudit extends EntidadeBasicaAudit{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@EmbeddedId
	private BalanceteAuditPK id;
	
	@Column(name="volume")
	private String volume;

	@Enumerated(EnumType.ORDINAL)
	@Column(name="mes")
	private MesEnum mes;
	
	@Column(name="ano")
	private Integer ano;
	
	@Enumerated(EnumType.STRING)
	@Column(name="orgao")
	private OrgaoEnum orgao;
	
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
	  	@JoinColumn(name = "id_ent_arq", referencedColumnName="id_arq_balanc"),
	  	@JoinColumn(name = "id_dt_hora_arq", referencedColumnName="data_hora")
	})
	private ArquivoBalanceteAudit arquivo;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumns({
  	  @JoinColumn(name = "tipo_op_audit_usr", referencedColumnName="tp_operacao"),
  	  @JoinColumn(name = "id_ent_usr", referencedColumnName="id_usuario"),
  	  @JoinColumn(name = "id_dt_hora_usr", referencedColumnName="data_hora")
	})
	private UsuarioAudit usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_ultima_alteracao")
	private Date dataUltimaAlteracao;
	
	public BalanceteAudit(Balancete balancete, TipoOperacaoAudit tipoOperacaoAudit, Long dateTimeMili) {
		
		this.id = new BalanceteAuditPK();
		
		id.setIdEntidade(balancete.getId());
		id.setTipoOperacaoAudit(tipoOperacaoAudit);
		id.setDataHora(dateTimeMili);
		
		this.ano =  balancete.getAno();
		this.arquivo = new ArquivoBalanceteAudit(balancete.getArquivo(), tipoOperacaoAudit, dateTimeMili);
		this.dataIndexacao = balancete.getDataIndexacao();
		this.dataUltimaAlteracao = balancete.getDataUltimaAlteracao();
		this.mes = balancete.getMes();
		this.observacao = balancete.getObservacao();
		this.orgao = balancete.getOrgao();
		this.situacao = balancete.getSituacao();
		this.usuario = new UsuarioAudit(balancete.getUsuario(), tipoOperacaoAudit, dateTimeMili);
		this.volume = balancete.getVolume();
	}
	
	public BalanceteAudit() {
	}
	
	@Transient
	public String getDataUltimaAlteracaoFormat() {
		
		String dataFormat = null;
		
		if (this.getDataUltimaAlteracao() != null){
			
			dataFormat = DataUtil.formataData(this.getDataUltimaAlteracao(), "dd/MM/yyyy hh:mm:ss");
		}
		
		return dataFormat;
	}
	
	@Transient
	public String getDataIndexacaoFormat() {
		
		String dataFormat = null;
		
		if (this.getDataIndexacao() != null){
			
			dataFormat = DataUtil.formataData(this.getDataIndexacao(),"dd/MM/yyyy");
		}
		
		return dataFormat;
	}
	
	public BalanceteAuditPK getId() {
		return id;
	}

	public String getVolume() {
		return volume;
	}

	public MesEnum getMes() {
		return mes;
	}

	public Integer getAno() {
		return ano;
	}

	public OrgaoEnum getOrgao() {
		return orgao;
	}

	public String getObservacao() {
		return observacao;
	}

	public Date getDataIndexacao() {
		return dataIndexacao;
	}

	public ArquivoBalanceteAudit getArquivo() {
		return arquivo;
	}

	public UsuarioAudit getUsuario() {
		return usuario;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public Situacao getSituacao() {
		return situacao;
	}
}