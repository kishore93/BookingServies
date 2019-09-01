package com.banquet.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banquet.server.pojo.BanquetHallEntity;
import com.banquet.server.service.ApiService;

@Controller
@CrossOrigin
@RequestMapping("/api/v1/banquet-service")
public class ApiController {

	ApiService service;

	@Autowired
	public ApiController(ApiService service) {
		this.service = service;
	}

	@GetMapping("/")
	public String echo() {
		return "Hell No!";
	}
	
	@PostMapping("/banquet")
	public void saveBanquetHall(@RequestBody BanquetHallEntity entity, HttpServletResponse response) {

		service.saveBanquetHall(entity);
		response.setStatus(HttpStatus.OK.value());
	}

	@GetMapping("/banquet")
	public ResponseEntity<List<BanquetHallEntity>> getBanquetHalls(HttpServletResponse response) {

		return new ResponseEntity<List<BanquetHallEntity>>(service.getBanquetHalls(), HttpStatus.OK);
	}

	@GetMapping("/banquet/{banquetHallId}")
	public ResponseEntity<BanquetHallEntity> getBanquetHalls(@PathVariable(value = "banquetHallId") String banquetHallId,
			HttpServletResponse response) {

		BanquetHallEntity entity = service.getBanquetHall(banquetHallId);

		if (entity != null) {
			return new ResponseEntity<BanquetHallEntity>(entity, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/banquet/page/{pageNo}")
	public ResponseEntity<List<BanquetHallEntity>> getBanquetHallsByPage(@PathVariable(value = "pageNo") @Range(min = 1)int pageNo,
			HttpServletResponse response) {

		return new ResponseEntity<List<BanquetHallEntity>>(service.getBanquetHallByPageNo(pageNo-1), HttpStatus.OK);
	}

	@PutMapping("/banquet")
	public void updateBanquetHalls(@RequestBody List<BanquetHallEntity> entities, HttpServletResponse response) {

		response.setStatus(HttpStatus.OK.value());
		service.updateBanquetHalls(entities);
	}

	@PutMapping("/banquet/{banquetHallId}")
	public void updateBanquetHall(@PathVariable(value = "banquetHallId") String banquetHallId,
			@RequestBody BanquetHallEntity entity, HttpServletResponse response) {

		response.setStatus(HttpStatus.OK.value());
		service.updateBanquetHall(banquetHallId, entity);
	}
	
	@DeleteMapping("/banquet")
	public void deleteBanquetHalls(@RequestBody List<BanquetHallEntity> entities, HttpServletResponse response) {

		response.setStatus(HttpStatus.OK.value());
		service.deleteBanquetHalls(entities);
	}
	
	@DeleteMapping("/banquet/{banquetHallId}")
	public void deleteBanquetHall(@PathVariable(value = "banquetHallId") String banquetHallId,
			@RequestBody BanquetHallEntity entity, HttpServletResponse response) {

		response.setStatus(HttpStatus.OK.value());
		service.deleteBanquetHall(banquetHallId, entity);
	}
}
