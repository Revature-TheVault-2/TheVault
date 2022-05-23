package com.revature.thevault.exportpdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.text.StyleConstants.FontConstants;

import com.itextpdf.forms.fields.borders.FormBorderFactory;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;
import com.revature.thevault.repository.entity.DepositEntity;
import com.revature.thevault.service.dto.TransactionObject;

import java.awt.Desktop;


public class ExportPDFTests {
	
	// Maybe make Helper Methods to incorporate all 3 into 1? So no giant blocks of code.

	public static void main(String[] args) {
		// createTestOne();
		createTestTwo();
	}

	public static void createTestOne() {
		try{
			String dest = "src/test/java/com/revature/thevault/exportpdf/myTestOne.pdf";
			PdfWriter writer = new PdfWriter(dest); // Writer can create PDF files
			PdfDocument pdfFile = new PdfDocument(writer); //Point of entry to work with a PDF file.
			Document document = new Document(pdfFile); // Desired output is a Document Object. The actual PDF file
			
			//Variables
			final String FUTURA = "src/test/java/com/revature/thevault/exportpdf/Futura-Std-Medium.otf";
			final String FUTURABOLD = "src/test/java/com/revature/thevault/exportpdf/Futura-Std-Book.otf";
			final String FUTURABOOK = "src/test/java/com/revature/thevault/exportpdf/Futura-Std-Bold.otf";
			
			//Font Reference Creation
			FontProgram medFontProgram = FontProgramFactory.createFont(FUTURA);
			FontProgram boldFontProgram = FontProgramFactory.createFont(FUTURABOLD);
			FontProgram bookFontProgram = FontProgramFactory.createFont(FUTURABOOK);
			
			
			//Font Object Initialization
			PdfFont futuraMed = PdfFontFactory.createFont(medFontProgram);
			PdfFont futuraBold = PdfFontFactory.createFont(boldFontProgram);
			PdfFont futuraBook = PdfFontFactory.createFont(bookFontProgram);
			
			//Font Adding
			document.add(new Paragraph("Hello Za Warudo - Futura-Med").setFont(futuraMed));
			document.add(new Paragraph("Gravity is a JoJo reference. - Futura-Bold").setFont(futuraBold));
			document.add(new Paragraph("It was me, Dio! - Futura-Book").setFont(futuraBook));
			
			
			//Image Initialization
			Image wow = new Image(ImageDataFactory.create("src/test/java/com/revature/thevault/exportpdf/wow.jpg"));
			Style myStyle = new Style();
			myStyle.setWidth(400F);
			
			wow.addStyle(myStyle);
			
			//Image Testing
			document.add(new Image(ImageDataFactory.create("src/test/java/com/revature/thevault/exportpdf/rev-logo.png"))); // All Images Read from Root folder.
			document.add(new Image(ImageDataFactory.create("src/test/java/com/revature/thevault/exportpdf/cat.png")));
			document.add(wow);
			//Table Testing
			
			document.close();
			System.out.println("A PDF File has been created at location " + dest);
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("The program has failed. An exception has occured.");
			}
	}
		
	public static void createTestTwo() {
		try{ // I should work on eventually reducing total number of lines.
			

			//Objects
			// Okay so... just like the transaction model is doing. You need to parse the info coming and transform that into a TransactionObject list.
			// For now, just focus on making a few fake objects and going from there.
			TransactionObject fakeTransaction = new TransactionObject(1, "Deposit", "Cash", "A1B", LocalDate.now(), 1_234_567F); // Why is amount a Float though? Why???
			TransactionObject fakeTransaction2 = new TransactionObject(2, "Withdrawal", "Payment", "Something", LocalDate.now(), 200F); // Why is amount a Float though? Why???
			TransactionObject fakeTransaction3 = new TransactionObject(3, "Withdrawal", "Payment", "CCC", LocalDate.now(), 500_000F); // Why is amount a Float though? Why???
			
			
			//Variables
			final String FUTURA = "src/test/java/com/revature/thevault/exportpdf/Futura-Std-Medium.otf";
			final String FUTURABOLD = "src/test/java/com/revature/thevault/exportpdf/Futura-Std-Book.otf";
			final String FUTURABOOK = "src/test/java/com/revature/thevault/exportpdf/Futura-Std-Bold.otf";
			
			
			String firstName = "Ernest";
			String lastName = "Hemingway";
			String address = "339 N Oak Park Ave, Oak Park, IL 60302";
			
			boolean isEmptyList = false;
			
			
			ArrayList<TransactionObject> arrList = new ArrayList<TransactionObject>(); // This needs to be a transaction input eventually.
			arrList.add(fakeTransaction);
			arrList.add(fakeTransaction2);
			arrList.add(fakeTransaction3);
			arrList.add(fakeTransaction3);
			
			
			
			//Font Reference Creation
			FontProgram medFontProgram = FontProgramFactory.createFont(FUTURA);
			FontProgram boldFontProgram = FontProgramFactory.createFont(FUTURABOLD);
			FontProgram bookFontProgram = FontProgramFactory.createFont(FUTURABOOK);
			
			//Font Object Initialization
			PdfFont futuraMed = PdfFontFactory.createFont(medFontProgram);
			PdfFont futuraBold = PdfFontFactory.createFont(boldFontProgram);
			PdfFont futuraBook = PdfFontFactory.createFont(bookFontProgram);
			
			//Paragraph Initialization
			Paragraph accInfo = new Paragraph(firstName + " " + lastName + "\n" + address); // This is assuming all three of these are mandatory. Double check...
			Paragraph revatureInfo = new Paragraph("1111 Constitution Avenue Northwest\nWashington, District of Columbia, DC");
			Paragraph emptyTransactions = new Paragraph("Transaction list is empty. No records to show.");
			
			//Image Initialization
			Image logo = new Image(ImageDataFactory.create("src/test/java/com/revature/thevault/exportpdf/rev-logo.png"));

			// Border Creation
			
//			FormBorderFactory mainBorder = new FormBorderFactory(new PdfDictionary("dashedBorder"), 5F, new Color(Red), new Color(Blue));
			
			// Style Initialization
			Style myLogo = new Style();
			myLogo.setWidth(165F);
			myLogo.setHeight(55F);
			myLogo.setMarginLeft(2F);

			Style myRevatureStyle = new Style();
			myRevatureStyle.setFont(futuraBold);
			myRevatureStyle.setFontSize(10F);
			
			Style myAccountInfoStyle = new Style();
			myAccountInfoStyle.setFont(futuraBook);
			myAccountInfoStyle.setFontSize(9F);
			myAccountInfoStyle.setMarginLeft(25F);
			
			Color orange = new DeviceRgb(242, 105, 38);
			Color blue = new DeviceRgb(115, 165, 194);
			Color yellow = new DeviceRgb(253, 181, 21);
			Style tableStyle = new Style();
//			tableStyle.setBorder(new SolidBorder(ColorConstants.WHITE, 1F));
//			tableStyle.setBorder(Border.NO_BORDER);
			tableStyle.setBackgroundColor(orange);
			
			Style tableCellStyle = new Style();
			tableCellStyle.setBackgroundColor(blue);
			tableCellStyle.setBorder(new SolidBorder(yellow, 1F));
			
			//Table Creation			
			Table table = new Table(new float[]{1F, 0.5F, 1F, 1F, 2F}, false); // In this float example, the float numbers represent table size. A
			table.setWidth(UnitValue.createPercentValue(100));
			table.addStyle(tableCellStyle);
	
			
			Cell Date = new Cell();
			Date.add(new Paragraph("Date"));	
			Date.addStyle(tableStyle);
			
			Cell Ref = new Cell();
			Ref.add(new Paragraph("Ref"));
			Ref.addStyle(tableStyle);
			
			Cell Withdrawals = new Cell();
			Withdrawals.add(new Paragraph("Withdrawals"));
			Withdrawals.addStyle(tableStyle);
			
			Cell Deposits = new Cell();
			Deposits.add(new Paragraph("Deposits"));
			Deposits.addStyle(tableStyle);
			
			Cell Balance = new Cell();
			Balance.add(new Paragraph("Balance"));
			Balance.addStyle(tableStyle);			
			
			table.addHeaderCell(Date);
			table.addHeaderCell(Ref);
			table.addHeaderCell(Withdrawals);
			table.addHeaderCell(Deposits);
			table.addHeaderCell(Balance);
			table.setFont(futuraBold);
			
			if(arrList.size() < 1) {
				isEmptyList = true;
			}
			else
				
				for(TransactionObject t : arrList) { // Dynamically creates the table based on the size of arrList
					
					table.addCell(t.getDate().toString());
					table.addCell(t.getTransactionReference());
					if (t.getTransactionType().equals("Withdrawal")) { 
						table.addCell(String.valueOf(t.getAmount()));
						table.addCell("");
					}
					else {
						table.addCell("");
						table.addCell(String.valueOf(t.getAmount()));
					}
					table.addCell("Not implemented yet.");
					table.addStyle(tableCellStyle);
				}
			
			//Style Appending
			logo.addStyle(myLogo);
			revatureInfo.addStyle(myRevatureStyle);
			accInfo.addStyle(myAccountInfoStyle);
			emptyTransactions.addStyle(myRevatureStyle);

			//Document Creation
			String dest = "src/test/java/com/revature/thevault/exportpdf/myTestTwo.pdf";
			PdfWriter writer = new PdfWriter(dest); // Writer can create PDF files
			PdfDocument pdfFile = new PdfDocument(writer); //Point of entry to work with a PDF file.
			Document document = new Document(pdfFile); // Desired output is a Document Object. The actual PDF file

			//Appending to Document
			document.add(logo); // All Images Read from Root folder.
			document.add(revatureInfo);
			document.add(accInfo);
			document.add(table);
			if (isEmptyList)
				document.add(emptyTransactions);
			
			document.close();
			System.out.println("A PDF File has been created at location " + dest);
			
			//Auto-Opening the file
			File file = new File("src/test/java/com/revature/thevault/exportpdf/myTestTwo.pdf");
			Desktop desktop = Desktop.getDesktop();  
			desktop.open(file); // Since the value is hard-coded in, we don't need to check whether or not the file exists because it WILL ALWAYS create it.
			  
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("The program has failed. An exception has occured.");
			}
	}

}
