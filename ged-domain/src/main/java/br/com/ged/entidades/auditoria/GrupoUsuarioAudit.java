package br.com.ged.entidades.auditoria;
 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.ged.domain.entidade.FuncionalidadeEnum;
import br.com.ged.domain.entidade.Situacao;
import br.com.ged.domain.entidade.TipoFuncionalidadeEnum;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.entidades.Usuario;
import br.com.ged.generics.EntidadeBasicaAudit;
 
@Entity
@Table(name = "tb_grp_audit")
public class GrupoUsuarioAudit extends EntidadeBasicaAudit {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7181106172249020200L;

	@EmbeddedId
	private GrupoUsuarioAuditPK id;
	
	@Column(name="grupo")
	private String grupo;
	
	@Column(name="situacao")
	@Enumerated(EnumType.STRING)
	private Situacao situacao;
	
	@ElementCollection(targetClass=FuncionalidadeEnum.class)
    @Enumerated(EnumType.STRING) 
    @CollectionTable(name="rl_func_grp_usr_audit")
    @Column(name="id_funcionalidade") 
	private List<FuncionalidadeEnum> funcionalidades;
	
	@ElementCollection(targetClass=TipoFuncionalidadeEnum.class)
    @Enumerated(EnumType.STRING) 
    @CollectionTable(name="rl_tp_func_grp_usr_audit")
    @Column(name="id_permissao") 
	private List<TipoFuncionalidadeEnum> tiposFuncionalidades;
	
	@OneToMany(cascade={CascadeType.ALL})
	@JoinTable(name = "rl_grp_usr_audit", 
		joinColumns = {
			  	  @JoinColumn(name = "tipo_op_cat_grp_usr", referencedColumnName="tp_operacao"),
			  	  @JoinColumn(name = "id_cat_grp_usr_audit", referencedColumnName="id_grupo_usuario"),
			  	  @JoinColumn(name = "id_dt_hora_cat_grp_usr", referencedColumnName="data_hora")
			}, 
		inverseJoinColumns = {
			  	  @JoinColumn(name = "tipo_op_audit_usr", referencedColumnName="tp_operacao"),
			  	  @JoinColumn(name = "id_ent_usr", referencedColumnName="id_usuario"),
			  	  @JoinColumn(name = "id_dt_hora_usr", referencedColumnName="data_hora")
				}
	)
    private List<UsuarioAudit> usuarios;
	
	public GrupoUsuarioAudit(){
		situacao = Situacao.ATIVO;
	}

	public GrupoUsuarioAudit(GrupoUsuario grp, TipoOperacaoAudit tpAudit, Long dateMili) {
		
		GrupoUsuarioAuditPK pk = new GrupoUsuarioAuditPK();
		
		pk.setDataHora(dateMili);
		pk.setIdEntidade(grp.getId());
		pk.setTipoOperacaoAudit(tpAudit);
		
		this.setFuncionalidades(grp.getFuncionalidades());
		this.setGrupo(grp.getGrupo());
		this.setId(pk);
		this.setSituacao(grp.getSituacao());
		this.setTiposFuncionalidades(grp.getTiposFuncionalidades());
		this.setUsuarios(converteListaUsuarioAudit(grp.getUsuarios(), tpAudit, dateMili));
		
	}

	private List<UsuarioAudit> converteListaUsuarioAudit(List<Usuario> usuarios, TipoOperacaoAudit tpAudit, Long dateMili) {
		
		List<UsuarioAudit> listUsrAudit = new ArrayList<UsuarioAudit>();
		
		for (Usuario usr : usuarios){
			
			UsuarioAudit usrAudit = new UsuarioAudit(usr, tpAudit, dateMili);
			listUsrAudit.add(usrAudit);
		}
		
		return listUsrAudit;
	}

	public GrupoUsuarioAuditPK getId() {
		return id;
	}

	public void setId(GrupoUsuarioAuditPK id) {
		this.id = id;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public List<FuncionalidadeEnum> getFuncionalidades() {
		return funcionalidades;
	}

	public void setFuncionalidades(List<FuncionalidadeEnum> funcionalidades) {
		this.funcionalidades = funcionalidades;
	}

	public List<TipoFuncionalidadeEnum> getTiposFuncionalidades() {
		return tiposFuncionalidades;
	}

	public void setTiposFuncionalidades(List<TipoFuncionalidadeEnum> tiposFuncionalidades) {
		this.tiposFuncionalidades = tiposFuncionalidades;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public List<UsuarioAudit> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioAudit> usuarios) {
		this.usuarios = usuarios;
	}
}