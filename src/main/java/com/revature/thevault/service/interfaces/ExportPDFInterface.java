package com.revature.thevault.service.interfaces;

import java.util.ArrayList;

import com.itextpdf.layout.Document;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.service.dto.TransactionObject;

public interface ExportPDFInterface {
	void exportPDF(Document pdfFile);
	Document createPDF(ArrayList<TransactionObject> arrList);
}
