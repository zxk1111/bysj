/**
 * 
 */
package com.zxgs.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zxgs.entity.StuTeacherMapping;
import com.zxgs.entity.StudentCompletion;
import com.zxgs.entity.StudentScore;
import com.zxgs.entity.SubjectInfo;
import com.zxgs.entity.UserInfo;

/**
 * @Desc //TODO 学生服务接口
 * @author zxk
 *
 * @Date 2019年5月8日 上午11:17:49
 */
public interface IStudentService {

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月8日 上午11:38:03
	 * @param id
	 * @return
	 */
	List<StuTeacherMapping> getTeacherId(String studenId);

	/**
	 * //TODO 查找学生未做过的题目
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月8日 下午4:59:34
	 * @param studenId
	 * @return
	 */
	List<StudentCompletion> getUndoSubject(String studenId);

	/**
	 * //TODO 获取已经做过的题目
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月8日 下午5:48:17
	 * @param studenId
	 * @return
	 */
	List<List<StudentCompletion>> getDoneSubject(String studenId);

	/**
	 * //TODO 获取所有老师信息
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 上午10:33:44
	 * @return
	 */
	List<UserInfo> getAllTeacher();

	/**
	 * //TODO 获取该老师信息
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 上午10:39:31
	 * @param parseInt
	 * @return
	 */
	List<StudentCompletion> getTeacherSubject(StuTeacherMapping stuTeacherMapping);

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 下午5:04:27
	 * @return
	 */
	List<UserInfo> getLeftTeacher(UserInfo userInfo);

	/**
	 * //TODO
	 * @param request 
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月14日 上午11:57:09
	 * @param code
	 * @param url 
	 * @param subjectName 
	 * @param userName 
	 * @throws IOException 
	 */
	Integer runCode(HttpServletRequest request, String code, String url, String subjectName, String teacherName) throws IOException;

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月15日 上午10:28:01
	 * @param studentScore
	 */
	void updateScoreInfo(StudentScore studentScore);

}
