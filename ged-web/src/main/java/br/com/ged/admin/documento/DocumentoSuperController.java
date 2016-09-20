package br.com.ged.admin.documento;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

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
	
	@PostConstruct
	public void inicio(){
	}
	
}