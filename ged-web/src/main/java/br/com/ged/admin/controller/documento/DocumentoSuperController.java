package br.com.ged.admin.controller.documento;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;

import br.com.ged.admin.controller.documento.balancete.BalanceteValidadorView;
import br.com.ged.admin.controller.documento.lei.LeiValidadorView;
import br.com.ged.admin.controller.documento.outro.DocumentoValidadorView;
import br.com.ged.admin.controller.documento.processoLicitatorio.ProcessoLicitatorioValidadorView;
import br.com.ged.admin.controller.documento.rh.RecursoHumanoValidadorView;
import br.com.ged.domain.ConfigLayoutCliente;
import br.com.ged.domain.EmpresaEnum;
import br.com.ged.domain.entidade.DepartamentoEnum;
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
	
	private List<SelectItem> listTipoIndexacao;
	private DepartamentoEnum tipoIndexacaoSelecionado;
	
	private boolean renderedTipoIndexacaoOutros;
	private boolean renderedTipoIndexacaoBalancete;
	private boolean renderedTipoIndexacaoRecursoHumano;
	private boolean renderedTipoIndexacaoProcessoLicitatorio;
	private boolean renderedTipoIndexacaoLei;
	private boolean renderedTipoIndexacao;
	
	public void preRenderView(ComponentSystemEvent event){
		
		configLayoutCliente = EmpresaEnum.configLayoutMarcaDaguaClientePorProperties();
		listTipoIndexacao = DepartamentoEnum.selectItems();
	}

	public void selecionaTipoIndexacao(){
		
		if (DepartamentoEnum.OUTROS.equals(tipoIndexacaoSelecionado)){
			renderedTipoIndexacaoOutros = Boolean.TRUE;
		}else{
			renderedTipoIndexacaoOutros = Boolean.FALSE;
		}
		
		if (DepartamentoEnum.RH.equals(tipoIndexacaoSelecionado)){
			renderedTipoIndexacaoRecursoHumano = Boolean.TRUE;
		}else{
			renderedTipoIndexacaoRecursoHumano = Boolean.FALSE;
		}
		
		if (DepartamentoEnum.BALANCETE.equals(tipoIndexacaoSelecionado)){
			renderedTipoIndexacaoBalancete = Boolean.TRUE;
		}else{
			renderedTipoIndexacaoBalancete = Boolean.FALSE;
		}
		
		if (DepartamentoEnum.PROC_LICITA.equals(tipoIndexacaoSelecionado)){
			renderedTipoIndexacaoProcessoLicitatorio = Boolean.TRUE;
		}else{
			renderedTipoIndexacaoProcessoLicitatorio = Boolean.FALSE;
		}
		
		if (DepartamentoEnum.LEI.equals(tipoIndexacaoSelecionado)){
			renderedTipoIndexacaoLei = Boolean.TRUE;
		}else{
			renderedTipoIndexacaoLei = Boolean.FALSE;
		}
	}
	
	public ConfigLayoutCliente getConfigLayoutCliente() {
		return configLayoutCliente;
	}
	
	public List<SelectItem> getListTipoIndexacao() {
		return listTipoIndexacao;
	}

	public DepartamentoEnum getTipoIndexacaoSelecionado() {
		return tipoIndexacaoSelecionado;
	}

	public void setTipoIndexacaoSelecionado(DepartamentoEnum tipoIndexacaoSelecionado) {
		this.tipoIndexacaoSelecionado = tipoIndexacaoSelecionado;
	}

	public boolean isRenderedTipoIndexacaoOutros() {
		return renderedTipoIndexacaoOutros;
	}

	public void setRenderedTipoIndexacaoOutros(boolean renderedTipoIndexacaoOutros) {
		this.renderedTipoIndexacaoOutros = renderedTipoIndexacaoOutros;
	}

	public boolean isRenderedTipoIndexacaoBalancete() {
		return renderedTipoIndexacaoBalancete;
	}

	public void setRenderedTipoIndexacaoBalancete(boolean renderedTipoIndexacaoBalancete) {
		this.renderedTipoIndexacaoBalancete = renderedTipoIndexacaoBalancete;
	}

	public boolean isRenderedTipoIndexacaoRecursoHumano() {
		return renderedTipoIndexacaoRecursoHumano;
	}

	public void setRenderedTipoIndexacaoRecursoHumano(boolean renderedTipoIndexacaoRecursoHumano) {
		this.renderedTipoIndexacaoRecursoHumano = renderedTipoIndexacaoRecursoHumano;
	}

	public boolean isRenderedTipoIndexacaoProcessoLicitatorio() {
		return renderedTipoIndexacaoProcessoLicitatorio;
	}

	public void setRenderedTipoIndexacaoProcessoLicitatorio(boolean renderedTipoIndexacaoProcessoLicitatorio) {
		this.renderedTipoIndexacaoProcessoLicitatorio = renderedTipoIndexacaoProcessoLicitatorio;
	}

	public boolean isRenderedTipoIndexacaoLei() {
		return renderedTipoIndexacaoLei;
	}

	public void setRenderedTipoIndexacaoLei(boolean renderedTipoIndexacaoLei) {
		this.renderedTipoIndexacaoLei = renderedTipoIndexacaoLei;
	}

	public boolean isRenderedTipoIndexacao() {
		return renderedTipoIndexacao;
	}
}