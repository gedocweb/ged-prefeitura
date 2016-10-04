package br.com.ged.entidades.auditoria;
 
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import br.com.ged.domain.Situacao;
import br.com.ged.domain.TipoOperacaoAudit;
import br.com.ged.entidades.Pessoa;
import br.com.ged.generics.EntidadeBasicaAudit;
 
@Entity
@Table(name = "tb_pessoa_audit")
public class PessoaAudit extends EntidadeBasicaAudit{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@EmbeddedId
	private PessoaAuditPK id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="rg")
	private Integer rg;
	
	@Column(name="situacao")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	@Column(name="cpf")
	private String cpf;
	
	@Column(name="celular")
	private String celular;
	
	@Column(name="email")
	private String email;
	
	public PessoaAudit(){
		situacao = Situacao.ATIVO;
	}

	public PessoaAudit(Pessoa pessoa, TipoOperacaoAudit tipoOperacaoAudit) {
		
		this.id = new PessoaAuditPK();
		
		id.setIdEntidade(pessoa.getId());
		id.setTipoOperacaoAudit(tipoOperacaoAudit);
		id.setDataHora(new Date().getTime());
		
		this.setCelular(pessoa.getCelular());
		this.setCpf(pessoa.getCpf());
		this.setEmail(pessoa.getEmail());
		this.setNome(pessoa.getNome());
		this.setRg(pessoa.getRg());
		this.setSituacao(pessoa.getSituacao());
	}

	public PessoaAuditPK getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setId(PessoaAuditPK id) {
		this.id = id;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRg() {
		return rg;
	}

	public void setRg(Integer rg) {
		this.rg = rg;
	}
}