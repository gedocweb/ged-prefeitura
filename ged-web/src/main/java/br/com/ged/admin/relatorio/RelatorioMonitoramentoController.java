package br.com.ged.admin.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.ged.domain.Pagina;
import br.com.ged.domain.entidade.DepartamentoEnum;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.dto.FiltroGrupoUsuarioDTO;
import br.com.ged.dto.audit.FiltroBalanceteAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;
import br.com.ged.dto.audit.RetornoMonitoramentoUsuarioDTO;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.entidades.Usuario;
import br.com.ged.entidades.auditoria.BalanceteAudit;
import br.com.ged.excecao.NegocioException;
import br.com.ged.generics.service.GenericService;
import br.com.ged.service.GrupoUsuarioService;
import br.com.ged.service.UsuarioService;
import br.com.ged.service.audit.BalanceteAuditService;
import br.com.ged.util.DataUtil;

@ManagedBean(name="painelRelatorioMonitoramento")
@ViewScoped
public class RelatorioMonitoramentoController extends RelatorioSuperController{
	
	private Long idGrupoSelecionado;
	private Long idUsuarioSelecionado;
	
	private DepartamentoEnum departamentoSelecionado;
	
	private Boolean periodoMesAtual;
	
	private Date periodoInicial;
	private Date periodoFinal;
	
	private List<SelectItem> selectItemsGrupoUsuario;
	private List<SelectItem> selectItemsUsuario;
	private List<SelectItem> selectItemsDepartamento;
	
	@EJB
	private GrupoUsuarioService grupoUsuarioService;
	
	@EJB
	private GenericService<GrupoUsuario, Long> genericServiceGrupoUsuario;
	
	@EJB
	private UsuarioService usuarioService;
	
	@EJB
	private BalanceteAuditService balanceteAuditService;
	
	@EJB
	private RelatorioMonitoramentoValidadorView validadorView;
	
	private FiltroMonitoramentoAuditDTO filtroMonitoramento;
	private FiltroBalanceteAuditDTO filtroBalanceteAuditDTO;
	
	private RetornoMonitoramentoUsuarioDTO retornoMonitoramento;
	
	private Boolean renderedDetalhar;
	
	private List<BalanceteAudit> listDetalheBalanceteAudit;
	
	@PostConstruct	
	public void inicio(){
		
		selectItemsGrupoUsuario = selectItemsGrupoUsuario();
		selectItemsDepartamento = DepartamentoEnum.selectItems();
		
		periodoMesAtual = Boolean.TRUE;
		
		filtroMonitoramento = new FiltroMonitoramentoAuditDTO();
		filtroBalanceteAuditDTO = new FiltroBalanceteAuditDTO();
		
		retornoMonitoramento = new RetornoMonitoramentoUsuarioDTO();
		renderedDetalhar = Boolean.FALSE;
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

	public void monitorar(){
		
		renderedDetalhar = Boolean.FALSE;
		retornoMonitoramento = new RetornoMonitoramentoUsuarioDTO();
		
		try {
			
			validadorView.valida(idGrupoSelecionado, idUsuarioSelecionado, departamentoSelecionado);
			
			if (!periodoMesAtual){
				filtroMonitoramento.getDataFiltroBetween().setDataInicio(periodoInicial);
				filtroMonitoramento.getDataFiltroBetween().setDataFim(periodoFinal);
			}else{
				
				periodoInicial = null;
				periodoFinal = null;
				
				filtroMonitoramento.getDataFiltroBetween().setDataInicio(DataUtil.primeiraDataDoMesAtual());
				filtroMonitoramento.getDataFiltroBetween().setDataFim(DataUtil.ultimaDataDoMesAtual());
			}
			
			filtroMonitoramento.setIdUsuario(idUsuarioSelecionado);
			
			if (isDepartamentoBalancete() && filtroMonitoramento.getIdUsuario() != null){
				
				retornoMonitoramento = balanceteAuditService.monitoramento(filtroMonitoramento);
			}
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}

	public void detalhar(TipoOperacaoAudit operacaoAudit){
		
		if (isDepartamentoBalancete()){
			
			FiltroBalanceteAuditDTO filtroBalanceteAuditDTO = new FiltroBalanceteAuditDTO();
			
			filtroBalanceteAuditDTO.setDataBetween(filtroMonitoramento.getDataFiltroBetween());
			filtroBalanceteAuditDTO.setIdUsuario(idUsuarioSelecionado);

			listDetalheBalanceteAudit = balanceteAuditService.detalharOperacao(filtroBalanceteAuditDTO, operacaoAudit);
			renderedDetalhar = Boolean.TRUE;
		}
	}

	private boolean isDepartamentoBalancete() {
		return DepartamentoEnum.BALANCETE.equals(departamentoSelecionado);
	}
	
	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.RELATORIO_MONITORAMENTO_USUARIO;
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

	public RetornoMonitoramentoUsuarioDTO getRetornoMonitoramento() {
		return retornoMonitoramento;
	}

	public List<BalanceteAudit> getListDetalheBalanceteAudit() {
		return listDetalheBalanceteAudit;
	}

	public Boolean getRenderedDetalhar() {
		return renderedDetalhar;
	}

	public void setRenderedDetalhar(Boolean renderedDetalhar) {
		this.renderedDetalhar = renderedDetalhar;
	}
}