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

import br.com.ged.domain.Situacao;
import br.com.ged.domain.TipoDocumentoRH;
import br.com.ged.generics.EntidadeBasicaAudit;
 
@Entity
@Table(name = "tb_rh_audit")
public class RecursoHumanoAudit extends EntidadeBasicaAudit{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@Id
	@Column(name = "id_rh")
	@GeneratedValue(generator = "seq_rh_audit", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "seq_rh_audit", sequenceName = "seq_rh",allocationSize=1)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name="tipo_documento")
	private TipoDocumentoRH tipoDocumento;
	
	@Enumerated(EnumType.STRING)
	@Column(name="situacao")
	private Situacao situacao;
	
	@Column(name="observacao")
	private String observacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_indexacao")
	private Date dataIndexacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_documento")
	private Date dataDocumento;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_arquivo")
	private ArquivoRecursoHumanoAudit arquivo;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private UsuarioAudit usuario;
	
	@ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="id_pessoa")
	private PessoaAudit pessoa;
	
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
	
	@Transient
	public String getDataDocumentoFormat() {
		
		String dataFormat = null;
		
		if (this.getDataDocumento() != null){
			
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

	public TipoDocumentoRH getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoRH tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public ArquivoRecursoHumanoAudit getArquivo() {
		return arquivo;
	}

	public void setArquivo(ArquivoRecursoHumanoAudit arquivo) {
		this.arquivo = arquivo;
	}

	public UsuarioAudit getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioAudit usuario) {
		this.usuario = usuario;
	}

	public PessoaAudit getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaAudit pessoa) {
		this.pessoa = pessoa;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}
}