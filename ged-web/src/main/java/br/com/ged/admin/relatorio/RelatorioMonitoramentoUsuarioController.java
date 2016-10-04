package br.com.ged.admin.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.ged.domain.DepartamentoEnum;
import br.com.ged.domain.Pagina;
import br.com.ged.domain.TipoOperacaoAudit;
import br.com.ged.dto.FiltroGrupoUsuarioDTO;
import br.com.ged.dto.FiltroUsuarioDTO;
import br.com.ged.dto.audit.FiltroBalanceteAuditDTO;
import br.com.ged.dto.audit.FiltroMonitoramentoAuditDTO;
import br.com.ged.dto.audit.RetornoMonitoramentoUsuarioDTO;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.entidades.Usuario;
import br.com.ged.entidades.auditoria.BalanceteAudit;
import br.com.ged.service.GrupoUsuarioService;
import br.com.ged.service.UsuarioService;
import br.com.ged.service.audit.BalanceteAuditService;
import br.com.ged.util.DataUtil;

@ManagedBean(name="painelRelatorioMonitoramentoUsuario")
@ViewScoped
public class RelatorioMonitoramentoUsuarioController extends RelatorioSuperController{
	
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
	private UsuarioService usuarioService;
	
	@EJB
	private BalanceteAuditService balanceteAuditService;
	
	private FiltroMonitoramentoAuditDTO filtroMonitoramento;
	private FiltroBalanceteAuditDTO filtroBalanceteAuditDTO;
	
	private RetornoMonitoramentoUsuarioDTO retornoMonitoramento;
	
	private List<BalanceteAudit> listDetalheBalanceteAudit;
	
	@PostConstruct	
	public void inicio(){
		
		selectItemsGrupoUsuario = selectItemsGrupoUsuario();
		selectItemsUsuario = selectItemsUsuario();
		selectItemsDepartamento = DepartamentoEnum.selectItems();
		
		periodoMesAtual = Boolean.TRUE;
		
		filtroMonitoramento = new FiltroMonitoramentoAuditDTO();
		filtroBalanceteAuditDTO = new FiltroBalanceteAuditDTO();
		
		retornoMonitoramento = new RetornoMonitoramentoUsuarioDTO();
		
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

	private List<SelectItem> selectItemsUsuario() {
		
		List<Usuario> listUsuarios = usuarioService.pesquisar(new FiltroUsuarioDTO(), "pessoa");
		
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

	public void pesquisar(){
		
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
		
		if (isDepartamentoBalancete()){
			
			retornoMonitoramento = balanceteAuditService.monitoramento(filtroMonitoramento);
		}
	}

	public void detalhar(TipoOperacaoAudit operacaoAudit){
		
		if (isDepartamentoBalancete()){
			
			FiltroBalanceteAuditDTO filtroBalanceteAuditDTO = new FiltroBalanceteAuditDTO();
			
			filtroBalanceteAuditDTO.setDataBetween(filtroMonitoramento.getDataFiltroBetween());
			filtroBalanceteAuditDTO.setIdUsuario(idUsuarioSelecionado);

			listDetalheBalanceteAudit = balanceteAuditService.detalharOperacao(filtroBalanceteAuditDTO, operacaoAudit);
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
}