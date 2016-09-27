package br.com.ged.admin.documento;

import javax.ejb.EJB;
import javax.faces.event.ComponentSystemEvent;

import br.com.ged.domain.EmpresaEnum;
import br.com.ged.domain.ConfigLayoutCliente;
import br.com.ged.framework.AbstractManageBean;

public abstract class DocumentoSuperController extends AbstractManageBean{
	
	@EJB
	protected DocumentoValidadorView documentoValidatorView;
	
	@EJB
	protected BalanceteValidadorView balanceteValidatorView;
	
	@EJB
	protected RecursoHumanoValidadorView recursoHumanoValidatorView;
	
	@EJB
	protected ProcessoLicitatorioValidadorView processoLicitatorioValidatorView;
	
	@EJB
	protected LeiValidadorView leiValidatorView;
	
	private ConfigLayoutCliente configLayoutCliente;
	
	public void preRenderView(ComponentSystemEvent event){
		
		configLayoutCliente = EmpresaEnum.configLayoutMarcaDaguaClientePorProperties();
	}

	public ConfigLayoutCliente getConfigLayoutCliente() {
		return configLayoutCliente;
	}
}