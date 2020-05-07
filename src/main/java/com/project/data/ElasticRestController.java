package com.project.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.data.enums.Language;
import com.project.api.data.enums.ProductType;
import com.project.api.data.model.autocomplete.Item;
import com.project.api.data.model.place.Localisation;
import com.project.data.elastic.entity.Suggestion;
import com.project.data.service.IAutocompleteService;


@RestController
@RequestMapping("/v1/elastic")
public class ElasticRestController {

	@Autowired
	private IAutocompleteService autocompleteService;
	
	@GetMapping("/autocomplete/places")
	public List<Item> callAutocomplete(@RequestParam String query, @RequestParam(defaultValue = "TR") String languageCode) {
		List<Suggestion> suggestions = autocompleteService.search(query, Language.getByCode(languageCode));
		if (CollectionUtils.isEmpty(suggestions)) {
			return Collections.emptyList();
		}
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < suggestions.size(); i++) {
			Optional<Localisation> localisation = suggestions.get(i).getLocalisations().stream().filter(l -> languageCode.equalsIgnoreCase(l.getLanguage().getCode())).findFirst();
			if (localisation.isPresent()) {
				Item item = new Item();
				item.setLabel(localisation.get().getName());
				item.setId(suggestions.get(i).getProductId());
				item.setProductType(suggestions.get(i).getProductType());
				if (item.getProductType()  == ProductType.EVENT) {
					item.setType(suggestions.get(i).getEventType().toString());
				} else {
					item.setType(suggestions.get(i).getPlaceType().toString());
				}
				item.setLanguage(Language.getByCode(languageCode));
				StringBuilder urlBuilder = new StringBuilder();
				if (Language.RUSSIAN != Language.getByCode(languageCode)) {
					urlBuilder.append("/").append(languageCode.toLowerCase());
				}
				urlBuilder.append("/places/").append(localisation.get().getSlug());
				item.setUrl(urlBuilder.toString());  
				items.add(item);
			}
		}
		return items;
	}
	
	@GetMapping("/autocomplete/put")
	public String putToElasticSeach() {
		autocompleteService.putPlacesToElasticSearch();
		return null;
	}
}
