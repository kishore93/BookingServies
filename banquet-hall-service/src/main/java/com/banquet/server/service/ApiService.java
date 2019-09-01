package com.banquet.server.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.banquet.server.pojo.BanquetHallEntity;
import com.banquet.server.repository.ApiRepository;

@Service
public class ApiService {

	ApiRepository repository;

	@Autowired
	public ApiService(ApiRepository repository) {
		this.repository = repository;
	}

	public void saveBanquetHall(BanquetHallEntity entity) {

		repository.save(entity);
	}

	public List<BanquetHallEntity> getBanquetHalls() {
		
		return repository.findAll();
	}

	public BanquetHallEntity getBanquetHall(String banquetHallId) {
		
		Optional<BanquetHallEntity> optionalEntity = repository.findById(banquetHallId);
		
		if(optionalEntity.isPresent()) {
			return optionalEntity.get();
		}
		
		return null;
	}

	public List<BanquetHallEntity> getBanquetHallByPageNo(int pageNo) {
		
		Page<BanquetHallEntity> results =  repository.findAll(PageRequest.of(pageNo, 1));// Can add Sorting of results in future based on name or distance
		return results.get().collect(Collectors.toList());
	}

	public void updateBanquetHalls(List<BanquetHallEntity> entities) {

		repository.saveAll(entities);
	}

	public void updateBanquetHall(String banquetHallId, BanquetHallEntity entity) {
		
		Optional<BanquetHallEntity> optionalEntity = repository.findById(banquetHallId);		
		
		if(optionalEntity.isPresent()) {
			entity.setId(optionalEntity.get().getId());
			repository.save(entity);
		}
	}

	public void deleteBanquetHalls(List<BanquetHallEntity> entities) {
		
		repository.deleteAll(entities);
	}

	public void deleteBanquetHall(String banquetHallId, BanquetHallEntity entity) {

		Optional<BanquetHallEntity> optionalEntity = repository.findById(banquetHallId);		
		
		if(optionalEntity.isPresent()) {
			entity.setId(optionalEntity.get().getId());
			repository.delete(entity);
		}
	}

}
