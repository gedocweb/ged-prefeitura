package br.com.ged.admin.controller.documento.outro;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.ged.domain.entidade.TipoOperacaoAudit;
import br.com.ged.entidades.Documento;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.service.audit.DocumentoAuditService;

@ManagedBean  
@ApplicationScoped
public class BasicDocumentViewerController implements Serializable {  
  
    private static final long serialVersionUID = 1L;  
  
    private StreamedContent content;  
    
    @EJB
    private GenericServiceController<Documento, Long> serviceDoc;
    
    private Long docId;
    
    @EJB
    private DocumentoAuditService documentoAuditService;
  
    public void carregaStreamArquivo(Long idDocumento) {  
  
        try {  
        	
            Documento doc = serviceDoc.getById(Documento.class, idDocumento, "arquivo","categoria.listGrupoUsuario", "categoria.listGrupoUsuario.usuarios");
        	
        	ByteArrayInputStream btArray = new ByteArrayInputStream(doc.getArquivo().getArquivo());
        	
        	documentoAuditService.auditoria(doc, TipoOperacaoAudit.VISUALIZADO);
        	
            content = new DefaultStreamedContent(btArray, doc.getArquivo().getContentType(), doc.getArquivo().getDescricao());  
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    public StreamedContent getStreamedImageById() {
    	
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        else if (docId != null){
            
            Documento doc = serviceDoc.getById(Documento.class, docId, "arquivo","categoria.listGrupoUsuario", "categoria.listGrupoUsuario.usuarios");
            
            documentoAuditService.auditoria(doc, TipoOperacaoAudit.BAIXADO);
            
            docId = null;
        	
        	ByteArrayInputStream btArray = new ByteArrayInputStream(doc.getArquivo().getArquivo());
        	
            return new DefaultStreamedContent(btArray, doc.getArquivo().getContentType(), doc.getArquivo().getDescricao());  
        }
        
        return null;
    }
    
    public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public StreamedContent getContent() throws IOException {  
    	
        return content;  
    }  
  
    public void setContent(StreamedContent content) {  
        this.content = content;  
    }
}  