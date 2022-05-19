package com.revature.thevault.service.classes;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.revature.thevault.presentation.model.response.builder.GetResponse;

@Service("exportPDFService")

public class ExportPDFService {
	public Document createPDF(GetResponse transactionHistory) throws FileNotFoundException, MalformedURLException {
	String dest = "myTest.pdf";
	PdfWriter writer = new PdfWriter(dest); // Writer can create PDF files
	PdfDocument pdfFile = new PdfDocument(writer);
	Document document = new Document(pdfFile);
	// Business Logic goes here
//	document.add(new Paragraph("Hello Za Warudo"));
//	document.add(new Image(ImageDataFactory.create("../../../../../../resources/img/cat.png")));
//	document.close();
//	
	return document;
	}
}
