package TypeWriting.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import TypeWriting.mapper.TestMapper;
import TypeWriting.service.TestService;

@Service("TestServiceImpl")
public class TestServiceImpl implements TestService{

	@Resource(name = "testMapper")
	TestMapper testMapper;
	
	@Override
	public void testMethod() {
		System.out.println("test method");
		String s = testMapper.testMapper();
		System.out.println(s);
	}

}
