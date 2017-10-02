package com.bridgeit.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bridgeit.entity.Student;

@Controller
@RequestMapping("/")
public class FormValidationController {

	// this method serves as default request handler

	@RequestMapping(method = RequestMethod.GET)
	public String newRegistration(ModelMap map) {
		System.out.println("Reached in request");
		Student student = new Student();
		map.addAttribute("student", student);
		return "enroll";
	}


	/*
	 * Notice that BindingResult must come right after the validated object else
	 * spring won’t be able to validate and an exception been thrown.
	 */
	
	@RequestMapping(method = RequestMethod.POST)
	public String showRegistration(@Valid Student student, BindingResult results, ModelMap map) {

		if (results.hasErrors())
			return "enroll";
		map.addAttribute("success", "Dear " + student.getFirstName() + ", Your Registration is Successfull.");
		return "success"; //sucess.jsp
	}

	/*
	 * Method used to populate the sections list in view. Note that here you can
	 * call external systems to provide real data.
	 */

	@ModelAttribute("sections")
	public List<String> initializeSections() {
		List<String> sections = new ArrayList<String>();
		sections.add("Graduate");
		sections.add("Post-Graduate");
		sections.add("Research");
		return sections;
	}

	@ModelAttribute("countries")
	public List<String> initializeCountries() {
		List<String> countries = new ArrayList<String>();
		countries.add("India");
		countries.add("Brazil");
		countries.add("USA");
		countries.add("Argentina");
		countries.add("France");
		countries.add("Germany");
		return countries;
	}

	@ModelAttribute("subjects")
	public List<String> initializeSubjects() {
		List<String> subjects = new ArrayList<String>();
		subjects.add("Science");
		subjects.add("Life Science");
		subjects.add("Political Science");
		subjects.add("Social Science");
		subjects.add("History");
		subjects.add("Geography");
		return subjects;
	}

}
