package br.com.ged.admin.relatorio.documento;

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

import br.com.ged.entidades.auditoria.DocumentoAudit;
import br.com.ged.entidades.auditoria.DocumentoAuditPK;
import br.com.ged.service.audit.DocumentoAuditService;

@ManagedBean  
@ApplicationScoped
public class BasicDocumentoAuditViewerController implements Serializable {  
  
    private static final long serialVersionUID = 1L;  
  
    private StreamedContent content;  
    
    @EJB
    private DocumentoAuditService serviceDoc;
    
    private DocumentoAuditPK documentoAuditPK;
  
    public StreamedContent getStreamedDownaload() {
    	
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        else if (documentoAuditPK != null){
            
        	DocumentoAudit doc = serviceDoc.getById(documentoAuditPK);
        	
        	documentoAuditPK = null;
        	
        	ByteArrayInputStream btArray = new ByteArrayInputStream(doc.getArquivo().getArquivo());
        	
            return new DefaultStreamedContent(btArray, doc.getArquivo().getContentType(), doc.getArquivo().getDescricao());  
        }
        
        return null;
    }

	public DocumentoAuditPK getDocumentoAuditPK() {
		return documentoAuditPK;
	}

	public void setDocumentoAuditPK(DocumentoAuditPK documentoAuditPK) {
		this.documentoAuditPK = documentoAuditPK;
	}

	public StreamedContent getContent() throws IOException {  
        return content;  
    }  
  
    public void setContent(StreamedContent content) {  
        this.content = content;  
    }
}  