package com.centraprise.hrmodule.service;

import java.io.File;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import com.centraprise.hrmodule.entity.AssignmentInformation;
import com.centraprise.hrmodule.entity.BankInformation;
import com.centraprise.hrmodule.entity.EmployeeDetails;
import com.centraprise.hrmodule.entity.ManageLeave;
import com.centraprise.hrmodule.entity.MonthLeave;
import com.centraprise.hrmodule.entity.ProvidentFundInformation;
import com.centraprise.hrmodule.entity.SaloryInfo;
import com.centraprise.hrmodule.exception.DatabaseException;
import com.centraprise.hrmodule.helper.DateParser;
import com.centraprise.hrmodule.model.SalarySlipDTO;
import com.centraprise.hrmodule.repository.EmployeeRepository;
import com.centraprise.hrmodule.repository.LeaveManagementRepository;
import com.centraprise.hrmodule.repository.SalaryRepository;

@Service
public class SalarySlipGenerationServiceImpl implements SalarySlipGenerationService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private SalaryRepository salaryRepository;

	@Autowired
	private LeaveManagementRepository leaveManagementRepository;

	@Autowired
	private DateParser dateParser;

	@Override
	public void generatePDF() {

		try {

			List<EmployeeDetails> employeeDetails = employeeRepository.findAll();

			String filePath = "src//main//resources//";
			if (employeeDetails != null && employeeDetails.size() > 0) {
				go: {
					for (EmployeeDetails employeeDe : employeeDetails) {
						Date today = new Date();
						Calendar cal = Calendar.getInstance();
						cal.setTime(today);
						int month = cal.get(Calendar.MONTH);
						int year = cal.get(Calendar.YEAR);

						SaloryInfo salatyDetails = salaryRepository
								.findByEmployeeNumberAndYearAndMonth(employeeDe.getEmployeeNumber(), year, month);

						ManageLeave manageLeave = leaveManagementRepository
								.findByEmployeeNumberAndYear(employeeDe.getEmployeeNumber(), year);
						int lossOfPayDays = 0;
						int usedLeaves = 0;
						int creditedLeaves = 22;
						if (manageLeave != null) {
							for (MonthLeave leave : manageLeave.getMonthLeavesInfo()) {
								usedLeaves = usedLeaves + leave.getNumberOfDaysLeave();
							}

							if (creditedLeaves < usedLeaves) {
								lossOfPayDays = usedLeaves - creditedLeaves;
							} else {
								lossOfPayDays = 0;
							}

						} else {
							lossOfPayDays = 0;
						}

						if (salatyDetails == null) {
							break go;
						} else {
							SalarySlipDTO salarySlipDTO = new SalarySlipDTO();
							salarySlipDTO.setEmployeeName(employeeDe.getEmployeeName());
							salarySlipDTO.setEmployeeNumber(employeeDe.getEmployeeNumber());
							for (AssignmentInformation assignmentInformation : employeeDe.getAssignmentInfo()) {
								salarySlipDTO.setDateOfJoining(
										dateParser.parseDateToString(assignmentInformation.getDateOfJoining()));
								salarySlipDTO.setDesignation(assignmentInformation.getJob());
							}
							salarySlipDTO.setLocation("Hyderabad");

							for (BankInformation bankInfo : employeeDe.getBankInfo()) {
								salarySlipDTO.setAccountNumber(bankInfo.getAccountNumber());
								salarySlipDTO.setBankName(bankInfo.getBankName());
							}

							for (ProvidentFundInformation providentFundInformation : employeeDe.getProvidentInfo()) {
								salarySlipDTO.setUanNumber(providentFundInformation.getUanNumber());
							}
							salarySlipDTO.setPanNumber(employeeDe.getPanNumber());
							salarySlipDTO.setGender(employeeDe.getSex());
							int daysWorked = 0;
							if (lossOfPayDays != 0) {
								daysWorked = findNumberOfdaysWorked(lossOfPayDays, year, month);
							} else {
								daysWorked = findNumberOfdaysWorked(lossOfPayDays, year, month);
							}
							salarySlipDTO.setNumberOfdaysWorked(daysWorked);
							salarySlipDTO.setPfNumber("0");
							salarySlipDTO.setBasic(salatyDetails.getBasicSalory());
							salarySlipDTO.setBasicArrear(0);
							salarySlipDTO.setBasicGross(salatyDetails.getBasicSalory());
							salarySlipDTO.setSignOnBonusRate(0);
							salarySlipDTO.setSpecialAllowance(salatyDetails.getSpecialAllowance());
							salarySlipDTO.setSpecialAllowanceArrear(0);
							salarySlipDTO.setSpecialAllowanceGross(salatyDetails.getSpecialAllowance() - 0);
							salarySlipDTO.setSignOnBonusCurrentMonth(0);
							salarySlipDTO.setSignOnBonusArrear(0);
							salarySlipDTO.setSignOnBonusGross(0);
							salarySlipDTO.setProfessionTax(salatyDetails.getProfessionalTax());
							float providentFund = salatyDetails.getProvidentFund() / 12;
							salarySlipDTO.setProvidentFund(providentFund);
							salarySlipDTO.setTotalDeductions(
									salatyDetails.getProfessionalTax() + salatyDetails.getProvidentFund());
							salarySlipDTO.setGrossEarningMonth(
									salatyDetails.getBasicSalory() + salatyDetails.getSpecialAllowance());
							salarySlipDTO.setGrossEarningArrear(0);
							salarySlipDTO.setGrossEarningTotal(
									salatyDetails.getBasicSalory() + salatyDetails.getSpecialAllowance());
							salarySlipDTO.setNetPay(salatyDetails.getBasicSalory() + salatyDetails.getSpecialAllowance()
									- salatyDetails.getProfessionalTax() + salatyDetails.getProvidentFund());
							// float totalPay = salatyDetails.getBasicSalory() +
							// salatyDetails.getSpecialAllowance()
							// + salatyDetails.getIncentive() + salatyDetails.getSpecialAllowance();

							// float perDaysalaryAmount = totalPay / daysWorked;
							salarySlipDTO.setLossOfpayDays(lossOfPayDays);

							JAXBContext jaxbContext = JAXBContext.newInstance(SalarySlipDTO.class);
							Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
							jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
							File file = new File(filePath + "payslip.xml");
							jaxbMarshaller.marshal(salarySlipDTO, file);

							File xsltFile = new File(filePath + "template.xsl");
							// the XML file which provides the input
							StreamSource xmlSource = new StreamSource(new File(filePath + "payslip.xml"));
							// create an instance of fop factory
							FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
							// a user agent is needed for transformation
							FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
							// Setup output
							OutputStream out;
							out = new java.io.FileOutputStream(filePath + "output.pdf");
							try {
								Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
								TransformerFactory factory = TransformerFactory.newInstance();
								Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));
								Result res = new SAXResult(fop.getDefaultHandler());
								transformer.transform(xmlSource, res);
							} finally {
								out.close();
							}

							String to = "tech.avinash1995@gmail.com";
							final String user = "avinashnarisetty466gmail.com";
							final String password = "Avinash@45";

							Properties pro = new Properties();
							pro.put("mail.smtp.host", "smtp.gmail.com");
							pro.put("mail.smtp.socketFactory.port", "465");
							pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
							pro.put("mail.smtp.auth", "true");
							pro.put("mail.smtp.port", "465");

							Session session = Session.getDefaultInstance(pro, new javax.mail.Authenticator() {
								protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication(user, password);
								}
							});

							try {
								MimeMessage message = new MimeMessage(session);
								message.setFrom(new InternetAddress(user));
								message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
								message.setSubject("Message Aleart");
								MimeBodyPart messageBodyPart2 = new MimeBodyPart();

								// String filename1 = ClassLoader.getSystemResource("hello.txt").getPath();
								String filename1 = filePath + "output.pdf";
								messageBodyPart2.attachFile(filename1);

								Multipart multipart = new MimeMultipart();
								multipart.addBodyPart(messageBodyPart2);
								message.setContent(multipart);
								Transport.send(message);
								System.out.println("message sent....");
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Database was down");
		}

	}

	public int findNumberOfdaysWorked(int lossOfPaydays, int year, int month) {
		int daysWorked = 0;
		if (year % 4 == 0) {
			switch (month) {
			case 0:
				daysWorked = 31 - lossOfPaydays;
				break;
			case 1:
				daysWorked = 29 - lossOfPaydays;
				break;
			case 2:
				daysWorked = 31 - lossOfPaydays;
				break;

			case 3:
				daysWorked = 30 - lossOfPaydays;
				break;

			case 4:
				daysWorked = 31 - lossOfPaydays;
				break;
			case 5:
				daysWorked = 30 - lossOfPaydays;
				break;

			case 6:
				daysWorked = 31 - lossOfPaydays;
				break;
			case 7:
				daysWorked = 31 - lossOfPaydays;
				break;
			case 8:
				daysWorked = 30 - lossOfPaydays;
				break;
			case 9:
				daysWorked = 31 - lossOfPaydays;
				break;
			case 10:
				daysWorked = 30 - lossOfPaydays;
				break;

			default:
				daysWorked = 31 - lossOfPaydays;
				break;
			}
		} else {
			switch (month) {
			case 0:
				daysWorked = 31 - lossOfPaydays;
				break;
			case 1:
				daysWorked = 28 - lossOfPaydays;
				break;
			case 2:
				daysWorked = 31 - lossOfPaydays;
				break;

			case 3:
				daysWorked = 30 - lossOfPaydays;
				break;

			case 4:
				daysWorked = 31 - lossOfPaydays;
				break;
			case 5:
				daysWorked = 30 - lossOfPaydays;
				break;

			case 6:
				daysWorked = 31 - lossOfPaydays;
				break;
			case 7:
				daysWorked = 31 - lossOfPaydays;
				break;
			case 8:
				daysWorked = 30 - lossOfPaydays;
				break;
			case 9:
				daysWorked = 31 - lossOfPaydays;
				break;
			case 10:
				daysWorked = 30 - lossOfPaydays;
				break;

			default:
				daysWorked = 31 - lossOfPaydays;
				break;
			}
		}

		return daysWorked;
	}

}
