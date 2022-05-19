package com.revature.thevault.exportpdf;

import java.io.FileNotFoundException;

import javax.swing.text.StyleConstants.FontConstants;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.*;

public class ExportPDFTests {
	
	// Maybe make Helper Methods to incorporate all 3 into 1? So no giant blocks of code.

	public static void main(String[] args) {
	try{
		String dest = "src/test/java/com/revature/thevault/exportpdf/myPDF.pdf";
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
		
		//Font Testing
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

}
