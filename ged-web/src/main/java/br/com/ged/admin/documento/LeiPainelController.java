package br.com.ged.admin.documento;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.ged.domain.Mensagem;
import br.com.ged.domain.Pagina;
import br.com.ged.domain.Situacao;
import br.com.ged.dto.FiltroLeiDTO;
import br.com.ged.entidades.ArquivoLei;
import br.com.ged.entidades.Lei;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.service.LeiService;
import br.com.ged.util.container.UtilArquivo;

@ManagedBean(name = "painelLei")
@SessionScoped
public class LeiPainelController extends DocumentoSuperController {

	@EJB
	protected GenericServiceController<Lei, Long> serviceLei;

	@EJB
	protected LeiService leiService;

	private List<Lei> listLei = null;

	private Lei lei;

	private FiltroLeiDTO filtroLeiDTO;

	private boolean renderedCadastro;
	private boolean renderedPesquisar;
	private boolean renderedExportar;
	private boolean renderedAlterar;

	private boolean arquivoAnexado;

	private boolean extensaoArquivoDiferentePDF;
	private boolean converterArquivoParaPDF;

	@PostConstruct
	public void inicio() {

		listLei = new ArrayList<>();
		filtroLeiDTO = new FiltroLeiDTO();

		preparaPesquisar();
	}
	
	private Lei inicializaLei() {
		
		Lei novoBalanc = new Lei();
		novoBalanc.setArquivo(new ArquivoLei());
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

		lei = inicializaLei();
	}

	public void preparaPesquisar() {

		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.FALSE;
		renderedExportar = Boolean.FALSE;
		renderedPesquisar = Boolean.TRUE;

		filtroLeiDTO = new FiltroLeiDTO();
	}

	public void preparaExportar() {

		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.FALSE;
		renderedExportar = Boolean.TRUE;
		renderedPesquisar = Boolean.FALSE;

		filtroLeiDTO = new FiltroLeiDTO();
	}

	public void preparaAlterar(Lei leiSelecionado) {

		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.TRUE;
		renderedExportar = Boolean.FALSE;
		renderedPesquisar = Boolean.FALSE;
		
		arquivoAnexado = leiSelecionado.getArquivo() != null;
		
		if ( arquivoAnexado && leiSelecionado.getArquivo().getDescricao().endsWith(".pdf")){
			
			converterArquivoParaPDF = Boolean.FALSE;
			extensaoArquivoDiferentePDF = Boolean.FALSE;
		}else{
			extensaoArquivoDiferentePDF = Boolean.TRUE;
		}

		lei = leiSelecionado;
	}

	public void pesquisar() {

		listLei = leiService.pesquisar(filtroLeiDTO, "arquivo");
	}

	public void alterar() {
		
		this.cadastrar();
		this.pesquisar();
		this.preparaPesquisar();
	}

	public void excluir() {
		
		serviceLei.excluir(getLei());
	}

	public void cadastrar() {
		
		if (!arquivoAnexado){
			getLei().setArquivo(null);
		}
		
		try {
			
			leiValidatorView.valida(getLei());
			
			getLei().setDataUltimaAlteracao(new Date());
			getLei().setSituacao(Situacao.ATIVO);
			
			if (converterArquivoParaPDF){
				getLei().setArquivo(UtilArquivo.converterArquivoParaPDF(getLei().getArquivo()));
			}
			
			serviceLei.salvar(getLei());
			
			setLei(inicializaLei());
			arquivoAnexado = Boolean.FALSE;
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}

	}

	public void upload(FileUploadEvent event) {

		ArquivoLei arquivo = arquivoUpload(event);

		getLei().setArquivo(arquivo);
		arquivoAnexado = Boolean.TRUE;
		extensaoArquivoDiferentePDF = !arquivo.getDescricao().endsWith(".pdf");
	}
	
	private ArquivoLei arquivoUpload(FileUploadEvent event) {
		
		UploadedFile uploadedFile = event.getFile();
	    String fileName = uploadedFile.getFileName();
	    byte[] contents = uploadedFile.getContents(); 
	    
	    ArquivoLei arquivo = new ArquivoLei(); 
		arquivo.setArquivo(contents);
		arquivo.setDescricao(fileName);
		arquivo.setContentType(uploadedFile.getContentType());
		return arquivo;
	}

	public StreamedContent exportar() {
		try {

			List<Lei> documentosFiltrados = leiService.pesquisar(filtroLeiDTO, "arquivo");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);

			for (Lei doc : documentosFiltrados) {

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
			return new DefaultStreamedContent(btArray, "application/zip", "ProcessosLicitatorio.zip");

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

	public List<Lei> getListLei() {
		return listLei;
	}

	public Lei getLei() {
		return lei;
	}

	public void setLei(Lei lei) {
		this.lei = lei;
	}

	public FiltroLeiDTO getFiltroLeiDTO() {
		return filtroLeiDTO;
	}

	public void setFiltroLeiDTO(FiltroLeiDTO filtroLeiDTO) {
		this.filtroLeiDTO = filtroLeiDTO;
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