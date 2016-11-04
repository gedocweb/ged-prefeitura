package br.com.ged.admin.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;

import br.com.ged.domain.entidade.DepartamentoEnum;
import br.com.ged.dto.FiltroGrupoUsuarioDTO;
import br.com.ged.dto.audit.FiltroBalanceteAuditDTO;
import br.com.ged.dto.audit.FiltroLeiAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;
import br.com.ged.dto.audit.RetornoMonitoramentoUsuarioDTO;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.entidades.Usuario;
import br.com.ged.framework.AbstractManageBean;
import br.com.ged.generics.service.GenericService;
import br.com.ged.service.GrupoUsuarioService;
import br.com.ged.service.UsuarioService;

public abstract class RelatorioSuperController extends AbstractManageBean{
	
	protected Long idGrupoSelecionado;
	protected Long idUsuarioSelecionado;
	
	protected DepartamentoEnum departamentoSelecionado;
	
	protected Boolean periodoMesAtual;
	
	protected Date periodoInicial;
	protected Date periodoFinal;
	
	protected List<SelectItem> selectItemsGrupoUsuario;
	protected List<SelectItem> selectItemsUsuario;
	protected List<SelectItem> selectItemsDepartamento;
	
	@EJB
	protected GrupoUsuarioService grupoUsuarioService;
	
	@EJB
	protected GenericService<GrupoUsuario, Long> genericServiceGrupoUsuario;
	
	@EJB
	protected UsuarioService usuarioService;
	
	@EJB
	protected RelatorioMonitoramentoValidadorView validadorView;
	
	protected FiltroMonitoramentoAuditDTO filtroMonitoramento;
	protected FiltroBalanceteAuditDTO filtroBalanceteAuditDTO;
	protected FiltroLeiAuditDTO filtroLeiAuditDTO;
	
	protected RetornoMonitoramentoUsuarioDTO retornoMonitoramento;
	
	protected Boolean renderedDetalhar;
	
	public void preRenderView(ComponentSystemEvent event){
		
		selectItemsGrupoUsuario = selectItemsGrupoUsuario();
		selectItemsDepartamento = DepartamentoEnum.selectItems();
		
	}
	
	private List<SelectItem> selectItemsGrupoUsuario() {
		
		List<GrupoUsuario> listUsuarios = grupoUsuarioService.pesquisar(new FiltroGrupoUsuarioDTO());
		
		List<SelectItem> listSelectItemNomeGrupoUsuario = new ArrayList<>(); 
		
		if (listUsuarios == null){
			return new ArrayList<>();
		}
		
		for (GrupoUsuario usuario : listUsuarios){
			
			SelectItem si = new SelectItem();
			si.setLabel(usuario.getGrupo());
			si.setValue(usuario.getId());
			
			listSelectItemNomeGrupoUsuario.add(si);
		}
		
		return listSelectItemNomeGrupoUsuario;
	}
	
	public void carregaSelectItemsUsuario(){
		
		if (getIdGrupoSelecionado() != null){
			
			List<Usuario> listUsuarios =  genericServiceGrupoUsuario.getById(GrupoUsuario.class, getIdGrupoSelecionado(), "usuarios","usuarios.pessoa").getUsuarios();
			selectItemsUsuario = selectItemsUsuario(listUsuarios );
		}else{
			
			selectItemsUsuario = null;
		}
	}

	private List<SelectItem> selectItemsUsuario(List<Usuario> listUsuarios) {
		
		List<SelectItem> listSelectItemNomePessoaUsuario = new ArrayList<>(); 
		
		if (listUsuarios == null){
			return new ArrayList<>();
		}
		
		for (Usuario usuario : listUsuarios){
			
			SelectItem si = new SelectItem();
			si.setLabel(usuario.getPessoa().getNome());
			si.setValue(usuario.getId());
			
			listSelectItemNomePessoaUsuario.add(si);
		}
		
		return listSelectItemNomePessoaUsuario;
	}
	
	public Long getIdGrupoSelecionado() {
		return idGrupoSelecionado;
	}

	public void setIdGrupoSelecionado(Long idGrupoSelecionado) {
		this.idGrupoSelecionado = idGrupoSelecionado;
	}

	public Long getIdUsuarioSelecionado() {
		return idUsuarioSelecionado;
	}

	public void setIdUsuarioSelecionado(Long idUsuarioSelecionado) {
		this.idUsuarioSelecionado = idUsuarioSelecionado;
	}

	public DepartamentoEnum getDepartamentoSelecionado() {
		return departamentoSelecionado;
	}

	public void setDepartamentoSelecionado(DepartamentoEnum departamentoSelecionado) {
		this.departamentoSelecionado = departamentoSelecionado;
	}

	public Boolean getPeriodoMesAtual() {
		return periodoMesAtual;
	}

	public void setPeriodoMesAtual(Boolean periodoMesAtual) {
		this.periodoMesAtual = periodoMesAtual;
	}

	public Date getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Date periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Date getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Date periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	public List<SelectItem> getSelectItemsGrupoUsuario() {
		return selectItemsGrupoUsuario;
	}

	public void setSelectItemsGrupoUsuario(List<SelectItem> selectItemsGrupoUsuario) {
		this.selectItemsGrupoUsuario = selectItemsGrupoUsuario;
	}

	public List<SelectItem> getSelectItemsUsuario() {
		return selectItemsUsuario;
	}

	public void setSelectItemsUsuario(List<SelectItem> selectItemsUsuario) {
		this.selectItemsUsuario = selectItemsUsuario;
	}

	public List<SelectItem> getSelectItemsDepartamento() {
		return selectItemsDepartamento;
	}

	public void setSelectItemsDepartamento(List<SelectItem> selectItemsDepartamento) {
		this.selectItemsDepartamento = selectItemsDepartamento;
	}

	public FiltroBalanceteAuditDTO getFiltroBalanceteAuditDTO() {
		return filtroBalanceteAuditDTO;
	}
	
	public FiltroLeiAuditDTO getFiltroLeiAuditDTO() {
		return filtroLeiAuditDTO;
	}

	public void setFiltroLeiAuditDTO(FiltroLeiAuditDTO filtroLeiAuditDTO) {
		this.filtroLeiAuditDTO = filtroLeiAuditDTO;
	}

	public RetornoMonitoramentoUsuarioDTO getRetornoMonitoramento() {
		return retornoMonitoramento;
	}

	public Boolean getRenderedDetalhar() {
		return renderedDetalhar;
	}

	public void setRenderedDetalhar(Boolean renderedDetalhar) {
		this.renderedDetalhar = renderedDetalhar;
	}
}