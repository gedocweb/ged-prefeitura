package br.com.ged.entidades.auditoria;
 
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

import org.hibernate.annotations.Type;

import br.com.ged.domain.entidade.Role;
import br.com.ged.domain.entidade.Situacao;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.entidades.Usuario;
import br.com.ged.generics.EntidadeBasicaAudit;
 
@Entity
@Table(name = "tb_usr_audit")
public class UsuarioAudit extends EntidadeBasicaAudit{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@EmbeddedId
	private UsuarioAuditPK id;
	
	@Column(name = "usuario")
	private String usuario;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name="situacao")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	@Column(name="role")
	@Enumerated(EnumType.STRING)
	private Role role;
	
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumns({
		@JoinColumn(name = "tipo_op_audit_pess", referencedColumnName="tp_operacao"),
	    @JoinColumn(name = "id_ent_pess", referencedColumnName="id_pessoa"),
	    @JoinColumn(name = "id_data_hora", referencedColumnName="data_hora")
	})
	private PessoaAudit pessoa;
    
    @Column(name="logado_sistema")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean logado; 
    
	public UsuarioAudit(){
		situacao = Situacao.ATIVO;
	}

	public UsuarioAudit(Usuario usuario, TipoOperacaoAudit tipoOperacaoAudit, Long dateTimeMili) {
		
		this.id = new UsuarioAuditPK();
		
		id.setIdEntidade(usuario.getId());
		id.setTipoOperacaoAudit(tipoOperacaoAudit);
		id.setDataHora(dateTimeMili);
		
		this.setLogado(usuario.isLogado());
		this.setPessoa(new PessoaAudit(usuario.getPessoa(), tipoOperacaoAudit));
		this.setRole(usuario.getRole());
		this.setSenha(usuario.getSenha());
		this.setSituacao(usuario.getSituacao());
		this.setUsuario(usuario.getUsuario());
	}

	public UsuarioAuditPK getId() {
		return id;
	}

	public void setId(UsuarioAuditPK id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public PessoaAudit getPessoa() {
		return pessoa;
	}

	public void setPessoa(PessoaAudit pessoa) {
		this.pessoa = pessoa;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioAudit other = (UsuarioAudit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		if (role != other.role)
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (situacao != other.situacao)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
}