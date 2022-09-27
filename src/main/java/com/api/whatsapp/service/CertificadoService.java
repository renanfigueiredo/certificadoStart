package com.api.whatsapp.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.api.whatsapp.model.Aluno;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Service
public class CertificadoService {

	public List<Aluno> importarPlanilha() {

		List<Aluno> alunos = new ArrayList<>();

		try {
			File file = new File("src/planilhas/start_formados_2022_2_completo.csv");
			FileReader filereader = new FileReader(file);
			CSVReader csvReader = new CSVReaderBuilder(filereader).build();
			List<String[]> allData = csvReader.readAll();

			int linha = 1;
			for (String[] row : allData) {
				try {
					Aluno aluno = new Aluno();
					aluno.setMatricula(1);
					aluno.setNome(row[0]);
					aluno.setEmail(row[1]);
					aluno.setTurma("ABRIL 2022");
					alunos.add(aluno);
					
					String html = stringHTML(aluno);
					sendEmail(aluno, html);
					linha++;
				} catch (Exception e) {
					System.out.println(linha + " - " +e.getMessage());
					linha++;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return alunos;
	}

	public File criarHtml(Aluno aluno) {

		String texto = aluno.getNome();
		String nomeArquivo = "src/certificados/" + aluno.getMatricula() + ".html";
		String turma = aluno.getTurma();

		String html = "<html> <head> <style>body{width: 1457px; height: 1017px; background-image: url('certificado_start.png'); background-repeat: no-repeat;}#nome{margin-top: 37.5%; margin-left: 30%; font-family: Helvetica, sans-serif; font-size:40px; font-weight: 600;}#turma{margin-top: 10%; margin-left: 45%; font-family: Helvetica, sans-serif; font-size:40px; font-weight: 600;}</style> </head> <body> <p id=\"nome\">${texto}</p><p id=\"turma\">${turma}</p></body></html>";

		html = html.replace("${texto}", texto);
		html = html.replace("${turma}", turma);

		try {
			File file = new File(nomeArquivo);
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte[] bytes = html.getBytes();
			// write byte array to file
			bos.write(bytes);
			bos.close();
			fos.close();

			return file;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public File criarPDF(Aluno aluno) {

		String texto = aluno.getNome();
		String output = "src/certificados/" + aluno.getMatricula() + ".pdf";
		String turma = aluno.getTurma();

		String html = "<html> <head> <style>#nome{margin-top: 25%; margin-left: 30%; font-family: Helvetica, sans-serif; font-size:18px; font-weight: 600;}#turma{margin-top: 10%; margin-left: 45%; font-family: Helvetica, sans-serif; font-size: 18px; font-weight: 600;}</style> </head> <body> <table background=\"https://i.ibb.co/xFtVzfT/certificado-start.png\"; background-repeat: no-repeat; width=\"640\" height=\"450\"> <tr> <td> <p id=\"nome\">${texto}</p><p id=\"turma\">${turma}</p></td></tr></table> </body></html>";

		html = html.replace("${texto}", texto);
		html = html.replace("${turma}", turma);

		
		
		return generateHtmlToPdf("src/certificados/renan.html", output);

	}
	
	public String stringHTML(Aluno aluno) {

		String texto = aluno.getNome();
 		String turma = aluno.getTurma();

		String html = "<html> <head> <style>#nome{margin-top: 25%; margin-left: 30%; font-family: Helvetica, sans-serif; font-size:18px; font-weight: 600;}#turma{margin-top: 10%; margin-left: 45%; font-family: Helvetica, sans-serif; font-size: 18px; font-weight: 600;}</style> </head> <body> <table background=\"https://i.ibb.co/xFtVzfT/certificado-start.png\"; background-repeat: no-repeat; width=\"640\" height=\"450\"> <tr> <td> <p id=\"nome\">${texto}</p><p id=\"turma\">${turma}</p></td></tr></table> </body></html>";

		html = html.replace("${texto}", texto);
		html = html.replace("${turma}", turma);

		
		
		return html;

	}

	private File generateHtmlToPdf(String html, String output) {
		try {
			File inputHTML = new File(html);
			Document doc;

			doc = createWellFormedHtml(inputHTML);
			xhtmlToPdf(doc, output);

			return inputHTML;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Document createWellFormedHtml(File inputHTML) throws IOException {
		Document document = Jsoup.parse(inputHTML, "UTF-8");
		document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
		return document;
	}

	private void xhtmlToPdf(Document doc, String outputPdf) throws IOException {
		try (OutputStream os = new FileOutputStream(outputPdf)) {
			String baseUri = FileSystems.getDefault().getPath("src/main/resources/").toUri().toString();
			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.withUri(outputPdf);
			builder.toStream(os);
			builder.withW3cDocument(new W3CDom().fromJsoup(doc), baseUri);
			builder.run();
		}
	}
	
	public void sendEmail(Aluno aluno, String html) {
		String meuEmail = "renan.figueiredo.05@gmail.com";
		String senha = "Rzw@0512234";
		
		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator(meuEmail, senha));
		email.setSSLOnConnect(true);
		
		try {
			email.setFrom(meuEmail);
			email.setSubject("CERTIFICADO START - "+ aluno.getNome());
			
			
			email.setHtmlMsg(html);
			
//			List<InternetAddress> to = new ArrayList<>();
//			InternetAddress internetAddress = new InternetAddress();
//			internetAddress.setAddress(aluno.getEmail());
//			to.add(internetAddress);
			email.addTo(aluno.getEmail());
			email.send();
			System.out.println("Email enviado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
