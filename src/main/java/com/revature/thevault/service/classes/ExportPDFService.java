package com.revature.thevault.service.classes;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.revature.thevault.repository.dao.AccountProfileRepository;
import com.revature.thevault.repository.entity.AccountProfileEntity;
import com.revature.thevault.service.dto.TransactionObject;

@Service("exportPDFService")
public class ExportPDFService {

	@Autowired
	private RandomStringService strServ;

	@Autowired
	private EmailService emailServ;

	@Autowired
	private AccountProfileRepository profileRepos;

	public void createPDF(List<TransactionObject> transactionObjects, int month, int year, int profileId, float ourCurrentBalance)
			throws FileNotFoundException, MalformedURLException {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		Date startDate = new Date(cal.getTimeInMillis());
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		Date endDate = new Date(cal.getTimeInMillis());
		String dateRange = startDate.toString() + " to " + endDate.toString();

		// Document Creation
		String randPath = strServ.getSaltString();
		String dest = "src/main/resources/pdf/" + randPath + "myMonthStatement.pdf";
		PdfWriter writer = new PdfWriter(dest); // Writer can create PDF files
		PdfDocument pdfFile = new PdfDocument(writer); // Point of entry to work with a PDF file.
		Document document = new Document(pdfFile); // Desired output is a Document Object. The actual PDF file

		try {

			// Variables
			final String FUTURA = "src/test/java/com/revature/thevault/exportpdf/Futura-Std-Medium.otf";
			final String FUTURABOLD = "src/test/java/com/revature/thevault/exportpdf/Futura-Std-Book.otf";
			final String FUTURABOOK = "src/test/java/com/revature/thevault/exportpdf/Futura-Std-Bold.otf";
			
			Optional<AccountProfileEntity> profileInfo = profileRepos.findById(profileId);

			// Get the profile information
			String userEmail = "";
			String firstName = "";
			String lastName = "";
			String address = "";
			if (!profileInfo.isEmpty()) {
				AccountProfileEntity profileInfoObj = profileInfo.get();
				userEmail = profileInfoObj.getEmail();
				firstName = profileInfoObj.getFirst_name();
				lastName = profileInfoObj.getLast_name();
				address = profileInfoObj.getAddress();
			} else {
				System.out.println("ERROR: No profile found!");
			}

			boolean isEmptyList = false;

			// Font Reference Creation
			FontProgram medFontProgram = FontProgramFactory.createFont(FUTURA);
			FontProgram boldFontProgram = FontProgramFactory.createFont(FUTURABOLD);
			FontProgram bookFontProgram = FontProgramFactory.createFont(FUTURABOOK);

			// Font Object Initialization
			PdfFont futuraMed = PdfFontFactory.createFont(medFontProgram);
			PdfFont futuraBold = PdfFontFactory.createFont(boldFontProgram);
			PdfFont futuraBook = PdfFontFactory.createFont(bookFontProgram);

			// Paragraph Initialization
			Paragraph accInfo = new Paragraph(firstName + " " + lastName + "\n" + address); // This is assuming all
																							// three of these are
																							// mandatory. Double
																							// check... // This needs to
																							// be changed.
			Paragraph revatureInfo = new Paragraph(
					"1111 Constitution Avenue Northwest\nWashington, District of Columbia, DC");
			Paragraph emptyTransactions = new Paragraph("Transaction list is empty. No records to show.");

			// Image Initialization
			Image logo = new Image(
					ImageDataFactory.create("src/test/java/com/revature/thevault/exportpdf/rev-logo.png"));

			// Colors
			Color orange = new DeviceRgb(242, 105, 38);
			Color blue = new DeviceRgb(115, 165, 194);
			Color yellow = new DeviceRgb(253, 181, 21);
			Color lightGray = new DeviceRgb(185, 185, 186);

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

			Style tableStyle = new Style();
			tableStyle.setBackgroundColor(blue);

			Style tableCellStyle = new Style();
			tableCellStyle.setBackgroundColor(lightGray);
			tableCellStyle.setBorder(new SolidBorder(lightGray, 1F));

			// Table Creation
			Table table = new Table(new float[] { 1F, 0.5F, 1F, 1F, 2F }, false); // In this float example, the float
																					// numbers represent table size. A
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

			if (transactionObjects.size() < 1) {
				isEmptyList = true;
			} else {

				for (TransactionObject t : transactionObjects) { // Dynamically creates the table based on the size of
																	// arrList

					table.addCell(t.getDate().toString());
					table.addCell(t.getTransactionReference());
					if (t.getTransactionType().equals("Withdraw")) {
						table.addCell(String.valueOf(t.getAmount()));
						table.addCell("");
						ourCurrentBalance = ourCurrentBalance - t.getAmount();
					} else {
						table.addCell("");
						table.addCell(String.valueOf(t.getAmount()));
						ourCurrentBalance = ourCurrentBalance + t.getAmount();
					}
					table.addCell(String.valueOf(ourCurrentBalance));
					table.addStyle(tableCellStyle);
				}

				// Style Appending
				logo.addStyle(myLogo);
				revatureInfo.addStyle(myRevatureStyle);
				accInfo.addStyle(myAccountInfoStyle);
				emptyTransactions.addStyle(myRevatureStyle);

				// Appending to Document
				document.add(logo); // All Images Read from Root folder.
				document.add(revatureInfo);
				document.add(accInfo);
				document.add(table);
				if (isEmptyList)
					document.add(emptyTransactions);

				document.close();
				System.out.println("A PDF File has been created at location " + dest);

				// Auto-Opening the file
//				File file = new File(dest); // Auto Opens for now....
//				Desktop desktop = Desktop.getDesktop();
//				desktop.open(file); // Since the value is hard-coded in, we don't need to check whether or not the file exists because it WILL ALWAYS create it.

				if (!profileInfo.isEmpty()) {
					// Email the file (by fred)
					emailServ.sendReportPdfEmail(dest, dateRange, userEmail, firstName);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("The program has failed. An exception has occured.");
		}
	}
}
