package br.com.ged.domain.entidade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.model.SelectItem;

import br.com.ged.domain.AutorizacaoEnum;
import br.com.ged.domain.TipoApp;
import br.com.ged.util.InitConfigProperties;

/**
 * 
 * @author pedro.oliveira
 * 
 * <p>Enum reposável por associar AutorizacaoEnum.java (Que já possui as Role.java de autorização para acesso a cada Pagina.java) a cada funcionalidade.</p>
 * 
 *
 */
public enum FuncionalidadeEnum {

	/**
	 * ADMINISTRADOR - Acesso a todas as funcionalidades
	 */
	MANTER_GRUPO_USUARIO("Manter Grupo de Usuário", 
			AutorizacaoEnum.ADMINISTRADOR, TipoApp.TODOS, 
			TipoFuncionalidadeEnum.CADASTRAR_GRUPO_USUARIO, 
			TipoFuncionalidadeEnum.PESQUISAR_GRUPO_USUARIO,
			TipoFuncionalidadeEnum.ALTERAR_NOME_GRUPO_USUARIO,
			TipoFuncionalidadeEnum.EXCLUIR_GRUPO_USUARIO,
			TipoFuncionalidadeEnum.REMOVER_USUARIO_GU,
			TipoFuncionalidadeEnum.ALTERAR_PERMISSAO,
			TipoFuncionalidadeEnum.ALTERAR_FUNCIONALIDADE,
			TipoFuncionalidadeEnum.INCLUIR_NOVO_USUARIO),
	
	
	MANTER_USUARIO("Manter Usuário", 
			AutorizacaoEnum.ADMINISTRADOR, TipoApp.TODOS, 
			TipoFuncionalidadeEnum.CADASTRAR_USUARIO, 
			TipoFuncionalidadeEnum.REMOVER_USUARIO,
			TipoFuncionalidadeEnum.ALTERAR_USUARIO,
			TipoFuncionalidadeEnum.PESQUISAR_USUARIO,
			TipoFuncionalidadeEnum.REMOVER_USUARIO_GU,
			TipoFuncionalidadeEnum.ALTERAR_SENHA_USUARIO,
			TipoFuncionalidadeEnum.ALTERAR_ROLE_USUARIO),
	
	MANTER_CATEGORIA_DOCUMENTO("Manter Categoria Documento",
			AutorizacaoEnum.ADMINISTRADOR, TipoApp.TODOS,
			TipoFuncionalidadeEnum.CADASTRAR_CATEGORIA_DOCUMENTO,
			TipoFuncionalidadeEnum.ALTERAR_CATEGORIA_DOCUMENTO,
			TipoFuncionalidadeEnum.REMOVER_CATEGORIA_DOCUMENTO),
	
	MANTER_TIPO_DOCUMENTO("Manter Tipo Documento",
			AutorizacaoEnum.ADMINISTRADOR, TipoApp.TODOS,
			TipoFuncionalidadeEnum.CADASTRAR_TIPO_DOCUMENTO,
			TipoFuncionalidadeEnum.ALTERAR_TIPO_DOCUMENTO,
			TipoFuncionalidadeEnum.REMOVER_TIPO_DOCUMENTO), 
	
	MANTER_DOCUMENTO("Manter Documento",
			AutorizacaoEnum.ADMINISTRADOR, TipoApp.TODOS,
			TipoFuncionalidadeEnum.CADASTRAR_DOCUMENTO,
			TipoFuncionalidadeEnum.ALTERAR_DOCUMENTO,
			TipoFuncionalidadeEnum.VISUALIZAR_DOCUMENTO,
			TipoFuncionalidadeEnum.BAIXAR_DOCUMENTO,
			TipoFuncionalidadeEnum.REMOVER_DOCUMENTO,
			TipoFuncionalidadeEnum.EXPORTAR_DOCUMENTO,
			TipoFuncionalidadeEnum.PESQUISAR_DOCUMENTO),
	
	MANTER_BALANCETE("Manter Balancete",
			AutorizacaoEnum.ADMINISTRADOR, TipoApp.PREFEITURA,
			TipoFuncionalidadeEnum.CADASTRAR_BALANCETE,
			TipoFuncionalidadeEnum.ALTERAR_BALANCETE,
			TipoFuncionalidadeEnum.VISUALIZAR_BALANCETE,
			TipoFuncionalidadeEnum.BAIXAR_BALANCETE,
			TipoFuncionalidadeEnum.REMOVER_BALANCETE,
			TipoFuncionalidadeEnum.EXPORTAR_BALANCETE,
			TipoFuncionalidadeEnum.PESQUISAR_BALANCETE),
	
	MANTER_RECURSO_HUMANO("Manter Recurso Humano",
			AutorizacaoEnum.ADMINISTRADOR, TipoApp.TODOS,
			TipoFuncionalidadeEnum.CADASTRAR_RECURSO_HUMANO,
			TipoFuncionalidadeEnum.PESQUISAR_RECURSO_HUMANO,
			TipoFuncionalidadeEnum.ALTERAR_RECURSO_HUMANO,
			TipoFuncionalidadeEnum.VISUALIZAR_RECURSO_HUMANO,
			TipoFuncionalidadeEnum.BAIXAR_RECURSO_HUMANO,
			TipoFuncionalidadeEnum.REMOVER_RECURSO_HUMANO,
			TipoFuncionalidadeEnum.EXPORTAR_RECURSO_HUMANO),
	

	MANTER_PROCESSO_LICITATORIO("Manter Processo Licitatório",
			AutorizacaoEnum.ADMINISTRADOR, TipoApp.PREFEITURA,
			TipoFuncionalidadeEnum.CADASTRAR_PROCESSO_LICITATORIO,
			TipoFuncionalidadeEnum.ALTERAR_PROCESSO_LICITATORIO,
			TipoFuncionalidadeEnum.VISUALIZAR_PROCESSO_LICITATORIO,
			TipoFuncionalidadeEnum.BAIXAR_PROCESSO_LICITATORIO,
			TipoFuncionalidadeEnum.REMOVER_PROCESSO_LICITATORIO,
			TipoFuncionalidadeEnum.EXPORTAR_PROCESSO_LICITATORIO,
			TipoFuncionalidadeEnum.PESQUISAR_PROCESSO_LICITATORIO),
	
	MANTER_LEI("Manter Lei",
			AutorizacaoEnum.ADMINISTRADOR, TipoApp.PREFEITURA,
			TipoFuncionalidadeEnum.CADASTRAR_LEI,
			TipoFuncionalidadeEnum.ALTERAR_LEI,
			TipoFuncionalidadeEnum.VISUALIZAR_LEI,
			TipoFuncionalidadeEnum.BAIXAR_LEI,
			TipoFuncionalidadeEnum.REMOVER_LEI,
			TipoFuncionalidadeEnum.EXPORTAR_LEI,
			TipoFuncionalidadeEnum.PESQUISAR_LEI),
	
	RELATORIO("Relatório de Auditoria",
			AutorizacaoEnum.ADMINISTRADOR, TipoApp.TODOS,
			TipoFuncionalidadeEnum.MONITORAR_OPERACAO,
			TipoFuncionalidadeEnum.DETALHAR_OPERACAO,
			TipoFuncionalidadeEnum.DOSSIE_OPERACAO)
	;
	
	private String label;

	private AutorizacaoEnum autorizacao;
	
	private TipoFuncionalidadeEnum[] funcionalidades;

	private TipoApp tipoApp;
	
	private FuncionalidadeEnum(String label, AutorizacaoEnum autorizacao, TipoApp tipoApp, TipoFuncionalidadeEnum... funcionalidades){
		
		this.label = label;
		this.autorizacao = autorizacao;
		this.funcionalidades = funcionalidades;
		this.tipoApp = tipoApp;
	}
	
	/**
	 * Verifica se o tipoFuncionalidade(TipoFuncionalidadeEnum) solicitada está dentro das que tem o {@code auth}(AutorizacaoEnum) do usuario logado. 
	 * 
	 * 
	 * @param auth variavel de instância dentro do AbstractManageBean, inicializada pelo usuario logado
	 * @param tipoFuncionalidade funcionalidade que solicita acesso 
	 * @return true caso o {@code tipoFuncionalidade} esteja dentre as permitidas no {@code auth}
	 */
	public static boolean verificaAutorizacaoComAcessoNaFuncionalidade(AutorizacaoEnum auth, TipoFuncionalidadeEnum tipoFuncionalidade) {
		
		boolean funcionalidadeLiberada = Boolean.FALSE;
		
		String modoName = InitConfigProperties.getValue(TipoApp.CONFIG_PARAM_PROPERTIE);
		
		for (FuncionalidadeEnum funcionalidade : values()){
			
			if (TipoApp.PREFEITURA.name().equals(modoName) && funcionalidade.tipoApp.equals(TipoApp.PREFEITURA)){
				
				if (funcionalidade.autorizacao.equals(auth) && contemFuncionalidade(tipoFuncionalidade, funcionalidade)){
					funcionalidadeLiberada = Boolean.TRUE;
				}
			}
			
			if (TipoApp.PRIVADO.name().equals(modoName) && funcionalidade.tipoApp.equals(TipoApp.PRIVADO)){
				
				if (funcionalidade.autorizacao.equals(auth) && contemFuncionalidade(tipoFuncionalidade, funcionalidade)){
					funcionalidadeLiberada = Boolean.TRUE;
				}
			}
			
			if (funcionalidade.tipoApp.equals(TipoApp.TODOS)){
				
				if (funcionalidade.autorizacao.equals(auth) && contemFuncionalidade(tipoFuncionalidade, funcionalidade)){
					funcionalidadeLiberada = Boolean.TRUE;
				}
			}
			
			
		}
		
		return funcionalidadeLiberada;
	}

	private static boolean contemFuncionalidade(TipoFuncionalidadeEnum tipoFuncionalidade, FuncionalidadeEnum funcionalidade) {
		
		List<TipoFuncionalidadeEnum> tipoFuncionalidadeList = Arrays.asList(funcionalidade.funcionalidades);
		boolean contemFuncionalidade = Boolean.FALSE;
		
		if (tipoFuncionalidadeList.contains(tipoFuncionalidade)){
			contemFuncionalidade = Boolean.TRUE;
		}
		
		return contemFuncionalidade;
	}

	public String getLabel() {
		return label;
	}

	public static List<SelectItem> selectItems() {
		
		List<SelectItem> list = new ArrayList<>();
		
		String modoName = InitConfigProperties.getValue(TipoApp.CONFIG_PARAM_PROPERTIE);
		
		for (FuncionalidadeEnum func : values()){
			
			if (TipoApp.PREFEITURA.name().equals(modoName) && func.tipoApp.equals(TipoApp.PREFEITURA)){
				
				addFuncionalidadeSelectItem(list, func);
			}
			
			if (TipoApp.PRIVADO.name().equals(modoName) && func.tipoApp.equals(TipoApp.PRIVADO)){
				
				addFuncionalidadeSelectItem(list, func);
			}
			
			if (func.tipoApp.equals(TipoApp.TODOS)){
				
				addFuncionalidadeSelectItem(list, func);
			}
			
		}
		
		return list;
	}

	private static void addFuncionalidadeSelectItem(List<SelectItem> list, FuncionalidadeEnum func) {
		SelectItem si = new SelectItem();
		
		si.setLabel(func.label);
		si.setValue(func.name());
		
		list.add(si);
	}

	public static List<String> listTipos(String funcionalidadeSelecionada, Role role) {
		
		List<String> list = new ArrayList<>();
		String modoName = InitConfigProperties.getValue(TipoApp.CONFIG_PARAM_PROPERTIE);
		
		for (FuncionalidadeEnum func : values()){
			
			if (func.name().equals(funcionalidadeSelecionada)){
				
				for (TipoFuncionalidadeEnum tipoFunc : func.funcionalidades){
					
					if (TipoApp.PREFEITURA.name().equals(modoName) && func.tipoApp.equals(TipoApp.PREFEITURA)){
						
						if (tipoFunc.getRoles().contains(role)){
							list.add(tipoFunc.getLabel());
						}
					}
					
					if (TipoApp.PRIVADO.name().equals(modoName) && func.tipoApp.equals(TipoApp.PRIVADO)){
						
						if (tipoFunc.getRoles().contains(role)){
							list.add(tipoFunc.getLabel());
						}
					}
					
					if (func.tipoApp.equals(TipoApp.TODOS)){
						
						if (tipoFunc.getRoles().contains(role)){
							list.add(tipoFunc.getLabel());
						}
					}
				}
				
				break;
			}
		}
		
		return list;
	}

	public TipoFuncionalidadeEnum[] getPermissoes() {
		return funcionalidades;
	}
}