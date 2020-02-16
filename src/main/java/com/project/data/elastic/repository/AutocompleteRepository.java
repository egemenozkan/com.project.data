package com.project.data.elastic.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.project.api.data.enums.Language;
import com.project.data.elastic.entity.Suggestion;

public interface AutocompleteRepository extends ElasticsearchRepository<Suggestion, String> {
	List<Suggestion> findByLabelContainingIgnoreCaseAndLanguage(String label, Language language);	
	List<Suggestion> findByLocalisationsNameContainingIgnoreCase(String query);
}
