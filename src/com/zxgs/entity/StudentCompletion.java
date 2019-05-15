/**
 * 
 */
package com.zxgs.entity;

/**
 * @Desc //TODO 学生完成情况
 * @author zxk
 *
 * @Date 2019年5月8日 下午4:44:23
 */
public class StudentCompletion {
	
	private String sbjectName;
	
	private Integer level;
	
	private String teacherName;
	
	private Integer score;
	
	
	
	public StudentCompletion(String sbjectName, Integer level, String teacherName, Integer score) {
		super();
		this.sbjectName = sbjectName;
		this.level = level;
		this.teacherName = teacherName;
		this.score = score;
	}
	/**
	 * //TODO 
	 * @auther zxk
	 * @ 2019年5月8日 下午4:44:23
	 */
	public StudentCompletion() {
		// TODO Auto-generated constructor stub
	}
	public String getSbjectName() {
		return sbjectName;
	}
	public void setSbjectName(String sbjectName) {
		this.sbjectName = sbjectName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}
