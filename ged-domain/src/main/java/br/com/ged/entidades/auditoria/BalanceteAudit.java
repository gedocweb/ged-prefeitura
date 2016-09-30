package br.com.ged.entidades.auditoria;
 
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.ged.domain.MesEnum;
import br.com.ged.domain.OrgaoEnum;
import br.com.ged.domain.Situacao;
import br.com.ged.domain.TipoOperacaoAudit;
import br.com.ged.entidades.Balancete;
import br.com.ged.generics.EntidadeBasicaAudit;
 
@Entity
@Table(name = "tb_balancete_audit")
public class BalanceteAudit extends EntidadeBasicaAudit{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@Id
	@Column(name = "id_balancete_audit")
	@GeneratedValue(generator = "seq_balancete_audit", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "seq_balancete_audit", sequenceName = "seq_balancete_audit",allocationSize=1)
	private Long id;
	
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
	@JoinColumn(name="id_arquivo")
	private ArquivoBalanceteAudit arquivo;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_usuario")
	private UsuarioAudit usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_ultima_alteracao")
	private Date dataUltimaAlteracao;
	
	public BalanceteAudit(Balancete balancete, TipoOperacaoAudit tipoOperacaoAudit) {
		
		super(balancete.getId(), tipoOperacaoAudit);
		
		this.setId(null);
		this.ano =  balancete.getAno();
		this.arquivo = new ArquivoBalanceteAudit(balancete.getArquivo(), tipoOperacaoAudit);
		this.dataIndexacao = balancete.getDataIndexacao();
		this.dataUltimaAlteracao = balancete.getDataUltimaAlteracao();
		this.mes = balancete.getMes();
		this.observacao = balancete.getObservacao();
		this.orgao = balancete.getOrgao();
		this.situacao = balancete.getSituacao();
		this.usuario = new UsuarioAudit(balancete.getUsuario(), tipoOperacaoAudit);
		this.volume = balancete.getVolume();
	}
	
	public BalanceteAudit() {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public MesEnum getMes() {
		return mes;
	}

	public void setMes(MesEnum mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public OrgaoEnum getOrgao() {
		return orgao;
	}

	public void setOrgao(OrgaoEnum orgao) {
		this.orgao = orgao;
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

	public ArquivoBalanceteAudit getArquivo() {
		return arquivo;
	}

	public void setArquivo(ArquivoBalanceteAudit arquivo) {
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

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
}