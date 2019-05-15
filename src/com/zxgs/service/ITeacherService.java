/**
 * 
 */
package com.zxgs.service;

import java.io.IOException;
import java.util.List;

import com.zxgs.entity.SubjectCase;
import com.zxgs.entity.SubjectInfo;
import com.zxgs.entity.TestDataInfo;
import com.zxgs.entity.UserInfo;
import com.zxgs.util.PlClientBaseModel;

/**
 * @Desc //TODO 老师服务接口
 * @author zxk
 *
 * @Date 2019年5月7日 下午3:59:31
 */
public interface ITeacherService {

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月7日 下午4:10:27
	 * @param result
	 * @param requestPage
	 * @param requestNumber
	 * @return
	 */
	PlClientBaseModel getPage(PlClientBaseModel result, Integer requestPage, Integer requestNumber);

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月7日 下午4:50:22
	 * @param subjectInfo
	 * @param listCase
	 * @param path 
	 * @throws IOException 
	 */
	Integer insert(List<TestDataInfo> listCase, String path) throws IOException;

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 上午9:32:22
	 * @param teacherId
	 * @return 
	 */
	List<UserInfo> getStudentInfo(String teacherId);

	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 上午9:57:26
	 * @param teacherId
	 * @return
	 */
	List<SubjectInfo> getSubjectInfo(String teacherId);

}
