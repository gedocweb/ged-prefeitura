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
import br.com.ged.dto.FiltroRecursoHumanoDTO;
import br.com.ged.entidades.ArquivoRecursoHumano;
import br.com.ged.entidades.Pessoa;
import br.com.ged.entidades.RecursoHumano;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.service.RecursoHumanoService;
import br.com.ged.util.container.UtilArquivo;

@ManagedBean(name = "painelRecursoHumano")
@SessionScoped
public class RecursoHumanoPainelController extends DocumentoSuperController {

	@EJB
	protected GenericServiceController<RecursoHumano, Long> serviceRH;

	@EJB
	protected RecursoHumanoService balanceteService;

	private List<RecursoHumano> listRecursoHumano = null;

	private RecursoHumano recursoHumano;

	private FiltroRecursoHumanoDTO filtroRecursoHumanoDTO;

	private boolean renderedCadastro;
	private boolean renderedPesquisar;
	private boolean renderedExportar;
	private boolean renderedAlterar;

	private boolean arquivoAnexado;

	private boolean extensaoArquivoDiferentePDF;
	private boolean converterArquivoParaPDF;

	@PostConstruct
	public void inicio() {

		listRecursoHumano = new ArrayList<>();
		filtroRecursoHumanoDTO = new FiltroRecursoHumanoDTO();

		preparaPesquisar();
	}
	
	private RecursoHumano inicializaRecursoHumano() {
		
		RecursoHumano novoBalanc = new RecursoHumano();
		novoBalanc.setPessoa(new Pessoa());
		novoBalanc.setArquivo(new ArquivoRecursoHumano());
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

		recursoHumano = inicializaRecursoHumano();
	}

	public void preparaPesquisar() {

		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.FALSE;
		renderedExportar = Boolean.FALSE;
		renderedPesquisar = Boolean.TRUE;

		filtroRecursoHumanoDTO = new FiltroRecursoHumanoDTO();
	}

	public void preparaExportar() {

		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.FALSE;
		renderedExportar = Boolean.TRUE;
		renderedPesquisar = Boolean.FALSE;

		filtroRecursoHumanoDTO = new FiltroRecursoHumanoDTO();
	}

	public void preparaAlterar(RecursoHumano balanceteSelecionado) {

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

		recursoHumano = balanceteSelecionado;
	}

	public void pesquisar() {

		listRecursoHumano = balanceteService.pesquisar(filtroRecursoHumanoDTO, "arquivo");
	}

	public void alterar() {
		
		this.cadastrar();
		this.pesquisar();
		this.preparaPesquisar();
	}

	public void excluir() {
		
		serviceRH.excluir(getRecursoHumano());
		this.pesquisar();
	}

	public void cadastrar() {
		
		if (!arquivoAnexado){
			getRecursoHumano().setArquivo(null);
		}
		
		try {
			
			recursoHumanoValidatorView.valida(getRecursoHumano());
			
			getRecursoHumano().setDataUltimaAlteracao(new Date());
			getRecursoHumano().setSituacao(Situacao.ATIVO);
			
			if (converterArquivoParaPDF){
				getRecursoHumano().setArquivo(UtilArquivo.converterArquivoParaPDF(getRecursoHumano().getArquivo()));
			}
			
			serviceRH.salvar(getRecursoHumano());
			
			setRecursoHumano(inicializaRecursoHumano());
			arquivoAnexado = Boolean.FALSE;
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}

	}

	public void upload(FileUploadEvent event) {

		ArquivoRecursoHumano arquivo = arquivoUpload(event);

		getRecursoHumano().setArquivo(arquivo);
		arquivoAnexado = Boolean.TRUE;
		extensaoArquivoDiferentePDF = !arquivo.getDescricao().endsWith(".pdf");
	}
	
	private ArquivoRecursoHumano arquivoUpload(FileUploadEvent event) {
		
		UploadedFile uploadedFile = event.getFile();
	    String fileName = uploadedFile.getFileName();
	    byte[] contents = uploadedFile.getContents(); 
	    
	    ArquivoRecursoHumano arquivo = new ArquivoRecursoHumano(); 
		arquivo.setArquivo(contents);
		arquivo.setDescricao(fileName);
		arquivo.setContentType(uploadedFile.getContentType());
		return arquivo;
	}

	public StreamedContent exportar() {
		try {

			List<RecursoHumano> documentosFiltrados = balanceteService.pesquisar(filtroRecursoHumanoDTO, "arquivo");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);

			for (RecursoHumano doc : documentosFiltrados) {

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
			return new DefaultStreamedContent(btArray, "application/zip", "RecursoHumano.zip");

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

	public List<RecursoHumano> getListRecursoHumano() {
		return listRecursoHumano;
	}

	public RecursoHumano getRecursoHumano() {
		return recursoHumano;
	}

	public void setRecursoHumano(RecursoHumano recursoHumano) {
		this.recursoHumano = recursoHumano;
	}

	public FiltroRecursoHumanoDTO getFiltroRecursoHumanoDTO() {
		return filtroRecursoHumanoDTO;
	}

	public void setFiltroRecursoHumanoDTO(FiltroRecursoHumanoDTO filtroRecursoHumanoDTO) {
		this.filtroRecursoHumanoDTO = filtroRecursoHumanoDTO;
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