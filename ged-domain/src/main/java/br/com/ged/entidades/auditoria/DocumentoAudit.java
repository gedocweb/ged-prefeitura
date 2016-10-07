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
import br.com.ged.entidades.Documento;
import br.com.ged.generics.EntidadeBasicaAudit;
 
@Entity
@Table(name = "tb_doc_audit")
public class DocumentoAudit extends EntidadeBasicaAudit {
 
	private static final long serialVersionUID = 7181106172249020200L;

	@EmbeddedId
	private DocumentoAuditPK id;
	
	@Column(name="descricao")
	private String descricao;
	
	@Column(name="observacao")
	private String observacao;

	@Enumerated(EnumType.STRING)
	@Column(name="situacao")
	private Situacao situacao;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumns({
	  	  @JoinColumn(name = "tipo_op_cat", referencedColumnName="tp_operacao"),
	  	  @JoinColumn(name = "id_cat", referencedColumnName="id_categoria"),
	  	  @JoinColumn(name = "id_dt_hora_cat", referencedColumnName="data_hora")
		})
	private CategoriaAudit categoria;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumns({
	  	  @JoinColumn(name = "tipo_op_tp_doc", referencedColumnName="tp_operacao"),
	  	  @JoinColumn(name = "id_tp_doc_audit", referencedColumnName="id_tp_doc"),
	  	  @JoinColumn(name = "id_dt_hora_tp_doc", referencedColumnName="data_hora")
		})
	private TipoDocumentoAudit tipoDocumento;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumns({
	  	  @JoinColumn(name = "tipo_op_arq", referencedColumnName="tp_operacao"),
	  	  @JoinColumn(name = "id_arq_audit", referencedColumnName="id_arquivo"),
	  	  @JoinColumn(name = "id_dt_hora_arq", referencedColumnName="data_hora")
		})
	private ArquivoAudit arquivo;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumns({
	  	  @JoinColumn(name = "tipo_op_usr", referencedColumnName="tp_operacao"),
	  	  @JoinColumn(name = "id_usr_audit", referencedColumnName="id_usuario"),
	  	  @JoinColumn(name = "id_dt_hora_usr", referencedColumnName="data_hora")
		})
	private UsuarioAudit usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_inclusao")
	private Date dataInclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_documento")
	private Date dataDocumento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_ultima_alteracao")
	private Date dataUltimaAlteracao;
	
	public DocumentoAudit(){
		situacao = Situacao.ATIVO;
	}
	
	public DocumentoAudit(Documento documento, TipoOperacaoAudit tpAudit, Long dateMili) {
		
		DocumentoAuditPK pk = new DocumentoAuditPK();
		pk.setDataHora(dateMili);
		pk.setIdEntidade(documento.getId());
		pk.setTipoOperacaoAudit(tpAudit);
		
		this.setArquivo(new ArquivoAudit(documento.getArquivo(), tpAudit, dateMili));
		this.setCategoria(new CategoriaAudit(documento.getCategoria(), tpAudit, dateMili));
		this.setDataDocumento(documento.getDataDocumento());
		this.setDataInclusao(documento.getDataInclusao());
		this.setDataUltimaAlteracao(documento.getDataUltimaAlteracao());
		this.setDescricao(documento.getDescricao());
		this.setId(pk);
		this.setObservacao(documento.getObservacao());
		this.setSituacao(documento.getSituacao());
		this.setTipoDocumento(new TipoDocumentoAudit(documento.getTipoDocumento(), tpAudit, dateMili));
		this.setUsuario(new UsuarioAudit(documento.getUsuario(), tpAudit, dateMili));
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
	public String getDataDocumentoFormat() {
		
		String dataFormat = null;
		
		if (this.getDataDocumento() != null){
			
			dataFormat = formataData(this.getDataDocumento(),"dd/MM/yyyy");
		}
		
		return dataFormat;
	}

	private String formataData(Date date, String pattern) {
		String dataUltimaAlteracaoFormat;
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		
		dataUltimaAlteracaoFormat = dateFormat.format(date);
		return dataUltimaAlteracaoFormat;
	}

	public DocumentoAuditPK getId() {
		return id;
	}

	public void setId(DocumentoAuditPK id) {
		this.id = id;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public CategoriaAudit getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaAudit categoria) {
		this.categoria = categoria;
	}

	public TipoDocumentoAudit getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoAudit tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public ArquivoAudit getArquivo() {
		return arquivo;
	}

	public void setArquivo(ArquivoAudit arquivo) {
		this.arquivo = arquivo;
	}

	public UsuarioAudit getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioAudit usuario) {
		this.usuario = usuario;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(Date dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arquivo == null) ? 0 : arquivo.hashCode());
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((dataDocumento == null) ? 0 : dataDocumento.hashCode());
		result = prime * result + ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
		result = prime * result + ((dataUltimaAlteracao == null) ? 0 : dataUltimaAlteracao.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DocumentoAudit)) {
			return false;
		}
		DocumentoAudit other = (DocumentoAudit) obj;
		if (arquivo == null) {
			if (other.arquivo != null) {
				return false;
			}
		} else if (!arquivo.equals(other.arquivo)) {
			return false;
		}
		if (categoria == null) {
			if (other.categoria != null) {
				return false;
			}
		} else if (!categoria.equals(other.categoria)) {
			return false;
		}
		if (dataDocumento == null) {
			if (other.dataDocumento != null) {
				return false;
			}
		} else if (!dataDocumento.equals(other.dataDocumento)) {
			return false;
		}
		if (dataInclusao == null) {
			if (other.dataInclusao != null) {
				return false;
			}
		} else if (!dataInclusao.equals(other.dataInclusao)) {
			return false;
		}
		if (dataUltimaAlteracao == null) {
			if (other.dataUltimaAlteracao != null) {
				return false;
			}
		} else if (!dataUltimaAlteracao.equals(other.dataUltimaAlteracao)) {
			return false;
		}
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		} else if (!descricao.equals(other.descricao)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (observacao == null) {
			if (other.observacao != null) {
				return false;
			}
		} else if (!observacao.equals(other.observacao)) {
			return false;
		}
		if (situacao != other.situacao) {
			return false;
		}
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null) {
				return false;
			}
		} else if (!tipoDocumento.equals(other.tipoDocumento)) {
			return false;
		}
		if (usuario == null) {
			if (other.usuario != null) {
				return false;
			}
		} else if (!usuario.equals(other.usuario)) {
			return false;
		}
		return true;
	}
}