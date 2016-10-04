package br.com.ged.admin.documento;

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

import br.com.ged.domain.TipoOperacaoAudit;
import br.com.ged.entidades.Balancete;
import br.com.ged.framework.GenericServiceController;
import br.com.ged.service.audit.BalanceteAuditService;

@ManagedBean  
@ApplicationScoped
public class BasicBalanceteViewerController implements Serializable {  
  
    private static final long serialVersionUID = 1L;  
  
    private StreamedContent content;  
    
    @EJB
    private GenericServiceController<Balancete, Long> serviceDoc;
    
    @EJB
    private BalanceteAuditService balanceteAuditService;
    
    private Long docId;
  
    public void carregaStreamArquivo(Long idDocumento) {  
  
        try {  
        	
        	if (idDocumento == null){
        		
        		System.out.println(BasicBalanceteViewerController.class.getName()+" idDocumento ta null");
        		return;
        	}
        	
        	Balancete doc = serviceDoc.getById(Balancete.class, idDocumento, "arquivo");
        	
        	balanceteAuditService.auditoriaBalancete(doc, TipoOperacaoAudit.VISUALIZADO);
        	
        	ByteArrayInputStream btArray = new ByteArrayInputStream(doc.getArquivo().getArquivo());
        	
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
            
        	Balancete doc = serviceDoc.getById(Balancete.class, docId, "arquivo");
        	
        	balanceteAuditService.auditoriaBalancete(doc, TipoOperacaoAudit.BAIXADO);
            
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