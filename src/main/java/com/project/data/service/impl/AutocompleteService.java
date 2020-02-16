package com.project.data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.api.data.enums.Language;
import com.project.data.elastic.entity.Suggestion;
import com.project.data.elastic.repository.AutocompleteRepository;
import com.project.data.service.IAutocompleteService;

@Service
public class AutocompleteService implements IAutocompleteService {

	@Autowired
	AutocompleteRepository autocompleteRepository;
	
	@Override
	public List<Suggestion> search(String query, Language language) {
//		return autocompleteRepository.findByLabelContainingIgnoreCaseAndLanguage(query, language);
		return autocompleteRepository.findByLocalisationsNameContainingIgnoreCase(query);
	}

	@Override
	public String save(Suggestion suggestion) {
		return autocompleteRepository.save(suggestion).getId();
	}

}
