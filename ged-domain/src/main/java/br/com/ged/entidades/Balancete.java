package br.com.ged.entidades;
 
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

import br.com.ged.domain.entidade.MesEnum;
import br.com.ged.domain.entidade.OrgaoEnum;
import br.com.ged.domain.entidade.Situacao;
import br.com.ged.generics.EntidadeBasica;
 
@Entity
@Table(name = "tb_balancete")
public class Balancete extends EntidadeBasica{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@Id
	@Column(name = "id_balancete")
	@GeneratedValue(generator = "seq_balancete", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "seq_balancete", sequenceName = "seq_balancete",allocationSize=1)
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
	private ArquivoBalancete arquivo;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_ultima_alteracao")
	private Date dataUltimaAlteracao;
	
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

	public ArquivoBalancete getArquivo() {
		return arquivo;
	}

	public void setArquivo(ArquivoBalancete arquivo) {
		this.arquivo = arquivo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
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