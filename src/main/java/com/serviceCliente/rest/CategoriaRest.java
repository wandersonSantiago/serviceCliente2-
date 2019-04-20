package com.serviceCliente.rest;

import java.net.URI;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.serviceCliente.model.Categoria;
import com.serviceCliente.services.CategoriaService;

@RestController
@RequestMapping("/rest/categorias")
public class CategoriaRest {
	
	@Autowired
	private CategoriaService categoriaService;
	
	 @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	 @GetMapping
	  public ResponseEntity<List<Categoria>> categorias(HttpServletRequest request, HttpServletResponse response) {
	    return  ResponseEntity.ok().body(categoriaService.categorias());
	  }
	 
	 @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	 @GetMapping(value="/{id}")
	 public ResponseEntity<Categoria> findId(@PathVariable Integer id){
		 return ResponseEntity.ok().body(categoriaService.findId(id));
	 }

	 @PostMapping
	 public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		 obj = categoriaService.insert(obj);
		 URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		 return ResponseEntity.created(uri).build();
	 }
}
