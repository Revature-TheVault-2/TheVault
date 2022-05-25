package com.revature.thevault.presentation.controller;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.layout.Document;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.service.classes.ExportPDFService;
import com.revature.thevault.service.dto.TransactionObject;
import com.revature.thevault.service.exceptions.InvalidAuthorizationError;
import com.revature.thevault.utility.JWTInfo;
import com.revature.thevault.utility.JWTUtility;

@CrossOrigin("*") // This needs to be eventually changed.
@RestController("exportPDFController")
@RequestMapping("/pdf")
public class ExportPDFController {
	
	@Autowired
	private static ExportPDFService PDFService;
	
	/**
	 * Requires a GetResponse parameter. The getTransactionHistory() method from the TransactionController would go here for example.
	 * 
	 * Will return a PDF File with a pre-set structure.
	 * <br>
	 * <img src="../../../../../../resources/img/cat.png" label="Example">
	 * Hello.
	 * Hello.
	 * Hello.
	 * 
	 * @param transactionHistory
	 * @return Document (PDF File)
	 * @throws MalformedURLException 
	 * @throws FileNotFoundException 
	 */
//	public static Document createPDF(List<TransactionObject> transactionObjects) throws FileNotFoundException, MalformedURLException {
//		// Maybe there doesn't need to be a reason for two authentications. Just have the first one from the TransactionService layer and we are good to go.
//		
//		return ExportPDFService.createPDF(transactionObjects);
//	}
	
	/* void exportPDF(){
	/*	
	 *  }
	 */
	
}
