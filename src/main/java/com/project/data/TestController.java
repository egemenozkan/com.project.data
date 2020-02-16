package com.project.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.data.enums.Language;
import com.project.api.data.enums.PlaceType;
import com.project.api.data.enums.ProductType;
import com.project.api.data.model.event.EventType;
import com.project.api.data.model.place.Localisation;
import com.project.data.elastic.entity.Suggestion;
import com.project.data.service.IAutocompleteService;


@RestController
public class TestController {

	@Autowired
	private IAutocompleteService autocompleteService;
	
	@GetMapping("/")
	public List<Suggestion> test() {
//        Book book = new Book("1001", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017");
//        Book testBook = bookService.save(book);
		Suggestion suggestion = new Suggestion();
		suggestion.setLabel("Shemall");
		suggestion.setSlug("/shemall");
		suggestion.setProductId(3L);
		suggestion.setEventType(EventType.NOTSET);
		suggestion.setPlaceType(PlaceType.MALL);
		suggestion.setProductType(ProductType.PLACE);
		suggestion.setLanguage(Language.TURKISH);
		suggestion.setKeywords(Arrays.asList("penti", "cinema pink"));
		List<Localisation> localisations = new ArrayList<>();
		localisations.add(new Localisation("Shemall Alışveriş Merkezi", Language.TURKISH, "/shemall"));
		localisations.add(new Localisation("Shemall Shopping Mall", Language.ENGLISH, "/shemall"));
		suggestion.setLocalisations(localisations);
		
//				autocompleteService.save(suggestion);
//		
//		return Arrays.asList(suggestion);
		return autocompleteService.search("Shemall", Language.TURKISH);
	}
	
}
