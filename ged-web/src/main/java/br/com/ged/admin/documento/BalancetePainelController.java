package br.com.ged.admin.documento;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ged.domain.Pagina;

@ManagedBean(name="painelBalancete")
@SessionScoped
public class BalancetePainelController extends DocumentoSuperController{

	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.PAINEL_DOCUMENTO;
	}
	
}