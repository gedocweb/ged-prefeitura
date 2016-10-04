package br.com.ged.entidades.auditoria;
 
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import br.com.ged.domain.Situacao;
import br.com.ged.domain.TipoOperacaoAudit;
import br.com.ged.entidades.ArquivoBalancete;
import br.com.ged.generics.EntidadeBasicaAudit;
 
@Entity
@Table(name = "tb_arquivo_balancet_audit")
public class ArquivoBalanceteAudit extends EntidadeBasicaAudit{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@EmbeddedId
	private ArquivoBalanceteAuditPK id;
	
	@Column(name="descricao")
	private String descricao;
	
	@Column(name="content_type")
	private String contentType;
	
	@Column(name="arquivo")
	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	private byte[] arquivo;

	@Column(name="situacao")
	private Situacao situacao;
	
	@Transient
	private transient boolean pdf;
	
	@Transient
	private transient boolean imagem;
	
	@Transient
	private transient boolean word;
	
	public ArquivoBalanceteAudit(ArquivoBalancete arquivo, TipoOperacaoAudit tipoOperacaoAudit, Long dateTimeMili) {
		
		this.id = new ArquivoBalanceteAuditPK();
		
		id.setIdEntidade(arquivo.getId());
		id.setTipoOperacaoAudit(tipoOperacaoAudit);
		id.setDataHora(dateTimeMili);
		
		this.arquivo = arquivo.getArquivo();
		this.contentType = arquivo.getContentType();
		this.descricao = arquivo.getDescricao();
		this.situacao = arquivo.getSituacao();
	}
	
	public ArquivoBalanceteAudit (){
		
	}
	
	public boolean isPdf() {
		return descricao != null && (descricao.endsWith(".pdf") || descricao.endsWith(".PDF"));
	}
	
	public boolean isWord() {
		return descricao != null && (descricao.endsWith(".docx") || descricao.endsWith(".DOCX"));
	}
	
	public boolean isImagem() {
		return descricao != null && (descricao.endsWith(".gif") || descricao.endsWith(".jpg") || descricao.endsWith(".png") || descricao.endsWith(".jpeg") || 
										descricao.endsWith(".GIF") || descricao.endsWith(".JPG") || descricao.endsWith(".PNG") || descricao.endsWith(".JPEG"));
	}

	public ArquivoBalanceteAuditPK getId() {
		return id;
	}

	public void setId(ArquivoBalanceteAuditPK id) {
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

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
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
		if (!(obj instanceof ArquivoBalanceteAudit)) {
			return false;
		}
		ArquivoBalanceteAudit other = (ArquivoBalanceteAudit) obj;
		if (contentType == null) {
			if (other.contentType != null) {
				return false;
			}
		} else if (!contentType.equals(other.contentType)) {
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
		if (situacao != other.situacao) {
			return false;
		}
		return true;
	}
}