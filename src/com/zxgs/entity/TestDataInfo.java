/**
 * 
 */
package com.zxgs.entity;

/**
 * @Desc //TODO 测试数据类
 * @author zxk
 *
 * @Date 2019年5月9日 下午2:44:51
 */
public class TestDataInfo {
	
	private String testCase;
	
	private String expectedValue;
	
	private Integer score;
	
	private SubjectInfo subjectInfo;

	/**
	 * //TODO 
	 * @auther zxk
	 * @ 2019年5月9日 下午2:44:51
	 */
	public TestDataInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getTestCase() {
		return testCase;
	}

	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}

	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public SubjectInfo getSubjectInfo() {
		return subjectInfo;
	}

	public void setSubjectInfo(SubjectInfo subjectInfo) {
		this.subjectInfo = subjectInfo;
	}
	
	

}
