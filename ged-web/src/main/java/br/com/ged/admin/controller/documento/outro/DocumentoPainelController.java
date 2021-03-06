package br.com.ged.admin.controller.documento.outro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;

import br.com.ged.admin.controller.documento.DocumentoSuperController;
import br.com.ged.domain.Mensagem;
import br.com.ged.domain.Pagina;
import br.com.ged.domain.entidade.Situacao;
import br.com.ged.domain.entidade.TipoFuncionalidadeEnum;
import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.dto.FiltroDocumentoDTO;
import br.com.ged.entidades.Arquivo;
import br.com.ged.entidades.Categoria;
import br.com.ged.entidades.Documento;
import br.com.ged.entidades.GrupoUsuario;
import br.com.ged.entidades.TipoDocumento;
import br.com.ged.entidades.Usuario;
import br.com.ged.excecao.NegocioException;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.service.CategoriaDocumentoService;
import br.com.ged.service.DocumentoService;
import br.com.ged.service.GrupoUsuarioService;
import br.com.ged.service.audit.DocumentoAuditService;
import br.com.ged.util.container.UtilArquivo;

@ManagedBean(name="painelDocumento")
@SessionScoped
public class DocumentoPainelController extends DocumentoSuperController{
	
	private List<Documento> listDocumento;
	private Documento documentoSelecionado;
	
	@EJB
	private DocumentoService documentoService;
	
	@EJB
	private GrupoUsuarioService grupoUsuarioService;
	
	@EJB
	protected GenericServiceController<Categoria, Long> serviceCategoria;
	
	@EJB
	protected GenericServiceController<TipoDocumento, Long> serviceTipoDocumento;
	
	@EJB
	protected GenericServiceController<Documento, Long> serviceDocumento;
	
	@EJB
	protected GenericServiceController<Arquivo, Long> serviceArquivo;
	
	private TreeNode categorias;
    private Categoria categoriaSelecionada;
    private Boolean diretorioRaizSelecionado;
    
    private Categoria categoria;
    
    private boolean renderizaBotoesAlterarExcluirCategoria;
    private boolean renderizaCategoriaSelecionada;
    
    @EJB
    private CategoriaDocumentoValidadorView categoriaValidador;
    
    @EJB
	private CategoriaDocumentoService categoriaService;
    
    //Tipo documento
    private List<TipoDocumento> listTipoDocumento;
    private TipoDocumento tipoDocumentoSelecionado;
    private boolean renderizaBotoesAlterarExcluirTipoDocumento;
    
    private TipoDocumento tipoDocumento;
    
    @EJB
    private TipoDocumentoValidadorView tipoDocumentoValidador;
    
    @ManagedProperty("#{tipoDocumentoController}")
    private TipoDocumentoController tipoDocumentoController;
    
    private Documento documento;
    
    private boolean renderizaCadastro = false;
    //Flag para controlar a exibição do painel de exportação de documentos
    private boolean renderizaExportar = false;
    private String tituloFildSetDocumento;
    private boolean arquivoAnexado;
    private boolean renderizaAlterar = false;
    
    private FiltroDocumentoDTO filtroDocumentoDTO;
    
    private boolean pesquisarSubCategorias;
    
    private boolean extensaoArquivoDiferentePDF;
    private boolean converterArquivoParaPDF;
	private boolean pesquisarTodosTiposDocuemento;
	
	private List<String> listGrupoUsuarioCategoria;
	private List<String> listGrupoUsuarioCategoriaSelecionados;
	
	@EJB
	protected DocumentoAuditService documentoAuditService;
	private Documento documentoPreAlteracao;
	
	@PostConstruct
	public void inicio(){
		
		diretorioRaizSelecionado = Boolean.FALSE;

		categoria = new Categoria();
		limpaCategoriaSelecionada();
		
		tipoDocumento = new TipoDocumento();
		tipoDocumentoSelecionado = new TipoDocumento();
		
		if (super.externalContext().getSessionMap().get(TipoDocumentoController.NOME_CONTROLLER) == null){
			super.externalContext().getSessionMap().put(TipoDocumentoController.NOME_CONTROLLER, tipoDocumentoController);
		}
		
		documento = inicializaDocumento();
		documentoSelecionado = inicializaDocumento();
		listDocumento = new ArrayList<Documento>();
		listGrupoUsuarioCategoria= new ArrayList<>();
		
		filtroDocumentoDTO = new FiltroDocumentoDTO();
		
		iniciaCategoria();
		iniciaTipoDocumento();
		renderizaTituloFieldSet();
	}
	
	public boolean getRenderizaBotaoNovaCategoria(){
		
		if (!renderizaCadastro || renderizaAlterar){
			return super.autorizaFuncionalidade(TipoFuncionalidadeEnum.CADASTRAR_CATEGORIA_DOCUMENTO);
		}
		
		return true;
	}
	
	public void consultaListGrupoUsuario(){
		
		if (categoriaSelecionada != null && categoriaSelecionada.getId() != null){
			diretorioRaizSelecionado = Boolean.FALSE;
		}else{
			diretorioRaizSelecionado = Boolean.TRUE;
		}
		
		listGrupoUsuarioCategoria = grupoUsuarioService.listNomeGrupoUsuario();
		listGrupoUsuarioCategoriaSelecionados = new ArrayList<>();
	}
	
	public void selecionaTodosGruposUsuario(){
		listGrupoUsuarioCategoriaSelecionados = new ArrayList<>();
		listGrupoUsuarioCategoriaSelecionados.addAll(listGrupoUsuarioCategoria);
	}
	
	public void selecionaNenhumGruposUsuario(){
		listGrupoUsuarioCategoriaSelecionados = new ArrayList<>();
	}

	private Documento inicializaDocumento() {
		
		Documento novoDoc = new Documento();
		novoDoc.setCategoria(new Categoria());
		novoDoc.setTipoDocumento(new TipoDocumento());
		novoDoc.setArquivo(new Arquivo());
		
		return novoDoc;
	}
	
	private void renderizaTituloFieldSet() {
		
		if(renderizaCadastro){
			tituloFildSetDocumento = "Informações para cadastro";
		}else if(renderizaExportar){
			tituloFildSetDocumento = "Exportar Documentos";
		}else{
			tituloFildSetDocumento = "Filtro para Pesquisa";
		}
	}

	public void preparaCadastro(){
		
		arquivoAnexado = Boolean.FALSE;
		renderizaCadastro = Boolean.TRUE;
		renderizaAlterar = Boolean.FALSE;
		renderizaExportar = Boolean.FALSE;
		renderizaTituloFieldSet();
		documento = inicializaDocumento();
		documentoSelecionado = inicializaDocumento();
		converterArquivoParaPDF = Boolean.FALSE;
		extensaoArquivoDiferentePDF = Boolean.FALSE;
	}
	
	public void preparaPesquisa(){
		
		arquivoAnexado = false;
		renderizaCadastro = false;
		renderizaAlterar = Boolean.FALSE;
		renderizaExportar = Boolean.FALSE;
		renderizaTituloFieldSet();
		documento = inicializaDocumento();
		documentoSelecionado = inicializaDocumento();
		listDocumento = new ArrayList<>();
		
		converterArquivoParaPDF = Boolean.FALSE;
		extensaoArquivoDiferentePDF = Boolean.FALSE;
	}
	
	public void preparaExportar(){
		
		arquivoAnexado = false;
		renderizaCadastro = false;
		renderizaAlterar = Boolean.FALSE;
		renderizaExportar = Boolean.TRUE;
		renderizaTituloFieldSet();
		documento = inicializaDocumento();
		documentoSelecionado = inicializaDocumento();
		listDocumento = new ArrayList<>();
		
		converterArquivoParaPDF = Boolean.FALSE;
		extensaoArquivoDiferentePDF = Boolean.FALSE;
	}
	
	public void preparaAlterar(Documento doc){
		
		documentoSelecionado = serviceDocumento.getById(Documento.class, doc.getId(), "arquivo","categoria.listGrupoUsuario", "categoria.listGrupoUsuario.usuarios");
		arquivoAnexado = doc.getArquivo().getId() != null;
		renderizaAlterar = Boolean.TRUE;
		renderizaCadastro = Boolean.FALSE;
		renderizaExportar = Boolean.FALSE;
		
		if (doc.getArquivo().getDescricao().endsWith(".pdf")){
			
			converterArquivoParaPDF = Boolean.FALSE;
			extensaoArquivoDiferentePDF = Boolean.FALSE;
		}else{
			extensaoArquivoDiferentePDF = Boolean.TRUE;
		}
		
		preAlteracaoAuditoria(documentoSelecionado);
	}
	
	private void preAlteracaoAuditoria(Documento docSelecionado) {
		
		try {
			documentoPreAlteracao = (Documento) BeanUtils.cloneBean(docSelecionado);
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
	
	private void iniciaCategoria() {
		
		List<Categoria> listCategoria = serviceCategoria.listarTodos(Categoria.class,"listGrupoUsuario","listGrupoUsuario.usuarios");
		
		Collections.sort(listCategoria);
		
		listCategoria = verificaPermissaoAcesso(listCategoria);
		
		if (listCategoria == null || listCategoria.isEmpty()){
			
			renderizaBotoesAlterarExcluirCategoria = Boolean.FALSE;
			return;
		}
		
		categorias = new DefaultTreeNode(new Categoria(), null);
		
		for (Categoria cat : listCategoria){
			
			if (verificaRamoJaAdicionado(categorias, cat)){
				continue;
			}
			
			if (cat.getCategoriaPai() == null){
				
				TreeNode categoriaPai = new DefaultTreeNode(cat, categorias);
				adicionaPastasFilhas(cat, categoriaPai);
			}
		}
		
		renderizaBotoesAlterarExcluirCategoria = Boolean.TRUE;
	}

	private List<Categoria> verificaPermissaoAcesso(List<Categoria> listCategoria) {
		
		List<Categoria> listCat = new ArrayList<>();
		
		for (Categoria cat : listCategoria){
			
			if (cat.getListGrupoUsuario() == null || verificaGruposSemUsuario(cat.getListGrupoUsuario())){
				continue;
			}
			
			List<Usuario> usuariosComPermissao = todosUsuariosComPermissao(cat.getListGrupoUsuario());
			
			for (Usuario usr : usuariosComPermissao){
				
				if (usr.getId().equals(getUsuarioLogado().getId())){
					
					listCat.add(cat);
				}
			}
		} 
		
		return listCat;
	}

	private List<Usuario> todosUsuariosComPermissao(List<GrupoUsuario> listGrupoUsuario) {
		
		List<Usuario> listUsuario = new ArrayList<>();
		
		for (GrupoUsuario gu : listGrupoUsuario){
			
			if (gu.getUsuarios() == null){
				continue;
			}
			
			for (Usuario usr : gu.getUsuarios()){
				
				listUsuario.add(usr);
			}
		}
		
		return listUsuario;
	}

	private boolean verificaGruposSemUsuario(List<GrupoUsuario> listGrupoUsuario) {
		
		for (GrupoUsuario gu : listGrupoUsuario){
			
			if (gu.getUsuarios() == null || gu.getUsuarios().isEmpty()){
				
				continue;
			}
			
			return false;
		}
		
		return true;
	}

	private boolean verificaRamoJaAdicionado(TreeNode tree,Categoria cat) {
		
		for (TreeNode tr : tree.getChildren()){
			
			Categoria categoria = (Categoria)tr.getData();
			
			if (categoria != null && categoria.getId().equals(cat.getId())){
				return true;
			}
		}
		
		return false;
	}

	private void adicionaPastasFilhas(Categoria cat, TreeNode diretorioPai) {
		
		if (cat.getCategoriaFilha() == null || cat.getCategoriaFilha().isEmpty()){
			return;
		}
		
		for (Categoria filho : cat.getCategoriaFilha()){
			TreeNode diretorioPaiInterno = new DefaultTreeNode(filho, diretorioPai);
			adicionaPastasFilhas(filho, diretorioPaiInterno);
		}
	}
	
	public void onNodeSelect(NodeSelectEvent event) {
		
		categoria = new Categoria();
		categoriaSelecionada = (Categoria) event.getTreeNode().getData();
		renderizaCategoriaSelecionada = Boolean.TRUE;
		
		documentoSelecionado.setCategoria(categoriaSelecionada);
    }
	
	public void alterarCategoria(){
		
		try {
			adicionaGruposUsuarioCategoria(categoriaSelecionada);
			
			categoriaValidador.validaAlteracao(categoriaSelecionada);
			
			serviceCategoria.merge(categoriaSelecionada);
			iniciaCategoria();
			limpaCategoriaSelecionada();
			categoria = new Categoria();
			
			super.fecharModal("dgAlterarCategoria");
			super.updateForm();
			
		} catch (NegocioException e) {
			e.printStackTrace();
			iniciaCategoria();
			categoriaSelecionada = new Categoria();
		}
	}

	private void limpaCategoriaSelecionada() {
		categoriaSelecionada = new Categoria();
		renderizaCategoriaSelecionada = false;
	}
	
	public void verificaExclusaoCategoria(){
		
		if (categoriaSelecionada != null && categoriaSelecionada.getId() != null){
			super.abrirModal("confirmaExclusaoCategoria");
		}else{
			enviaMensagem(Mensagem.CATDOC2);
		}
	}
	
	public void verificaAlteracaoCategoria(){
		
		if (categoriaSelecionada != null && categoriaSelecionada.getId() != null){
			
			selecionaNenhumGruposUsuario();
			
			consultaListGrupoUsuario();
			
			for (GrupoUsuario gu : categoriaSelecionada.getListGrupoUsuario()){
				
				listGrupoUsuarioCategoriaSelecionados.add(gu.getGrupo());
			}
			
			super.abrirModal("dgAlterarCategoria");
		}else{
			enviaMensagem(Mensagem.CATDOC2);
		}
	}
	
	public void excluirCategoria(){
			
		try{
			
			//TODO Auditoria
			categoriaService.excluir(categoriaSelecionada);
			iniciaCategoria();
			categoria = new Categoria();
			limpaCategoriaSelecionada();
			enviaMensagem(Mensagem.MDF001);
			
		}catch(NegocioException e){
			
			e.printStackTrace();
		}
	}
	
	public void novaCategoria(){
		
		try {
			
			adicionaGruposUsuarioCategoria(categoria);
			
			categoriaValidador.validaCadastro(categoria);
			
			if (diretorioRaizSelecionado || categoriaSelecionada == null){
				
				categoria.setCategoriaPai(null);
			}else if (categoriaSelecionada.getId() != null){
				categoria.setCategoriaPai(categoriaSelecionada);
			}
			
			//TODO Auditoria
			serviceCategoria.salvar(categoria);
			iniciaCategoria();
			categoriaSelecionada = new Categoria();
			categoria = new Categoria();
			
			super.fecharModal("dgCategoria");
			super.updateForm();
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}

	private void adicionaGruposUsuarioCategoria(Categoria categoria) {
		
		List<GrupoUsuario> listGrupoUsuario = new ArrayList<>();
		
		for (String nomeGrupo : listGrupoUsuarioCategoriaSelecionados){
			
			listGrupoUsuario.addAll(grupoUsuarioService.gruposUsuarioPorNome(nomeGrupo));
		}
		
		categoria.setListGrupoUsuario(listGrupoUsuario);
	}
	
	//Tipo documento
	private void iniciaTipoDocumento() {
		
		listTipoDocumento = serviceTipoDocumento.listarTodos(TipoDocumento.class);
		
		Collections.sort(listTipoDocumento);
		
		if (listTipoDocumento == null || listTipoDocumento.isEmpty()){
			
			renderizaBotoesAlterarExcluirTipoDocumento = Boolean.FALSE;
			return;
		}else{
			renderizaBotoesAlterarExcluirTipoDocumento = Boolean.TRUE;
		}
	}
	
	public void novoTipoDocumento(){
		
		try {
			
			tipoDocumentoValidador.validaCadastro(tipoDocumento);
			
			//TODO Auditoria
			serviceTipoDocumento.salvar(tipoDocumento);
			iniciaTipoDocumento();
			tipoDocumentoSelecionado = new TipoDocumento();
			tipoDocumento = new TipoDocumento();
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void onSelect(SelectEvent event) {
		this.tipoDocumentoSelecionado = (TipoDocumento) event.getObject();
		
		this.documentoSelecionado.setTipoDocumento(tipoDocumentoSelecionado);
    }
	
	public void verificaExclusaoTipoDocumento(){
		
		if (tipoDocumentoSelecionado != null && tipoDocumentoSelecionado.getId() != null){
			super.abrirModal("confirmaExclusaoTipoDoc");
		}else{
			enviaMensagem(Mensagem.TPDOC2);
		}
	}
	
	public void verificaAlteracaoTipoDocumento(){
		
		if (tipoDocumentoSelecionado != null && tipoDocumentoSelecionado.getId() != null){
			super.abrirModal("dgAlterarTipoDocumento");
		}else{
			enviaMensagem(Mensagem.TPDOC2);
		}
	}
	
	public void excluirTipoDocumento(){
		
		try{
			
			//TODO Auditoria
			serviceTipoDocumento.excluir(tipoDocumentoSelecionado);
			iniciaTipoDocumento();
			tipoDocumento = new TipoDocumento();
			
		}catch(ConstraintViolationException cve){
			cve.printStackTrace();
			
			enviaMensagem(Mensagem.TPDOC3); 
		}
	}
	
	public void alterarTipoDocumento(){
		
		try {
			
			tipoDocumentoValidador.validaAlteracao(tipoDocumentoSelecionado);
			
			//TODO Auditoria
			serviceTipoDocumento.merge(tipoDocumentoSelecionado);
			iniciaTipoDocumento();
			tipoDocumentoSelecionado = null;
			tipoDocumento = new TipoDocumento();
			
		} catch (NegocioException e) {
			e.printStackTrace();
			iniciaTipoDocumento();
			tipoDocumentoSelecionado = new TipoDocumento();
		}
	}
	
	public String pathCategoria(Categoria pai, Categoria atual, String path){
		if(pai.getId() == atual.getId()){
			return pai.getDescricao();
		}
		return pathCategoria(pai, atual.getCategoriaPai(), path) + File.separator + atual.getDescricao();  
	}
	
	public StreamedContent exportar(){
		try {			
			documento.setCategoria(getCategoriaSelecionada());						
			
			documentoValidatorView.validaExportar(documento);
			
			filtroDocumentoDTO.setSubCategorias(new ArrayList<Long>());
			filtroDocumentoDTO.getSubCategorias().add(getCategoriaSelecionada().getId());
			
			if (pesquisarSubCategorias){
				filtroDocumentoDTO.getSubCategorias().addAll(extraiIdsSubCategoriasSelecionadas(getCategoriaSelecionada().getCategoriaFilha()));
			}
						
			filtroDocumentoDTO.setIdTipoDocumento(null);			
			
			List<Documento> documentosFiltrados = documentoService.pesquisar(filtroDocumentoDTO,"arquivo","categoria","categoria.listGrupoUsuario", "categoria.listGrupoUsuario.usuarios");
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);
									
			for (Documento doc : documentosFiltrados){
				String docDescricao = doc.getArquivo().getDescricao();
				/*String zipArquivoDescricao = doc.getCategoria().getId() ==  getCategoriaSelecionada().getId() ? docDescricao :
					createPathFromCategory(categoriaSelecionada, doc.getCategoria(), "") + File.separator + docDescricao;*/						
				String zipArquivoDescricao = pathCategoria(categoriaSelecionada, doc.getCategoria(), "") + File.separator + docDescricao;
				ZipEntry entry = new ZipEntry(zipArquivoDescricao);
				entry.setSize(doc.getArquivo().getArquivo().length);
				zos.putNextEntry(entry);
				zos.write(doc.getArquivo().getArquivo());
				
				documentoAuditService.auditoria(doc, TipoOperacaoAudit.EXPORTADO);
				
				zos.closeEntry();									
			}												
			zos.close();
			ByteArrayInputStream btArray = new ByteArrayInputStream(baos.toByteArray());        	
            return new DefaultStreamedContent(btArray, "application/zip", getCategoriaSelecionada().getDescricao() + ".zip");          
            
		} catch (NegocioException e) {
			e.printStackTrace();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void pesquisar(){
		
		try {
			
			documento.setCategoria(getCategoriaSelecionada());
			documento.setTipoDocumento(getTipoDocumentoSelecionado());
			
			//Verificar Campos Obrigatorios
			documentoValidatorView.validaPesquisa(documento);
			
			filtroDocumentoDTO.setDescricao(documento.getDescricao());
			filtroDocumentoDTO.setIdTipoDocumento(getTipoDocumentoSelecionado().getId());
			filtroDocumentoDTO.setObservacao(documento.getObservacao());
			
			filtroDocumentoDTO.setSubCategorias(new ArrayList<Long>());
			filtroDocumentoDTO.getSubCategorias().add(getCategoriaSelecionada().getId());
			
			if (pesquisarSubCategorias){
				filtroDocumentoDTO.getSubCategorias().addAll(extraiIdsSubCategoriasSelecionadas(getCategoriaSelecionada().getCategoriaFilha()));
			}
			
			if (pesquisarTodosTiposDocuemento){
				filtroDocumentoDTO.setIdTipoDocumento(null);
			}
			
			filtroDocumentoDTO.setSituacao(Situacao.ATIVO);
			
			listDocumento = documentoService.pesquisar(filtroDocumentoDTO,"arquivo");
			
			for (Documento doc : listDocumento){
				doc.getArquivo().setArquivo(null);
			}
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	private List<Long> extraiIdsSubCategoriasSelecionadas(List<Categoria> categoriaFilha) {
		
		if (categoriaFilha == null){
			return null;
		}
		
		List<Long> ids = new ArrayList<>();
		
		for (Categoria cats : categoriaFilha){
			
			ids.add(cats.getId());
			
			if (cats.getCategoriaFilha() != null && !cats.getCategoriaFilha().isEmpty()){
				ids.addAll(extraiIdsSubCategoriasSelecionadas(cats.getCategoriaFilha()));
			}
		}
		
		return ids;
	}

	public void excluirDocumento(){
		
		setDocumentoSelecionado(serviceDocumento.getById(Documento.class, documentoSelecionado.getId(), "arquivo","categoria.listGrupoUsuario", "categoria.listGrupoUsuario.usuarios"));
		
		getDocumentoSelecionado().setDataUltimaAlteracao(new Date());
		getDocumentoSelecionado().setUsuario(getUsuarioLogado());
		
		//TODO Auditoria
		serviceDocumento.excluir(getDocumentoSelecionado());
		documentoAuditService.auditoria(getDocumentoSelecionado(), TipoOperacaoAudit.EXCLUIDO);
		
		documento = inicializaDocumento();
		documentoSelecionado = inicializaDocumento();
		this.pesquisar();
	}
	
	public void cadastrarDocumento(){
		
		if (!arquivoAnexado){
			getDocumento().setArquivo(null);
		}
		
		getDocumento().setCategoria(getCategoriaSelecionada());
		getDocumento().setUsuario(getUsuarioLogado());
		getDocumento().setTipoDocumento(getTipoDocumentoSelecionado());
		
		try {
			
			documentoValidatorView.validaCadastro(getDocumento());
			
			getDocumento().setDataUltimaAlteracao(new Date());
			getDocumento().setDataInclusao(new Date());
			
			getDocumento().setSituacao(Situacao.ATIVO);
			
			if (converterArquivoParaPDF){
				getDocumento().setArquivo(UtilArquivo.converterArquivoParaPDF(getDocumento().getArquivo()));
			}
			
			//TODO Auditoria
			serviceDocumento.salvar(getDocumento());
			documentoAuditService.auditoria(getDocumento(), TipoOperacaoAudit.CADASTRADO);
			
			setDocumento(inicializaDocumento());
			arquivoAnexado = Boolean.FALSE;
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}

	public void alterarDocumento(){
		
		if (!arquivoAnexado){
			getDocumentoSelecionado().setArquivo(null);
		}
		
		if (getCategoriaSelecionada().getId() != null){
			getDocumentoSelecionado().setCategoria(getCategoriaSelecionada());
		}
		
		if (getTipoDocumentoSelecionado().getId() != null){
			getDocumentoSelecionado().setTipoDocumento(getTipoDocumentoSelecionado());
		}
		
		getDocumentoSelecionado().setUsuario(getUsuarioLogado());
		
		try {
			
			documentoValidatorView.validaCadastro(getDocumentoSelecionado());
			
			getDocumentoSelecionado().setDataUltimaAlteracao(new Date());
			getDocumentoSelecionado().setSituacao(Situacao.ATIVO);
			
			if (getDocumentoSelecionado().getArquivo() == null || getDocumentoSelecionado().getArquivo().getArquivo() == null){
				getDocumentoSelecionado().setArquivo(serviceDocumento.getById(Documento.class, getDocumentoSelecionado().getId(),"arquivo").getArquivo());
			}
			
			if (converterArquivoParaPDF){
				getDocumentoSelecionado().setArquivo(UtilArquivo.converterArquivoParaPDF(getDocumentoSelecionado().getArquivo()));
			}
			
			//TODO Auditoria
			serviceDocumento.merge(getDocumentoSelecionado());
			documentoAuditService.auditoriaAlterar(this.documentoPreAlteracao, getDocumentoSelecionado());
			
			setDocumentoSelecionado(inicializaDocumento());
			arquivoAnexado = Boolean.FALSE;
			
			this.preparaPesquisa();
			
		} catch (NegocioException e) {
			e.printStackTrace();
		}
	}
	
	public void upload(FileUploadEvent event) {
		
	    Arquivo arquivo = arquivoUpload(event);
		
		getDocumento().setArquivo(arquivo);
		arquivoAnexado = Boolean.TRUE;
		extensaoArquivoDiferentePDF = !arquivo.getDescricao().endsWith(".pdf");
	}
	
	public void uploadAlterar(FileUploadEvent event) {
		
	    Arquivo arquivo = arquivoUpload(event);
		
		getDocumentoSelecionado().setArquivo(arquivo);
		arquivoAnexado = Boolean.TRUE;
		extensaoArquivoDiferentePDF = !arquivo.getDescricao().endsWith(".pdf");
	}

	private Arquivo arquivoUpload(FileUploadEvent event) {
		
		UploadedFile uploadedFile = event.getFile();
	    String fileName = uploadedFile.getFileName();
	    byte[] contents = uploadedFile.getContents(); 
	    
		Arquivo arquivo = new Arquivo(); 
		arquivo.setArquivo(contents);
		arquivo.setDescricao(fileName);
		arquivo.setContentType(uploadedFile.getContentType());
		return arquivo;
	}
	
	@Override
	protected Pagina getPaginaManageBean() {
		return Pagina.PAINEL_DOCUMENTO;
	}

	public TreeNode getCategorias() {
		return categorias;
	}

	public void setCategorias(TreeNode categorias) {
		this.categorias = categorias;
	}

	public Categoria getCategoriaSelecionada() {
		return categoriaSelecionada;
	}

	public Boolean getDiretorioRaizSelecionado() {
		return diretorioRaizSelecionado;
	}

	public void setDiretorioRaizSelecionado(Boolean diretorioRaizSelecionado) {
		this.diretorioRaizSelecionado = diretorioRaizSelecionado;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public boolean isRenderizaBotoesAlterarExcluirCategoria() {
		
		return renderizaBotoesAlterarExcluirCategoria;
	}

	public void setRenderizaBotoesAlterarExcluirCategoria(boolean renderizaBotoesAlterarExcluirCategoria) {
		this.renderizaBotoesAlterarExcluirCategoria = renderizaBotoesAlterarExcluirCategoria;
	}

	public boolean isRenderizaCategoriaSelecionada() {
		return renderizaCategoriaSelecionada;
	}

	public void setRenderizaCategoriaSelecionada(boolean renderizaCategoriaSelecionada) {
		this.renderizaCategoriaSelecionada = renderizaCategoriaSelecionada;
	}

	public List<TipoDocumento> getListTipoDocumento() {
		return listTipoDocumento;
	}

	public void setListTipoDocumento(List<TipoDocumento> listTipoDocumento) {
		this.listTipoDocumento = listTipoDocumento;
	}

	public TipoDocumento getTipoDocumentoSelecionado() {
		return tipoDocumentoSelecionado;
	}

	public void setTipoDocumentoSelecionado(TipoDocumento tipoDocumentoSelecionado) {
		this.tipoDocumentoSelecionado = tipoDocumentoSelecionado;
	}

	public boolean isRenderizaBotoesAlterarExcluirTipoDocumento() {
		return renderizaBotoesAlterarExcluirTipoDocumento;
	}

	public void setRenderizaBotoesAlterarExcluirTipoDocumento(boolean renderizaBotoesAlterarExcluirTipoDocumento) {
		this.renderizaBotoesAlterarExcluirTipoDocumento = renderizaBotoesAlterarExcluirTipoDocumento;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public TipoDocumentoController getTipoDocumentoController() {
		return tipoDocumentoController;
	}

	public void setTipoDocumentoController(TipoDocumentoController tipoDocumentoController) {
		this.tipoDocumentoController = tipoDocumentoController;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public List<Documento> getListDocumento() {
		return listDocumento;
	}

	public void setListDocumento(List<Documento> listDocumento) {
		this.listDocumento = listDocumento;
	}

	public Documento getDocumentoSelecionado() {
		return documentoSelecionado;
	}

	public void setDocumentoSelecionado(Documento documentoSelecionado) {
		this.documentoSelecionado = documentoSelecionado;
	}

	public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
		this.categoriaSelecionada = categoriaSelecionada;
	}

	public boolean isRenderizaCadastro() {
		return renderizaCadastro;
	}

	public void setRenderizaCadastro(boolean renderizaCadastro) {
		this.renderizaCadastro = renderizaCadastro;
	}

	public String getTituloFildSetDocumento() {
		return tituloFildSetDocumento;
	}

	public void setTituloFildSetDocumento(String tituloFildSetDocumento) {
		this.tituloFildSetDocumento = tituloFildSetDocumento;
	}

	public boolean isArquivoAnexado() {
		return arquivoAnexado;
	}

	public void setArquivoAnexado(boolean arquivoAnexado) {
		this.arquivoAnexado = arquivoAnexado;
	}

	public boolean isRenderizaAlterar() {
		return renderizaAlterar;
	}

	public void setRenderizaAlterar(boolean renderizaAlterar) {
		this.renderizaAlterar = renderizaAlterar;
	}

	public FiltroDocumentoDTO getFiltroDocumentoDTO() {
		return filtroDocumentoDTO;
	}

	public void setFiltroDocumentoDTO(FiltroDocumentoDTO filtroDocumentoDTO) {
		this.filtroDocumentoDTO = filtroDocumentoDTO;
	}

	public boolean isPesquisarSubCategorias() {
		return pesquisarSubCategorias;
	}

	public void setPesquisarSubCategorias(boolean pesquisarSubCategorias) {
		this.pesquisarSubCategorias = pesquisarSubCategorias;
	}

	public boolean isPesquisarTodosTiposDocuemento() {
		return pesquisarTodosTiposDocuemento;
	}

	public void setPesquisarTodosTiposDocuemento(boolean pesquisarTodosTiposDocuemento) {
		this.pesquisarTodosTiposDocuemento = pesquisarTodosTiposDocuemento;
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

	public List<String> getListGrupoUsuarioCategoria() {
		return listGrupoUsuarioCategoria;
	}

	public void setListGrupoUsuarioCategoria(List<String> listGrupoUsuarioCategoria) {
		this.listGrupoUsuarioCategoria = listGrupoUsuarioCategoria;
	}

	public List<String> getListGrupoUsuarioCategoriaSelecionados() {
		return listGrupoUsuarioCategoriaSelecionados;
	}

	public void setListGrupoUsuarioCategoriaSelecionados(List<String> listGrupoUsuarioCategoriaSelecionados) {
		this.listGrupoUsuarioCategoriaSelecionados = listGrupoUsuarioCategoriaSelecionados;
	}

	public boolean isRenderizaExportar() {
		return renderizaExportar;
	}

	public void setRenderizaExportar(boolean renderizaExportar) {
		this.renderizaExportar = renderizaExportar;
	}
}