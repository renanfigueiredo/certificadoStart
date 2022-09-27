//package com.api.whatsapp.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.api.whatsapp.model.User;
//import com.api.whatsapp.service.UserService;
//
//@RestController
//@RequestMapping("v1/whatsapp")
//public class UserController {
//
//	@Autowired
//	UserService userService;
//	
//	@PostMapping()
//	public ResponseEntity<?> save(@RequestBody User user){
//		return ResponseEntity.ok(userService.save(user));
//	}
//	
//	@GetMapping()
//	public ResponseEntity<?> list(){
//		return ResponseEntity.ok(userService.list());
//	}
//	
//	@GetMapping("/name")
//	public ResponseEntity<?> findByName(String name){
//		return ResponseEntity.ok(userService.findByName(name));
//	}
//	
//	@PutMapping()
//	public ResponseEntity<?> edit(@RequestBody User user){
//		return ResponseEntity.ok(userService.edit(user));
//	}
//	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> delete(@PathVariable String id){
//		return ResponseEntity.ok(userService.delete(id));
//	}	
//		 
//}
