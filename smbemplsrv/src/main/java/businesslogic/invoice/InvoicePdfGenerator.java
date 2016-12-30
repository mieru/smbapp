package businesslogic.invoice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import dbmodel.Invoice;

@Stateless
@LocalBean
public class InvoicePdfGenerator { 
	private DecimalFormat df = new DecimalFormat("#.##");
	
	public String generatePdf(Invoice fakturaSprzedazy, Float wartoscAllNetto, Float wartoscAllBrutto) throws AddressException, MessagingException, DocumentException,
			SQLException, IOException {
		JSONArray jsonArray = new JSONArray(fakturaSprzedazy.getCommodityList());
		Document document = new Document();
		String filePath = "\\faktury\\FakturaVAT" + fakturaSprzedazy.getId() + ".pdf";
		PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("jboss.server.data.dir").toString() + filePath));
		document.open();
		
		PdfPTable headerTable = new PdfPTable(3);
		headerTable.setWidthPercentage(100);
		headerTable.addCell(getCell("\nSprzedawca:\n" + fakturaSprzedazy.getIssuingEntity() + "\n", PdfPCell.ALIGN_LEFT));
		headerTable.addCell(getCell("Faktura VAT nr " + fakturaSprzedazy.getInvoiceNumber() + "\n", PdfPCell.ALIGN_CENTER));
		headerTable.addCell(getCell("\nNabywca:\n" + fakturaSprzedazy.getCustomerData() + "\n", PdfPCell.ALIGN_RIGHT));
		document.add(headerTable);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		document.add(new Paragraph("Data wystawienia: " + simpleDateFormat.format(new Date(fakturaSprzedazy.getCreationDate().getTime()))));
		
		PdfPTable table = getTable(jsonArray);
		document.add(table);
		
		PdfPTable underTable = new PdfPTable(3);
		underTable.setWidthPercentage(100);
		underTable.addCell(getCell("", PdfPCell.ALIGN_LEFT));
		underTable.addCell(getCell("", PdfPCell.ALIGN_CENTER));
		underTable.addCell(getCell("Suma netto : " + df.format(wartoscAllNetto.doubleValue()) + " zl\n"
		+ "Stawka VAT : " + df.format(Float.valueOf((wartoscAllBrutto - wartoscAllNetto)).doubleValue()) + " zl\n"
		+ "\nDo zaplaty : " + df.format(wartoscAllBrutto.doubleValue()) + " zl\n", PdfPCell.ALIGN_RIGHT));
		document.add(underTable);
		
		document.close();
		return filePath;
	}

	private PdfPCell getCell(String text, int alignment) {
	    PdfPCell cell = new PdfPCell(new Phrase(text));
	    cell.setPadding(0);
	    cell.setHorizontalAlignment(alignment);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    return cell;
	}
	
	private PdfPTable getTable(JSONArray jsonArray) throws SQLException, DocumentException, IOException {
            PdfPTable table = new PdfPTable(new float[] { 1, 5, 1, 1, 1, 1, 1});
            table.setWidthPercentage(100f);
            
            	table.addCell("Lp.");
                table.addCell("Nazwa");
                table.addCell("Ilosc");
                table.addCell("Jednostka");
                table.addCell("Cena netto[zl]");
                table.addCell("Stawka VAT[%]");
                table.addCell("Cena brutto[zl]");
             
            JSONObject jsonObject = null;
           for (int i = 0; i < jsonArray.length(); i++) {
        	   jsonObject = jsonArray.getJSONObject(i);
        	   table.addCell("" + (i+1));
        	   table.addCell(jsonObject.getString("nazwa"));
        	   table.addCell(jsonObject.getString("ilosc"));
        	   table.addCell(jsonObject.getString("jednostka"));
        	   Double netto = Double.valueOf(Double.valueOf(jsonObject.getString("cnetto"))  * Double.valueOf(jsonObject.getString("ilosc")));
        	   table.addCell(df.format(netto));
        	   table.addCell(jsonObject.getString("stawka_vat"));
        	   Double brutto = Double.valueOf(Double.valueOf(jsonObject.getString("cbrutto"))  * Double.valueOf(jsonObject.getString("ilosc")));
        	   table.addCell(df.format(brutto));
           }
               
            return table;
        }
}