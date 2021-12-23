package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repo;
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event obj = repo.getOne(id);
			copyDtoToEntity(dto, obj);
			
			repo.save(obj);
			return new EventDTO(obj);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}

	private void copyDtoToEntity(EventDTO dto, Event obj) {
		obj.setName(dto.getName());
		obj.setDate(dto.getDate());
		obj.setUrl(dto.getUrl());		
		obj.setCity(new City(dto.getCityId(), null));		
	}
}
