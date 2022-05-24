package com.revature.thevault.service.interfaces;

import com.itextpdf.layout.Document;
import com.revature.thevault.presentation.model.response.builder.GetResponse;

public interface ExportPDFInterface {
	void exportPDF(Document pdfFile);
	Document createPDF(GetResponse transactionHistory);
}
