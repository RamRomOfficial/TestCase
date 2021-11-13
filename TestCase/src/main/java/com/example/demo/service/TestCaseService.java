package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.model.TestCaseCounter;
import com.example.demo.model.TestCaseModel;
import com.example.demo.resource.TestCaseResource;
import com.example.demo.utilites.TestCaseUtility;


@Service
public class TestCaseService {
private MongoTemplate mongoTemplate;
	
	@Autowired
	private MongoOperations mongoOperation;

	public TestCaseService(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public TestCaseModel getByTestCaseId(String id) {
		return mongoTemplate.findOne(TestCaseUtility.getQueryByKeyValue("id", id), TestCaseModel.class);
	}

	public List<TestCaseModel> getAllTestCase() {
	
		return   mongoTemplate.findAll(TestCaseModel.class);
	}
	public String addTestCase(TestCaseModel testcaseModel)
	{
			
		if(mongoTemplate.insert(testcaseModel)!=null)
		return " Inserted";
		else
		return "Not Inserted";
		
	}
	
	public TestCaseCounter uniqueValue(String key)
	{
		
		 Update update = new Update();
		  update.inc(TestCaseResource.COUNTER_DOCUMENT_SEQUENCE_COLUMN, 1);
		  FindAndModifyOptions options = new FindAndModifyOptions();
		  options.returnNew(true).upsert(true);
		  TestCaseCounter counter= mongoOperation.findAndModify(TestCaseUtility.getQueryByKeyValue("_id", key), update, options, TestCaseCounter.class);

		return counter;
		
	}
	
	public String updateProject(TestCaseModel testCaseModel,String id) {
		TestCaseModel requestedTestCase=getByTestCaseId(id);
		
		if(testCaseModel==null)
		{
			requestedTestCase.setStatus(TestCaseResource.TESTCASE_STATUS_ONHOLD);
			mongoTemplate.save( requestedTestCase);
			return "testCase Deleted";
		}
		
		if(testCaseModel.getName()!=null)
		{
			 requestedTestCase.setName(testCaseModel.getName());
		}
		if(testCaseModel.getDescription()!=null)
		{
			 requestedTestCase.setDescription(testCaseModel.getDescription());
		}
		if(testCaseModel.getExpectedResults()!=null)
		{
			 requestedTestCase.setExpectedResults(testCaseModel.getExpectedResults());
		}
		if(testCaseModel.getActualResults()!=null)
		{
			 requestedTestCase.setActualResults(testCaseModel.getActualResults());
		}
		if(testCaseModel.getStatus()!=null && !testCaseModel.getStatus().equals(TestCaseResource.TESTCASE_STATUS_ONHOLD))
		{
			 requestedTestCase.setStatus(testCaseModel.getStatus());
		}
		
		mongoTemplate.save( requestedTestCase);
		
		return "TestCase "+ requestedTestCase.getId()+" Updated";
			
		
	}
	
	
	
	
	
	
	
}
