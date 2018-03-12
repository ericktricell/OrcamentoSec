/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.orcamento.documentos;

import br.com.orcamento.model.ItensOrc;
import br.com.orcamento.model.Orcamento;
import br.com.orcamento.services.relatorioProgress;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import static java.awt.Color.black;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eu
 */
public class OrcamentoMat {
    private DecimalFormat df = new DecimalFormat("¤#,##0.00");
    
    public void emite(Orcamento o) throws FileNotFoundException, DocumentException{
        String DEST = "C:\\Users\\Public\\Documents\\OrcaFacil\\" + o.getIdOrcamento() + ".pdf";
        
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(DEST));
        
        doc.open();
        
        Font fontNegrito = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20,Font.BOLD, black );
        Font fontNegrito1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD, black );
        Font fontNormal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.NORMAL, black );
        
        Paragraph line = new Paragraph("_____________________________________________________________________________");
        
        Paragraph cabecalho = new Paragraph("Orçamento nº: " + o.getIdOrcamento(), fontNegrito);
        
        Paragraph emitente = new Paragraph("Emitente: " + o.getUsuario().getNome(), fontNormal);
        
        Paragraph clien = null;
        try{
            clien = new Paragraph("Nome: " + o.getCliente().getNome() + "\n"
                    + "Tel.: " + o.getCliente().getTelefone() + "\n"
                    + "End.: " + o.getCliente().getLogradouro() + " " + o.getCliente().getNum() + " "+ 
                    o.getCliente().getBairro() + "\n"
                    + "Cep: " + o.getCliente().getCep() + " Cidade: " + o.getCliente().getCidade() + "\n\n\n", fontNormal);
        }catch(NullPointerException e){
            clien = new Paragraph("Nome: \n"
                    + "Tel.: \n"
                    + "End.: \n"
                    + "Cep: \t\tCidade: \n\n\n", fontNormal);
        }
       
        Paragraph rodape = new Paragraph("\n\nObservações: " + o.getObs() + "\n"
                    + "Prazo de Entrega: " + o.getPrazoEnt() + "\n"
                    + "Cond. Pagamento: " + o.getCondPag(), fontNormal);
        
        doc.add(cabecalho);
        doc.add(line);
        doc.add(emitente);
        doc.add(line);
        doc.add(clien);
        doc.add(getItens(o));
        doc.add(getTotal(o));
        doc.add(rodape);
        doc.newPage();
        doc.add(cabecalho);
        doc.add(line);
        doc.add(emitente);
        doc.add(line);
        doc.add(clien);
        doc.add(getMateriais(o));
        doc.add(getTotalMat(o));
        doc.add(rodape);
        doc.close();
        
        new relatorioProgress(new javax.swing.JFrame(), true).setVisible(true);
        
        try {
            Runtime.getRuntime().exec("cmd /c start " + DEST);
        } catch (IOException ex) {
            Logger.getLogger(OrcamentoMat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public PdfPTable getItens(Orcamento o) throws DocumentException{
        PdfPTable t = new PdfPTable(5);
        t.setWidthPercentage(100);
        t.setWidths(new int[]{3,2,6,3,3});
        t.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        t.addCell("Quant.");
        t.addCell("UN");
        t.addCell("Discriminação");
        t.addCell("Val. Unit.");
        t.addCell("Total");
        
        for (ItensOrc io : o.getItensOrcs()){
            if (io.getItem().getTipo().equals("Serviço")){
                t.addCell(String.valueOf(io.getNum()));
                t.addCell(io.getItem().getUn());
                t.addCell(io.getItem().getDiscriminacao());
                t.addCell(df.format(io.getItem().getVlrUnit()));
                t.addCell(df.format(io.getNum() * io.getItem().getVlrUnit()));
            }
        }
        
        return t;
    }
    
    
    public PdfPTable getMateriais(Orcamento o) throws DocumentException{
        PdfPTable t = new PdfPTable(5);
        t.setWidthPercentage(100);
        t.setWidths(new int[]{3,2,6,3,3});
        t.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        t.addCell("Quant.");
        t.addCell("UN");
        t.addCell("Discriminação");
        t.addCell("Val. Unit.");
        t.addCell("Total");
        
        for (ItensOrc io : o.getItensOrcs()){
            if (io.getItem().getTipo().equals("Material")){
                t.addCell(String.valueOf(io.getNum()));
                t.addCell(io.getItem().getUn());
                t.addCell(io.getItem().getDiscriminacao());
                t.addCell(df.format(io.getItem().getVlrUnit()));
                t.addCell(df.format(io.getNum() * io.getItem().getVlrUnit()));
            }
        }
        
        return t;
    }
    
    private PdfPTable getTotal(Orcamento o) throws DocumentException{
        PdfPTable t = new PdfPTable(2);
        t.setWidthPercentage(100);
        t.setHorizontalAlignment(Element.ALIGN_RIGHT);
        t.setWidths(new int[]{7,3});
        
        t.addCell("Total Geral");
        
        Double to = new Double("0");
        for (ItensOrc io : o.getItensOrcs()){
            if (io.getItem().getTipo().equals("Serviço")){
                to += io.getItem().getVlrUnit() * io.getNum();
            }
        }
        t.addCell(df.format(to));
        return t;
    }
    
    private PdfPTable getTotalMat(Orcamento o) throws DocumentException{
        PdfPTable t = new PdfPTable(2);
        t.setWidthPercentage(100);
        t.setHorizontalAlignment(Element.ALIGN_RIGHT);
        t.setWidths(new int[]{7,3});
        
        t.addCell("Total Geral");
        
        Double to = new Double("0");
        for (ItensOrc io : o.getItensOrcs()){
            if (io.getItem().getTipo().equals("Material")){
                to += io.getItem().getVlrUnit() * io.getNum();
            }
        }
        t.addCell(df.format(to));
        return t;
    }
}
