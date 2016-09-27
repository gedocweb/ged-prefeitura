package br.com.ged.controller.login;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ged.domain.ClienteEnum;
import br.com.ged.domain.ConfigLayoutCliente;
import br.com.ged.domain.Role;
import br.com.ged.domain.Situacao;
import br.com.ged.entidades.Pessoa;
import br.com.ged.entidades.Usuario;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.util.criptografia.CriptografiaUtil;

/**
 * 
 * @author pedro.oliveira
 * 
 *         <p>
 *         Classe responsável por ser o filtro do primeiro acesso interno dentro
 *         do sistema
 *         </p>
 *         <p>
 *         Ao tentar logar no sistema a ação passa por esse manage bean e é
 *         passado a requisição para o Spring Security realizar a autenticação.
 *         </p>
 * 
 *         <p>
 *         Completando a resposta, o contexto do Spring Security vai enviar a
 *         requisição para o método do autenticador customizado do projeto
 *         AuthenticationProviderCustom.java método authenticate(Authentication)
 *         </p>
 */
@ManagedBean
@RequestScoped
public class LoginController {

	private static final String SPRING_SECURITY = "/j_spring_security_check";
	
	@EJB
	private GenericServiceController<Usuario, Long> usuarioService;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ConfigLayoutCliente configLayoutCliente;
	
	public void preRenderView(ComponentSystemEvent event){
		
		this.configLayoutCliente = ClienteEnum.configLayoutLoginClientePorProperties();
	}
	
	@PostConstruct
	public void utilizandoBancoEmMemoria(){
		
		request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		if (usuarioService.emptyTable(Usuario.class)){
			criaUsuarioInicial();
		}
	}

	private void criaUsuarioInicial() {
		Usuario usuario = new Usuario();
		Pessoa pessoa = new Pessoa();
		
		pessoa.setCpf("00000000000");
		pessoa.setNome("Administrador Master");
		pessoa.setSituacao(Situacao.ATIVO);
		pessoa.setCelular("0000000000");
		pessoa.setEmail("xxx@xxx.xxx");
		
		usuario.setUsuario("admin");
		usuario.setSenha(CriptografiaUtil.criptografar("admin"));
		usuario.setSituacao(Situacao.ATIVO);
		usuario.setPessoa(pessoa);
		usuario.setRole(Role.ADMIN);
		
		usuarioService.salvar(usuario);
	}

	public void doLogin()  {

		RequestDispatcher dispatcher = request.getRequestDispatcher(
				SPRING_SECURITY);
		
		try {
			
			dispatcher.forward(request, response);
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public ConfigLayoutCliente getConfigLayoutCliente() {
		return configLayoutCliente;
	}
}