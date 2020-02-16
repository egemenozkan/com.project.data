package com.project.data.service;

import java.util.List;

import com.project.api.data.enums.Language;
import com.project.data.elastic.entity.Suggestion;

public interface IAutocompleteService {
	List<Suggestion> search(String query, Language language);
	String save(Suggestion suggestion);
}
