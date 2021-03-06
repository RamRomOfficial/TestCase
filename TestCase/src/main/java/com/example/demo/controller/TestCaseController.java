package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.TestCaseCounter;
import com.example.demo.model.TestCaseModel;
import com.example.demo.resource.TestCaseResource;
import com.example.demo.service.TestCaseService;


@RequestMapping("/api/v1")
@RestController
public class TestCaseController {

	 
		@Autowired
		private TestCaseService testCaseService;
		
		@PostMapping("/testcase")	
		public String createProject(@RequestBody TestCaseModel testCaseModel)
		{
			TestCaseCounter counter=testCaseService.uniqueValue(TestCaseResource.COUNTER_DOCUMENT_ID);
			testCaseModel.setId("Test-"+String.valueOf(counter.getSeq()));
			
			return testCaseService.addTestCase(testCaseModel);
		}
		
		@GetMapping("/testcase")	
		public List<TestCaseModel> allTestCase()
		{
			return testCaseService.getAllTestCase();
		}
		
		
		@GetMapping("/testcase/{id}")
		public TestCaseModel testcaseByID(@PathVariable("id") String id){
			return testCaseService.getByTestCaseId(id);
			
		}
		
		@PutMapping("/testcase/{id}")	
		public String updateProject(@PathVariable("id") String id,@RequestBody TestCaseModel testCaseModel)
		{
			return testCaseService.updateProject(testCaseModel,id);
		}
		
		@DeleteMapping("/testcase/{id}")
		public String deleteRequirement(@PathVariable("id") String id) {
			
			return testCaseService.updateProject(null,id);
			
		}
		
}
