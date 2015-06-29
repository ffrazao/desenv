package gov.emater.aterweb.mvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Furacao {

	@RequestMapping(value = "furacao")
	public Map<String, Object> furacao() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Vento", 123);
		List<Integer> intList = new ArrayList<Integer>();
		intList.add(4);
		intList.add(433);
		intList.add(14);
		intList.add(54);
		result.put("List", intList);
		return result;
	}

}
