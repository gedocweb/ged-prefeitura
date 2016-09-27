package br.com.ged.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.ged.util.InitConfigProperties;

/**
 * 
 * @author pedro.oliveira
 *	
 *	<p>Utilizado para saber qual funcionalidade está sendo executada para realizar a autorização</p>
 *
 *	<p>Esse enum deve ser utilizado na página Xhtml/jsf para acessar o método na classe AbstractManageBean.java o método autorizaFuncionalidade(TipoFuncionalidadeEnum)</p>
 *	
 *	<p>Exemplo de implementação no arquivo xhtml:</p>
 *	
 *	<ul>
 *		<li>rendered="#{manageBean.autorizaFuncionalidade('CADASTRAR_PESSOA')}"</li>
 *	</ul>
 *
 *	<p><b>NOTA: O managebean em execução deve extender a classe AbstractManageBean</b></p>
 */
public enum TipoFuncionalidadeEnum {

	//Grupo Usuario
	CADASTRAR_GRUPO_USUARIO("Cadastro de Grupo de Usuário", FuncionalidadeEnum.MANTER_GRUPO_USUARIO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	EXCLUIR_GRUPO_USUARIO("Excluir de Grupo de Usuário", FuncionalidadeEnum.MANTER_GRUPO_USUARIO, TipoApp.TODOS, Role.ADMIN),
	ALTERAR_NOME_GRUPO_USUARIO("Alterar de Grupo de Usuário", FuncionalidadeEnum.MANTER_GRUPO_USUARIO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	PESQUISAR_GRUPO_USUARIO("Pesquisa de Grupo de Usuário", FuncionalidadeEnum.MANTER_GRUPO_USUARIO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	INCLUIR_NOVO_USUARIO("Incluir novos usuários ao grupo.", FuncionalidadeEnum.MANTER_GRUPO_USUARIO, TipoApp.TODOS, Role.ADMIN),
	ALTERAR_PERMISSAO("Alterar permissão das funcionalidades.", FuncionalidadeEnum.MANTER_GRUPO_USUARIO, TipoApp.TODOS, Role.ADMIN),
	REMOVER_USUARIO_GU("Remover usuários do grupo.", FuncionalidadeEnum.MANTER_GRUPO_USUARIO, TipoApp.TODOS, Role.ADMIN),
	ALTERAR_FUNCIONALIDADE("Alterar funcionalidades do grupo.", FuncionalidadeEnum.MANTER_GRUPO_USUARIO, TipoApp.TODOS, Role.ADMIN),
	
	//Usuario
	CADASTRAR_USUARIO("Cadastro de usuário", FuncionalidadeEnum.MANTER_USUARIO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	ALTERAR_USUARIO("Alterar usuário", FuncionalidadeEnum.MANTER_USUARIO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	PESQUISAR_USUARIO("Pesquisa de usuário", FuncionalidadeEnum.MANTER_USUARIO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	ALTERAR_SENHA_USUARIO("Alterar senha de usuário.", FuncionalidadeEnum.MANTER_USUARIO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	ALTERAR_ROLE_USUARIO("Alterar role do usuário.", FuncionalidadeEnum.MANTER_USUARIO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	REMOVER_USUARIO("Remover usuário.", FuncionalidadeEnum.MANTER_USUARIO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	
	//Documento
	CADASTRAR_CATEGORIA_DOCUMENTO("Cadastrar Nova Categoria Documento", FuncionalidadeEnum.MANTER_CATEGORIA_DOCUMENTO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	ALTERAR_CATEGORIA_DOCUMENTO("Alterar Categoria Documento", FuncionalidadeEnum.MANTER_CATEGORIA_DOCUMENTO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	REMOVER_CATEGORIA_DOCUMENTO("Remover Categoria Documento", FuncionalidadeEnum.MANTER_CATEGORIA_DOCUMENTO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	
	CADASTRAR_TIPO_DOCUMENTO("Cadastrar Novo Tipo Documento", FuncionalidadeEnum.MANTER_TIPO_DOCUMENTO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	ALTERAR_TIPO_DOCUMENTO("Alterar Tipo Documento", FuncionalidadeEnum.MANTER_TIPO_DOCUMENTO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	REMOVER_TIPO_DOCUMENTO("Remover Tipo Documento", FuncionalidadeEnum.MANTER_TIPO_DOCUMENTO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	
	CADASTRAR_DOCUMENTO("Cadastrar Documento", FuncionalidadeEnum.MANTER_DOCUMENTO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	ALTERAR_DOCUMENTO("Alterar Documento", FuncionalidadeEnum.MANTER_DOCUMENTO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	VISUALIZAR_DOCUMENTO("Visualizar Documento PDF", FuncionalidadeEnum.MANTER_DOCUMENTO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	BAIXAR_DOCUMENTO("Baixar Documento", FuncionalidadeEnum.MANTER_DOCUMENTO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	REMOVER_DOCUMENTO("Remover Documento", FuncionalidadeEnum.MANTER_DOCUMENTO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	EXPORTAR_DOCUMENTO("Exportar Documentos", FuncionalidadeEnum.MANTER_DOCUMENTO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	
	//Recurso Humano
	CADASTRAR_RECURSO_HUMANO("Cadastrar Recurso Humano", FuncionalidadeEnum.MANTER_RECURSO_HUMANO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	PESQUISAR_RECURSO_HUMANO("Pesquisar Recurso Humano", FuncionalidadeEnum.MANTER_RECURSO_HUMANO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	ALTERAR_RECURSO_HUMANO("Alterar Recurso Humano", FuncionalidadeEnum.MANTER_RECURSO_HUMANO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	VISUALIZAR_RECURSO_HUMANO("Visualizar Recurso Humano", FuncionalidadeEnum.MANTER_RECURSO_HUMANO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	BAIXAR_RECURSO_HUMANO("Baixar Recurso Humano", FuncionalidadeEnum.MANTER_RECURSO_HUMANO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	REMOVER_RECURSO_HUMANO("Remover Recurso Humano", FuncionalidadeEnum.MANTER_RECURSO_HUMANO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	EXPORTAR_RECURSO_HUMANO("Exportar Recurso Humano", FuncionalidadeEnum.MANTER_RECURSO_HUMANO, TipoApp.TODOS, Role.ADMIN, Role.GERENTE),
	
	
	//Balancete
	CADASTRAR_BALANCETE("Cadastrar Balancete", FuncionalidadeEnum.MANTER_BALANCETE, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	ALTERAR_BALANCETE("Alterar Balancete", FuncionalidadeEnum.MANTER_BALANCETE, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	VISUALIZAR_BALANCETE("Visualizar Balancete", FuncionalidadeEnum.MANTER_BALANCETE, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	BAIXAR_BALANCETE("Baixar Balancete", FuncionalidadeEnum.MANTER_BALANCETE, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	REMOVER_BALANCETE("Remover Balancete", FuncionalidadeEnum.MANTER_BALANCETE, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	EXPORTAR_BALANCETE("Exportar Balancete", FuncionalidadeEnum.MANTER_BALANCETE, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	

	//Processo licitatorio
	CADASTRAR_PROCESSO_LICITATORIO("Cadastrar Processo Licitatório", FuncionalidadeEnum.MANTER_PROCESSO_LICITATORIO, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	ALTERAR_PROCESSO_LICITATORIO("Alterar Processo Licitatório", FuncionalidadeEnum.MANTER_PROCESSO_LICITATORIO, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	VISUALIZAR_PROCESSO_LICITATORIO("Visualizar Processo Licitatório", FuncionalidadeEnum.MANTER_PROCESSO_LICITATORIO, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	BAIXAR_PROCESSO_LICITATORIO("Baixar Processo Licitatório", FuncionalidadeEnum.MANTER_PROCESSO_LICITATORIO, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	REMOVER_PROCESSO_LICITATORIO("Remover Processo Licitatório", FuncionalidadeEnum.MANTER_PROCESSO_LICITATORIO, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	EXPORTAR_PROCESSO_LICITATORIO("Exportar Processo Licitatório", FuncionalidadeEnum.MANTER_PROCESSO_LICITATORIO, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	
	//Lei
	CADASTRAR_LEI("Cadastrar Lei", FuncionalidadeEnum.MANTER_LEI, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	ALTERAR_LEI("Alterar Lei", FuncionalidadeEnum.MANTER_LEI, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	VISUALIZAR_LEI("Visualizar Lei", FuncionalidadeEnum.MANTER_LEI, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	BAIXAR_LEI("Baixar Lei", FuncionalidadeEnum.MANTER_LEI, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	REMOVER_LEI("Remover Lei", FuncionalidadeEnum.MANTER_LEI, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE),
	EXPORTAR_LEI("Exportar Lei", FuncionalidadeEnum.MANTER_LEI, TipoApp.PREFEITURA, Role.ADMIN, Role.GERENTE)
	
	;
	
	private String label;
	private FuncionalidadeEnum funcionalidade;
	private TipoApp tipoApp;
	private Role[] role;
	
	private TipoFuncionalidadeEnum(String label, FuncionalidadeEnum funcionalidade,TipoApp tipoApp ,Role...role){
		
		this.label = label;
		this.funcionalidade = funcionalidade;
		this.role = role;
		this.tipoApp = tipoApp;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public FuncionalidadeEnum getFuncionalidade() {
		return funcionalidade;
	}

	public static List<TipoFuncionalidadeEnum> permissoesPorFuncionalidades(FuncionalidadeEnum... funcionalidades) {
		
		if (funcionalidades == null){
			return null;
		}
		
		List<TipoFuncionalidadeEnum> list = new ArrayList<>();
		String modoName = InitConfigProperties.getValue(TipoApp.CONFIG_PARAM_PROPERTIE);
		
		for (FuncionalidadeEnum fe : funcionalidades){
			
			for (TipoFuncionalidadeEnum tf : fe.getPermissoes()){
				
				if (TipoApp.PREFEITURA.name().equals(modoName) && tf.tipoApp.equals(TipoApp.PREFEITURA)){
					
					list.add(tf);
				}
				
				if (TipoApp.PRIVADO.name().equals(modoName) && tf.tipoApp.equals(TipoApp.PRIVADO)){
					
					list.add(tf);
				}
				
				if (tf.tipoApp.equals(TipoApp.TODOS)){
					
					list.add(tf);
				}
			}
		}
		
		return list;
	}

	public List<Role> getRoles() {
		return Arrays.asList(role);
	}
}