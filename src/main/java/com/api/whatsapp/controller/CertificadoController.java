package com.api.whatsapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.whatsapp.model.Aluno;
import com.api.whatsapp.service.CertificadoService;

@RestController
@RequestMapping("v1/import")
public class CertificadoController {

	@Autowired
	CertificadoService certificadoService;
	
	@PostMapping()
	public ResponseEntity<?> importarPlanilha(){
		return ResponseEntity.ok(certificadoService.importarPlanilha());
	}
	
	@PostMapping("html")
	public ResponseEntity<?> criarHtml(Aluno aluno){
		return ResponseEntity.ok(certificadoService.criarHtml(aluno));
	}
	
	@PostMapping("pdf")
	public ResponseEntity<?> criarPdf(Aluno aluno){
		certificadoService.criarPDF(aluno);
		return ResponseEntity.ok(certificadoService.criarPDF(aluno));
	}
	
	@PostMapping("send-email")
	public ResponseEntity<?> criarEmail(Aluno aluno){
		String html = certificadoService.stringHTML(aluno);
		certificadoService.sendEmail(aluno, html);
		return ResponseEntity.ok("Email enviado com sucesso!");
		
	}
	
}
