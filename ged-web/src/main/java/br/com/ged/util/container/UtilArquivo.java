package br.com.ged.util.container;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

import br.com.ged.domain.ImagemExtensao;
import br.com.ged.entidades.Arquivo;
import br.com.ged.entidades.ArquivoBalancete;
import br.com.ged.entidades.ArquivoRecursoHumano;

public class UtilArquivo {

	public static Arquivo converterArquivoParaPDF(Arquivo arquivo) {
		
		String fileName = arquivo.getDescricao();
		byte[] contents = arquivo.getArquivo();
		
		if (!fileName.endsWith(".pdf")){
	    	
	    	Document document = new Document();
	    	
	    	String[] divideNomeArquivoPelaExtensao = StringUtils.split(fileName, ".");
	    	String nomeSemExtensao = divideNomeArquivoPelaExtensao[0];
	    	String extensao = divideNomeArquivoPelaExtensao[divideNomeArquivoPelaExtensao.length - 1];
	    	
	    	String filePDF = nomeSemExtensao+".pdf";
	    	
	    	//Converte imagem para pdf
		    if (ImagemExtensao.isImagem(extensao)){
		    	
			    ByteArrayOutputStream  file =  new ByteArrayOutputStream ();

			    try {
			    	
			    	PdfWriter.getInstance(document,file);
			       
			        document.open();
			        Image image = Image.getInstance(contents);
			        
			        //if you would have a chapter indentation
			        int indentation = 0;
			        //whatever

			        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
			                       - document.rightMargin() - indentation) / image.getWidth()) * 100;

			        image.scalePercent(scaler);

			        document.add(image);
			        document.close();
			        
			        contents = file.toByteArray();
			        fileName = filePDF;
			    } catch(Exception e){
			      e.printStackTrace();
			    }
			    
			//Converte word para pdf    
		    }else if (extensao.equals("docx")){
		    	
		    	ByteArrayInputStream in = new ByteArrayInputStream(contents);		    	
		    	XWPFDocument docx = null;
		    	ByteArrayOutputStream out = null;
				try {
					
					docx = new XWPFDocument(in);
					out = new ByteArrayOutputStream();
		            PdfOptions options = PdfOptions.create();
		            PdfConverter.getInstance().convert( docx, out, options );
		            
		            contents = out.toByteArray();
			        fileName = filePDF;
		            
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					
					try {
						out.close();
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		    }
	    }
		
		arquivo.setDescricao(fileName);
		arquivo.setArquivo(contents);
		arquivo.setContentType("application/pdf");
		
		return arquivo;
	}
	
	public static ArquivoBalancete converterArquivoParaPDF(ArquivoBalancete arquivo) {
		
		String fileName = arquivo.getDescricao();
		byte[] contents = arquivo.getArquivo();
		
		if (!fileName.endsWith(".pdf")){
	    	
	    	Document document = new Document();
	    	
	    	String[] divideNomeArquivoPelaExtensao = StringUtils.split(fileName, ".");
	    	String nomeSemExtensao = divideNomeArquivoPelaExtensao[0];
	    	String extensao = divideNomeArquivoPelaExtensao[divideNomeArquivoPelaExtensao.length - 1];
	    	
	    	String filePDF = nomeSemExtensao+".pdf";
	    	
	    	//Converte imagem para pdf
		    if (ImagemExtensao.isImagem(extensao)){
		    	
			    ByteArrayOutputStream  file =  new ByteArrayOutputStream ();

			    try {
			    	
			    	PdfWriter.getInstance(document,file);
			       
			        document.open();
			        Image image = Image.getInstance(contents);
			        
			        //if you would have a chapter indentation
			        int indentation = 0;
			        //whatever

			        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
			                       - document.rightMargin() - indentation) / image.getWidth()) * 100;

			        image.scalePercent(scaler);

			        document.add(image);
			        document.close();
			        
			        contents = file.toByteArray();
			        fileName = filePDF;
			    } catch(Exception e){
			      e.printStackTrace();
			    }
			    
			//Converte word para pdf    
		    }else if (extensao.equals("docx")){
		    	
		    	ByteArrayInputStream in = new ByteArrayInputStream(contents);		    	
		    	XWPFDocument docx = null;
		    	ByteArrayOutputStream out = null;
				try {
					
					docx = new XWPFDocument(in);
					out = new ByteArrayOutputStream();
		            PdfOptions options = PdfOptions.create();
		            PdfConverter.getInstance().convert( docx, out, options );
		            
		            contents = out.toByteArray();
			        fileName = filePDF;
		            
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					
					try {
						out.close();
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		    }
	    }
		
		arquivo.setDescricao(fileName);
		arquivo.setArquivo(contents);
		arquivo.setContentType("application/pdf");
		
		return arquivo;
	}
	
	public static ArquivoRecursoHumano converterArquivoParaPDF(ArquivoRecursoHumano arquivo) {
		
		String fileName = arquivo.getDescricao();
		byte[] contents = arquivo.getArquivo();
		
		if (!fileName.endsWith(".pdf")){
	    	
	    	Document document = new Document();
	    	
	    	String[] divideNomeArquivoPelaExtensao = StringUtils.split(fileName, ".");
	    	String nomeSemExtensao = divideNomeArquivoPelaExtensao[0];
	    	String extensao = divideNomeArquivoPelaExtensao[divideNomeArquivoPelaExtensao.length - 1];
	    	
	    	String filePDF = nomeSemExtensao+".pdf";
	    	
	    	//Converte imagem para pdf
		    if (ImagemExtensao.isImagem(extensao)){
		    	
			    ByteArrayOutputStream  file =  new ByteArrayOutputStream ();

			    try {
			    	
			    	PdfWriter.getInstance(document,file);
			       
			        document.open();
			        Image image = Image.getInstance(contents);
			        
			        //if you would have a chapter indentation
			        int indentation = 0;
			        //whatever

			        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
			                       - document.rightMargin() - indentation) / image.getWidth()) * 100;

			        image.scalePercent(scaler);

			        document.add(image);
			        document.close();
			        
			        contents = file.toByteArray();
			        fileName = filePDF;
			    } catch(Exception e){
			      e.printStackTrace();
			    }
			    
			//Converte word para pdf    
		    }else if (extensao.equals("docx")){
		    	
		    	ByteArrayInputStream in = new ByteArrayInputStream(contents);		    	
		    	XWPFDocument docx = null;
		    	ByteArrayOutputStream out = null;
				try {
					
					docx = new XWPFDocument(in);
					out = new ByteArrayOutputStream();
		            PdfOptions options = PdfOptions.create();
		            PdfConverter.getInstance().convert( docx, out, options );
		            
		            contents = out.toByteArray();
			        fileName = filePDF;
		            
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					
					try {
						out.close();
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		    }
	    }
		
		arquivo.setDescricao(fileName);
		arquivo.setArquivo(contents);
		arquivo.setContentType("application/pdf");
		
		return arquivo;
	}
}
