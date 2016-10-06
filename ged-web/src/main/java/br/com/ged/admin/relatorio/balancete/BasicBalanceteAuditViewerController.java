package br.com.ged.admin.relatorio.balancete;

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

import br.com.ged.entidades.auditoria.BalanceteAudit;
import br.com.ged.entidades.auditoria.BalanceteAuditPK;
import br.com.ged.service.audit.BalanceteAuditService;

@ManagedBean  
@ApplicationScoped
public class BasicBalanceteAuditViewerController implements Serializable {  
  
    private static final long serialVersionUID = 1L;  
  
    private StreamedContent content;  
    
    @EJB
    private BalanceteAuditService serviceDoc;
    
    private BalanceteAuditPK balanceteAuditPK;
  
    public StreamedContent getStreamedDownaload() {
    	
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        else if (balanceteAuditPK != null){
            
        	BalanceteAudit doc = serviceDoc.getById(balanceteAuditPK);
        	
        	balanceteAuditPK = null;
        	
        	ByteArrayInputStream btArray = new ByteArrayInputStream(doc.getArquivo().getArquivo());
        	
            return new DefaultStreamedContent(btArray, doc.getArquivo().getContentType(), doc.getArquivo().getDescricao());  
        }
        
        return null;
    }

	public BalanceteAuditPK getBalanceteAuditPK() {
		return balanceteAuditPK;
	}

	public void setBalanceteAuditPK(BalanceteAuditPK balanceteAuditPK) {
		this.balanceteAuditPK = balanceteAuditPK;
	}

	public StreamedContent getContent() throws IOException {  
        return content;  
    }  
  
    public void setContent(StreamedContent content) {  
        this.content = content;  
    }
}  