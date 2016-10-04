package br.com.ged.admin.documento;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.ged.domain.Mensagem;
import br.com.ged.domain.Pagina;
import br.com.ged.domain.Situacao;
import br.com.ged.domain.TipoOperacaoAudit;
import br.com.ged.dto.FiltroBalanceteDTO;
import br.com.ged.entidades.ArquivoBalancete;
import br.com.ged.entidades.Balancete;
import br.com.ged.entidades.auditoria.BalanceteAudit;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.framework.GenericServiceControllerAudit;
import br.com.ged.service.BalanceteService;
import br.com.ged.service.audit.BalanceteAuditService;
import br.com.ged.util.container.UtilArquivo;

@ManagedBean(name = "painelBalancete")
@SessionScoped
public class BalancetePainelController extends DocumentoSuperController {

	@EJB
	protected GenericServiceController<Balancete, Long> serviceBalancete;
	
	@EJB
	protected GenericServiceControllerAudit<BalanceteAudit, Long> serviceBalanceteAudit;

	@EJB
	protected BalanceteService balanceteService;
	
	@EJB
	protected BalanceteAuditService balanceteAuditService;
	
	private List<Balancete> listBalancete = null;

	private Balancete balancete;

	private FiltroBalanceteDTO filtroBalanceteDTO;

	private boolean renderedCadastro;
	private boolean renderedPesquisar;
	private boolean renderedExportar;
	private boolean renderedAlterar;

	private boolean arquivoAnexado;

	private boolean extensaoArquivoDiferentePDF;
	private boolean converterArquivoParaPDF;
	
	private Balancete balancetePreAlteracao;

	@PostConstruct
	public void inicio() {

		listBalancete = new ArrayList<>();
		filtroBalanceteDTO = new FiltroBalanceteDTO();
		balancete = inicializaBalancete();

		preparaPesquisar();
	}
	
	private Balancete inicializaBalancete() {
		
		Balancete novoBalanc = new Balancete();
		novoBalanc.setArquivo(new ArquivoBalancete());
		novoBalanc.setUsuario(getUsuarioLogado());
		
		return novoBalanc;
	}

	public void preparaCadastro() {

		renderedCadastro = Boolean.TRUE;
		renderedAlterar = Boolean.FALSE;
		renderedExportar = Boolean.FALSE;
		renderedPesquisar = Boolean.FALSE;
		
		converterArquivoParaPDF = Boolean.FALSE;
		extensaoArquivoDiferentePDF = Boolean.FALSE;
		arquivoAnexado = Boolean.FALSE;

		balancete = inicializaBalancete();
	}

	public void preparaPesquisar() {

		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.FALSE;
		renderedExportar = Boolean.FALSE;
		renderedPesquisar = Boolean.TRUE;

		filtroBalanceteDTO = new FiltroBalanceteDTO();
	}

	public void preparaExportar() {

		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.FALSE;
		renderedExportar = Boolean.TRUE;
		renderedPesquisar = Boolean.FALSE;

		filtroBalanceteDTO = new FiltroBalanceteDTO();
	}

	public void preparaAlterar(Balancete balanceteSelecionado) {

		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.TRUE;
		renderedExportar = Boolean.FALSE;
		renderedPesquisar = Boolean.FALSE;
		
		arquivoAnexado = balanceteSelecionado.getArquivo() != null;
		
		if ( arquivoAnexado && balanceteSelecionado.getArquivo().getDescricao().endsWith(".pdf")){
			
			converterArquivoParaPDF = Boolean.FALSE;
			extensaoArquivoDiferentePDF = Boolean.FALSE;
		}else{
			extensaoArquivoDiferentePDF = Boolean.TRUE;
		}

		balancete = balanceteSelecionado;
		
		preAlteracaoAuditoria(balanceteSelecionado);
	}

	private void preAlteracaoAuditoria(Balancete balanceteSelecionado) {
		
		try {
			balancetePreAlteracao = (Balancete) BeanUtils.cloneBean(balanceteSelecionado);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public void pesquisar() {

		listBalancete = balanceteService.pesquisar(filtroBalanceteDTO, "arquivo");
	}

	public void alterar() {
		
		this.cadastrar();
		this.pesquisar();
		this.preparaPesquisar();
	}

	public void excluir() {
		
		//TODO Auditoria
		balanceteAuditService.auditoriaBalancete(getBalancete(), TipoOperacaoAudit.EXCLUIDO);
		serviceBalancete.excluir(getBalancete());
	}

	public void cadastrar() {
		
		if (!arquivoAnexado){
			getBalancete().setArquivo(null);
		}
		
		try {
			
			balanceteValidatorView.valida(getBalancete());
			
			getBalancete().setDataUltimaAlteracao(new Date());
			getBalancete().setSituacao(Situacao.ATIVO);
			
			if (converterArquivoParaPDF){
				getBalancete().setArquivo(UtilArquivo.converterArquivoParaPDF(getBalancete().getArquivo()));
			}
			
			serviceBalancete.salvar(balancete);
			
			//TODO Auditoria
			if (renderedCadastro){
				balanceteAuditService.auditoriaBalancete(getBalancete(), TipoOperacaoAudit.CADASTRADO);
			}else if (renderedAlterar){
				balanceteAuditService.auditoriaBalancete(balancetePreAlteracao, getBalancete());
			}
			
			setBalancete(inicializaBalancete());
			arquivoAnexado = Boolean.FALSE;
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}

	}

	public void upload(FileUploadEvent event) {

		ArquivoBalancete arquivo = arquivoUpload(event);

		getBalancete().setArquivo(arquivo);
		arquivoAnexado = Boolean.TRUE;
		extensaoArquivoDiferentePDF = !arquivo.getDescricao().endsWith(".pdf");
	}
	
	private ArquivoBalancete arquivoUpload(FileUploadEvent event) {
		
		UploadedFile uploadedFile = event.getFile();
	    String fileName = uploadedFile.getFileName();
	    byte[] contents = uploadedFile.getContents(); 
	    
	    ArquivoBalancete arquivo = new ArquivoBalancete(); 
		arquivo.setArquivo(contents);
		arquivo.setDescricao(fileName);
		arquivo.setContentType(uploadedFile.getContentType());
		return arquivo;
	}

	public StreamedContent exportar() {
		try {

			List<Balancete> documentosFiltrados = balanceteService.pesquisar(filtroBalanceteDTO, "arquivo");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);

			for (Balancete doc : documentosFiltrados) {
				
				//TODO Auditoria
				balanceteAuditService.auditoriaBalancete(doc, TipoOperacaoAudit.EXPORTADO);

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

	public boolean isArquivoAnexado() {
		return arquivoAnexado;
	}

	public void setArquivoAnexado(boolean arquivoAnexado) {
		this.arquivoAnexado = arquivoAnexado;
	}

	public boolean isExtensaoArquivoDiferentePDF() {
		return extensaoArquivoDiferentePDF;
	}

	public void setExtensaoArquivoDiferentePDF(boolean extensaoArquivoDiferentePDF) {
		this.extensaoArquivoDiferentePDF = extensaoArquivoDiferentePDF;
	}

	public boolean isConverterArquivoParaPDF() {
		return converterArquivoParaPDF;
	}

	public void setConverterArquivoParaPDF(boolean converterArquivoParaPDF) {
		this.converterArquivoParaPDF = converterArquivoParaPDF;
	}
}