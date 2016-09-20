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
import br.com.ged.dto.FiltroProcessoLicitatorioDTO;
import br.com.ged.entidades.ArquivoProcessoLicitatorio;
import br.com.ged.entidades.ProcessoLicitatorio;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.service.ProcessoLicitatorioService;
import br.com.ged.util.container.UtilArquivo;

@ManagedBean(name = "painelProcessoLicitatorio")
@SessionScoped
public class ProcessoLicitatorioPainelController extends DocumentoSuperController {

	@EJB
	protected GenericServiceController<ProcessoLicitatorio, Long> serviceProcessoLicitatorio;

	@EJB
	protected ProcessoLicitatorioService processoLicitatorioService;

	private List<ProcessoLicitatorio> listProcessoLicitatorio = null;

	private ProcessoLicitatorio processoLicitatorio;

	private FiltroProcessoLicitatorioDTO filtroProcessoLicitatorioDTO;

	private boolean renderedCadastro;
	private boolean renderedPesquisar;
	private boolean renderedExportar;
	private boolean renderedAlterar;

	private boolean arquivoAnexado;

	private boolean extensaoArquivoDiferentePDF;
	private boolean converterArquivoParaPDF;

	@PostConstruct
	public void inicio() {

		listProcessoLicitatorio = new ArrayList<>();
		filtroProcessoLicitatorioDTO = new FiltroProcessoLicitatorioDTO();
		processoLicitatorio = inicializaProcessoLicitatorio();

		preparaPesquisar();
	}
	
	private ProcessoLicitatorio inicializaProcessoLicitatorio() {
		
		ProcessoLicitatorio novoBalanc = new ProcessoLicitatorio();
		novoBalanc.setArquivo(new ArquivoProcessoLicitatorio());
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

		processoLicitatorio = inicializaProcessoLicitatorio();
	}

	public void preparaPesquisar() {

		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.FALSE;
		renderedExportar = Boolean.FALSE;
		renderedPesquisar = Boolean.TRUE;

		filtroProcessoLicitatorioDTO = new FiltroProcessoLicitatorioDTO();
	}

	public void preparaExportar() {

		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.FALSE;
		renderedExportar = Boolean.TRUE;
		renderedPesquisar = Boolean.FALSE;

		filtroProcessoLicitatorioDTO = new FiltroProcessoLicitatorioDTO();
	}

	public void preparaAlterar(ProcessoLicitatorio processoLicitatorioSelecionado) {

		renderedCadastro = Boolean.FALSE;
		renderedAlterar = Boolean.TRUE;
		renderedExportar = Boolean.FALSE;
		renderedPesquisar = Boolean.FALSE;
		
		arquivoAnexado = processoLicitatorioSelecionado.getArquivo() != null;
		
		if ( arquivoAnexado && processoLicitatorioSelecionado.getArquivo().getDescricao().endsWith(".pdf")){
			
			converterArquivoParaPDF = Boolean.FALSE;
			extensaoArquivoDiferentePDF = Boolean.FALSE;
		}else{
			extensaoArquivoDiferentePDF = Boolean.TRUE;
		}

		processoLicitatorio = processoLicitatorioSelecionado;
	}

	public void pesquisar() {

		listProcessoLicitatorio = processoLicitatorioService.pesquisar(filtroProcessoLicitatorioDTO, "arquivo");
	}

	public void alterar() {
		
		this.cadastrar();
		this.pesquisar();
		this.preparaPesquisar();
	}

	public void excluir() {
		
		serviceProcessoLicitatorio.excluir(getProcessoLicitatorio());
	}

	public void cadastrar() {
		
		if (!arquivoAnexado){
			getProcessoLicitatorio().setArquivo(null);
		}
		
		try {
			
			processoLicitatorioValidatorView.valida(getProcessoLicitatorio());
			
			getProcessoLicitatorio().setDataUltimaAlteracao(new Date());
			getProcessoLicitatorio().setSituacao(Situacao.ATIVO);
			
			if (converterArquivoParaPDF){
				getProcessoLicitatorio().setArquivo(UtilArquivo.converterArquivoParaPDF(getProcessoLicitatorio().getArquivo()));
			}
			
			serviceProcessoLicitatorio.salvar(getProcessoLicitatorio());
			
			setProcessoLicitatorio(inicializaProcessoLicitatorio());
			arquivoAnexado = Boolean.FALSE;
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}

	}

	public void upload(FileUploadEvent event) {

		ArquivoProcessoLicitatorio arquivo = arquivoUpload(event);

		getProcessoLicitatorio().setArquivo(arquivo);
		arquivoAnexado = Boolean.TRUE;
		extensaoArquivoDiferentePDF = !arquivo.getDescricao().endsWith(".pdf");
	}
	
	private ArquivoProcessoLicitatorio arquivoUpload(FileUploadEvent event) {
		
		UploadedFile uploadedFile = event.getFile();
	    String fileName = uploadedFile.getFileName();
	    byte[] contents = uploadedFile.getContents(); 
	    
	    ArquivoProcessoLicitatorio arquivo = new ArquivoProcessoLicitatorio(); 
		arquivo.setArquivo(contents);
		arquivo.setDescricao(fileName);
		arquivo.setContentType(uploadedFile.getContentType());
		return arquivo;
	}

	public StreamedContent exportar() {
		try {

			List<ProcessoLicitatorio> documentosFiltrados = processoLicitatorioService.pesquisar(filtroProcessoLicitatorioDTO, "arquivo");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);

			for (ProcessoLicitatorio doc : documentosFiltrados) {

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

	public List<ProcessoLicitatorio> getListProcessoLicitatorio() {
		return listProcessoLicitatorio;
	}

	public ProcessoLicitatorio getProcessoLicitatorio() {
		return processoLicitatorio;
	}

	public void setProcessoLicitatorio(ProcessoLicitatorio processoLicitatorio) {
		this.processoLicitatorio = processoLicitatorio;
	}

	public FiltroProcessoLicitatorioDTO getFiltroProcessoLicitatorioDTO() {
		return filtroProcessoLicitatorioDTO;
	}

	public void setFiltroProcessoLicitatorioDTO(FiltroProcessoLicitatorioDTO filtroProcessoLicitatorioDTO) {
		this.filtroProcessoLicitatorioDTO = filtroProcessoLicitatorioDTO;
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