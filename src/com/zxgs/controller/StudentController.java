/**
 * 
 */
package com.zxgs.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.AbstractDocument.Content;

import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zxgs.common.Constant;
import com.zxgs.entity.StuTeacherMapping;
import com.zxgs.entity.StudentCompletion;
import com.zxgs.entity.StudentScore;
import com.zxgs.entity.UserInfo;
import com.zxgs.error.BusinessException;
import com.zxgs.error.EmBusinessError;
import com.zxgs.js.JSBaseParameter;
import com.zxgs.service.IStudentService;
import com.zxgs.tools.ToolsImpl;
import com.zxgs.util.PlClientBaseModel;

/**
 * @Desc //TODO 学生控制器
 * @author zxk
 *
 * @Date 2019年5月7日 下午3:56:39
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/sys/student")
public class StudentController extends WebBaseController<JSBaseParameter>{

	@Autowired
	private IStudentService iStudentService;
	/**
	 * //TODO 
	 * @auther zxk
	 * @ 2019年5月7日 下午3:56:39
	 */
	public StudentController() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * 
	 * //TODO 查找该学生通过的题目信息和未通过的题目信息，或者未做过的题目信息
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 上午11:46:07
	 * @param request
	 * @param response
	 * @param studenId
	 * @param flag
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/getSubject")
	public void get(HttpServletRequest request, HttpServletResponse response
			, String studentId,Integer flag) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		//未做的题目
		List<StudentCompletion> undoList = new ArrayList<StudentCompletion>();
		//做过的题目(index=0为通过的，index=1为未通过的)
		List<List<StudentCompletion>> list =  new ArrayList<List<StudentCompletion>>();
		
		if(StringUtils.isBlank(studentId) || flag == null){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			if(flag == 1){//flag等于1，则查找通过的题目
				list = iStudentService.getDoneSubject(studentId);
				result.setData(list);
			}else{//flag等于其他则查找未完成的题目
				undoList = iStudentService.getUndoSubject(studentId);
				result.setData(undoList);
			}
		}
		//返回前台数据
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 获取所有老师信息
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 上午10:38:07
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/getAllTeacher")
	public void getAllTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		userInfoList = iStudentService.getAllTeacher();
		
		result.setData(userInfoList);//可能返回值为空（没有老师注册）
		//返回前台数据
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 获取该学生剩下未选择的老师
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 下午5:30:31
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/getLeftTeacher")
	public void getLeftTeacher(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		userInfoList = iStudentService.getLeftTeacher((UserInfo)request.getSession().getAttribute(Constant.SESSION_SYS_USER));
		
		result.setData(userInfoList);//可能返回值为空（没有老师注册）
		//返回前台数据
		writeJSON(response, result);
	}
	
	
	/**
	 * 
	 * //TODO 选择老师
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 上午11:45:40
	 * @param request
	 * @param response
	 * @param teacherId
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/chooseTeacher")
	public void chooseTeacher(HttpServletRequest request, HttpServletResponse response
			,StuTeacherMapping stuTeacherMapping) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		
		if(stuTeacherMapping.getStudentId() == null
				&& stuTeacherMapping.getTeacherId() == null){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			List<StudentCompletion> studentCompletionList = iStudentService.getTeacherSubject(stuTeacherMapping);
			if(studentCompletionList == null){
				result.setCode(EmBusinessError.TEACHER_NOT_PUBLISH_SUBJECT.getErrCode());
				result.setData(EmBusinessError.TEACHER_NOT_PUBLISH_SUBJECT.getErrMsg());
				result.setMessage("error");
			}else{
				result.setData(studentCompletionList);
			}
		}
		//返回前台数据
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 执行代码
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月15日 下午3:23:30
	 * @param request
	 * @param response
	 * @param code
	 * @param subjectName
	 * @param teacherName
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/runCode")
	public void runCode(HttpServletRequest request, HttpServletResponse response
			, @RequestParam(value = "code")String code,
			@RequestParam(value = "subjectName") String subjectName, 
			@RequestParam(value = "teacherName")String teacherName) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		ToolsImpl toolsImpl = new ToolsImpl();
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute(Constant.SESSION_SYS_USER);
		
		
		if(StringUtils.isBlank(code) || StringUtils.isBlank(teacherName) ||
				StringUtils.isBlank(subjectName)){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			String path=Constant.CODEPATH+userInfo.getUserName()+"\\"+subjectName+"\\";
			Integer temp = iStudentService.runCode(request,code,path,subjectName,teacherName);
			if(temp == 2){//运行失败
				result.setCode(EmBusinessError.RUN_FAIL.getErrCode());
				result.setData(EmBusinessError.RUN_FAIL.getErrMsg()+
						","+toolsImpl.readFile(path+"err.txt"));
				result.setMessage("error");
			}else if(temp == -2){//编译失败
				result.setCode(EmBusinessError.COMPILER_FAIL.getErrCode());
				result.setData(EmBusinessError.COMPILER_FAIL.getErrMsg()+
						","+toolsImpl.readFile(path+"err.txt"));
				result.setMessage("error");
			}
		}
		//返回前台数据
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 提交代码
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月15日 下午3:23:18
	 * @param request
	 * @param response
	 * @param code
	 * @param subjectName
	 * @param teacherName
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/submitCode")
	public void submitCode(HttpServletRequest request, HttpServletResponse response
			, @RequestParam(value = "code")String code,
			@RequestParam(value = "subjectName") String subjectName, 
			@RequestParam(value = "teacherName")String teacherName) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		ToolsImpl toolsImpl = new ToolsImpl();
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute(Constant.SESSION_SYS_USER);

		if(StringUtils.isBlank(code) || StringUtils.isBlank(teacherName) ||
				StringUtils.isBlank(subjectName)){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			String path=Constant.CODEPATH+userInfo.getUserName()+"\\"+subjectName+"\\";
			Integer temp = iStudentService.runCode(request,code,path,subjectName,teacherName);
			if(temp == 2){//运行失败
				result.setCode(EmBusinessError.RUN_FAIL.getErrCode());
				result.setData(EmBusinessError.RUN_FAIL.getErrMsg()+
						","+toolsImpl.readFile(path+"err.txt"));
				result.setMessage("error");
			}else if(temp == -2){//编译失败
				result.setCode(EmBusinessError.COMPILER_FAIL.getErrCode());
				result.setData(EmBusinessError.COMPILER_FAIL.getErrMsg()+
						","+toolsImpl.readFile(path+"err.txt"));
				result.setMessage("error");
			}else{
				//存储得分
				StudentScore studentScore = new StudentScore();
				studentScore.setCodeUrl(path+toolsImpl.getFile(Constant.CODEPATH+teacherName+"\\"+subjectName)+".java");
				studentScore.setStudentId(((UserInfo)request.getSession().getAttribute(Constant.SESSION_SYS_USER)).getId());
				studentScore.setStuScore(temp);
				studentScore.setSubjectId(Integer.parseInt((String) request.getSession().getAttribute(Constant.SUBJECTID)));
				studentScore.setTeacherId(Integer.parseInt((String) request.getSession().getAttribute(Constant.TEACHERID)));
				iStudentService.updateScoreInfo(studentScore);
			}
		}
		//返回前台数据
		writeJSON(response, result);
	}

}
