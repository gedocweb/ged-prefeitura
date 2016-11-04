package br.com.ged.admin.relatorio.lei;

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

import br.com.ged.entidades.auditoria.LeiAudit;
import br.com.ged.entidades.auditoria.LeiAuditPK;
import br.com.ged.service.audit.LeiAuditService;

@ManagedBean  
@ApplicationScoped
public class BasicLeiAuditViewerController implements Serializable {  
  
    private static final long serialVersionUID = 1L;  
  
    private StreamedContent content;  
    
    @EJB
    private LeiAuditService serviceDoc;
    
    private LeiAuditPK leiAuditPK;
  
    public StreamedContent getStreamedDownaload() {
    	
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        else if (leiAuditPK != null){
            
        	LeiAudit doc = serviceDoc.getById(leiAuditPK);
        	
        	leiAuditPK = null;
        	
        	ByteArrayInputStream btArray = new ByteArrayInputStream(doc.getArquivo().getArquivo());
        	
            return new DefaultStreamedContent(btArray, doc.getArquivo().getContentType(), doc.getArquivo().getDescricao());  
        }
        
        return null;
    }

	public LeiAuditPK getLeiAuditPK() {
		return leiAuditPK;
	}

	public void setLeiAuditPK(LeiAuditPK leiAuditPK) {
		this.leiAuditPK = leiAuditPK;
	}

	public StreamedContent getContent() throws IOException {  
        return content;  
    }  
  
    public void setContent(StreamedContent content) {  
        this.content = content;  
    }
}  