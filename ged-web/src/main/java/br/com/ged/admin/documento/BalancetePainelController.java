package br.com.ged.admin.documento;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.ged.domain.Mensagem;
import br.com.ged.domain.Pagina;
import br.com.ged.dto.FiltroBalanceteDTO;
import br.com.ged.entidades.Balancete;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.service.BalanceteService;

@ManagedBean(name="painelBalancete")
@SessionScoped
public class BalancetePainelController extends DocumentoSuperController{

	@EJB
	protected GenericServiceController<Balancete, Long> serviceBalancete;
	
	@EJB
	protected BalanceteService balanceteService;
	
	private List<Balancete> listBalancete = null;
	
	private Balancete balancete;
	
	private FiltroBalanceteDTO filtroBalanceteDTO;
	
	private boolean renderedCadastro;
	private boolean renderedPesquisar;
	private boolean renderedExportar;
	private boolean renderedAlterar;
	
	@PostConstruct
	public void inicio(){
		
		listBalancete = new ArrayList<>();
		filtroBalanceteDTO = new FiltroBalanceteDTO();
		balancete = new Balancete();
		
		preparaPesquisar();
	}
	
	public void preparaCadastro(){
		
		renderedCadastro = Boolean.TRUE;
		renderedAlterar = Boolean.FALSE;
		renderedExportar = Boolean.FALSE;
		renderedPesquisar = Boolean.FALSE;
		
		balancete = new Balancete();
	}
	
	public void preparaPesquisar(){
		
		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.FALSE;
		renderedExportar = Boolean.FALSE;
		renderedPesquisar = Boolean.TRUE;
		
		filtroBalanceteDTO = new FiltroBalanceteDTO();
	}
	
	public void preparaExportar(){
		
		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.FALSE;
		renderedExportar = Boolean.TRUE;
		renderedPesquisar = Boolean.FALSE;
		
		filtroBalanceteDTO = new FiltroBalanceteDTO();
	}
	
	public void preparaAlterar(Balancete balanceteSelecionado){
		
		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.TRUE;
		renderedExportar = Boolean.FALSE;
		renderedPesquisar = Boolean.FALSE;
		
		balancete = balanceteSelecionado;
	}
	
	public void pesquisar(){
		
		listBalancete = balanceteService.pesquisar(filtroBalanceteDTO, "arquivo");
	}
	
	public void alterar(){
		
	}
	
	public void excluir(){
		
	}
	
	public void cadastrar(){
		
	}
	
	public StreamedContent exportar(){
		try {			
			
			List<Balancete> documentosFiltrados = balanceteService.pesquisar(filtroBalanceteDTO, "arquivo");
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);
									
			for (Balancete doc : documentosFiltrados){
				
				String docDescricao = doc.getArquivo().getDescricao();
				String zipArquivoDescricao = docDescricao;
				ZipEntry entry = new ZipEntry(zipArquivoDescricao);
				entry.setSize(doc.getArquivo().getArquivo().length);
				zos.putNextEntry(entry);
				zos.write(doc.getArquivo().getArquivo());
				zos.closeEntry();									
			}												
			zos.close();
			ByteArrayInputStream btArray = new ByteArrayInputStream(baos.toByteArray());        	
            return new DefaultStreamedContent(btArray, "application/zip", "Balancetes.zip");          
            
		} catch (Exception e) {
			e.printStackTrace();
			enviaMensagem(Mensagem.ERRO_DEFAULT);
		}
		
		return null;
	}

	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.PAINEL_DOCUMENTO;
	}
	
	public List<Balancete> getListBalancete() {
		return listBalancete;
	}

	public Balancete getBalancete() {
		return balancete;
	}

	public void setBalancete(Balancete balancete) {
		this.balancete = balancete;
	}

	public FiltroBalanceteDTO getFiltroBalanceteDTO() {
		return filtroBalanceteDTO;
	}

	public void setFiltroBalanceteDTO(FiltroBalanceteDTO filtroBalanceteDTO) {
		this.filtroBalanceteDTO = filtroBalanceteDTO;
	}

	public boolean isRenderedCadastro() {
		return renderedCadastro;
	}

	public void setRenderedCadastro(boolean renderedCadastro) {
		this.renderedCadastro = renderedCadastro;
	}

	public boolean isRenderedPesquisar() {
		return renderedPesquisar;
	}

	public void setRenderedPesquisar(boolean renderedPesquisar) {
		this.renderedPesquisar = renderedPesquisar;
	}

	public boolean isRenderedExportar() {
		return renderedExportar;
	}

	public void setRenderedExportar(boolean renderedExportar) {
		this.renderedExportar = renderedExportar;
	}

	public boolean isRenderedAlterar() {
		return renderedAlterar;
	}

	public void setRenderedAlterar(boolean renderedAlterar) {
		this.renderedAlterar = renderedAlterar;
	}
	
}