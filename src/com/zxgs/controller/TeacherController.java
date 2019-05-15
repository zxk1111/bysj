/**
 * 
 */
package com.zxgs.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zxgs.common.Constant;
import com.zxgs.entity.SubjectInfo;
import com.zxgs.entity.TestDataInfo;
import com.zxgs.entity.UserInfo;
import com.zxgs.error.BusinessException;
import com.zxgs.error.EmBusinessError;
import com.zxgs.js.JSBaseParameter;
import com.zxgs.service.ITeacherService;
import com.zxgs.util.PlClientBaseModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Desc //TODO 老师控制器
 * @author zxk
 *
 * @Date 2019年5月7日 下午3:57:41
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/sys/teacher")
public class TeacherController extends WebBaseController<JSBaseParameter>{
	
	@Autowired
	private ITeacherService iTeacherService;

	/**
	 * //TODO 
	 * @auther zxk
	 * @ 2019年5月7日 下午3:57:41
	 */
	public TeacherController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * //TODO 分页查询
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月7日 下午4:13:04
	 * @param request
	 * @param response
	 * @param pageIndex
	 * @param pageSize
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/getPage")
	public void getPage(HttpServletRequest request, HttpServletResponse response,
			String pageIndex,String pageSize) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result=new PlClientBaseModel();
		
		if(StringUtils.isBlank(pageSize) || StringUtils.isBlank(pageIndex)
				|| 0 == Integer.parseInt(pageSize) || 0 == Integer.parseInt(pageIndex)){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			result = iTeacherService.getPage(result, Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
		}
		//返回前台数据
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 发布题目
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 上午11:45:00
	 * @param request
	 * @param response
	 * @param subjectInfo
	 * @param listCase
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/insert",produces = "application/json")
	public void insert(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String object) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result = new PlClientBaseModel();
		JSONArray list = JSONArray.fromObject(object);//转成json类型的数组
		@SuppressWarnings({ "unchecked", "deprecation" })
		List<TestDataInfo> testDataInfoList = JSONArray.toList(list,TestDataInfo.class);//转成list类型
		
		
		//判空
		if(testDataInfoList == null){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{//subjectInfo和listCase都不为空
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute(Constant.SESSION_SYS_USER);
			//存储老师编写的主函数的路径
			String path=Constant.CODEPATH+userInfo.getUserName() +"\\"+
					testDataInfoList.get(0).getSubjectInfo().getSubjectName()+"\\";
			
			int totol = 0;//总分
			int i = 0;		
			for(;i<testDataInfoList.size();i++){
				if(StringUtils.isBlank(testDataInfoList.get(i).getExpectedValue()) ||
						StringUtils.isBlank(testDataInfoList.get(i).getSubjectInfo().getMainCodeUrl()) ||
						StringUtils.isBlank(testDataInfoList.get(i).getTestCase()) ||
						testDataInfoList.get(i).getScore() == null ||
						testDataInfoList.get(i).getSubjectInfo().getLevel() == null ||
						testDataInfoList.get(i).getSubjectInfo().getSubjectChapter() == null ||
						StringUtils.isBlank(testDataInfoList.get(i).getSubjectInfo().getSubjectContent()) ||
						StringUtils.isBlank(testDataInfoList.get(i).getSubjectInfo().getSubjectName()) ||
						testDataInfoList.get(i).getSubjectInfo().getTeacherId() == null){//判断listCase是否有空值
					
					result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
					result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
					result.setMessage("error");
					break;
				}else{//计算总分
					totol+=testDataInfoList.get(i).getScore();
				}
			}
			
			if(i == testDataInfoList.size() && totol != 100){//listCase没有空值，并且总分相加正确
				result.setCode(EmBusinessError.TOTOL_SCORE_WRONG.getErrCode());
				result.setData(EmBusinessError.TOTOL_SCORE_WRONG.getErrMsg());
				result.setMessage("error");
			}else if(i == testDataInfoList.size() && totol == 100){//没有空值且总分正确
				Integer temp = iTeacherService.insert(testDataInfoList,path);
				if(temp == 0){
					//插入失败
					result.setCode(EmBusinessError.SUBJECT_INSERT_FAILURE.getErrCode());
					result.setData(EmBusinessError.SUBJECT_INSERT_FAILURE.getErrMsg());
					result.setMessage("error");
				}else if(temp == 3){//该老师布置过相同题目名字的题目
					result.setCode(EmBusinessError.SUBJECT_PUBLISHED.getErrCode());
					result.setData(EmBusinessError.SUBJECT_PUBLISHED.getErrMsg());
					result.setMessage("error");
				}else{//返回插入成功的数据
					result.setData(testDataInfoList);
				}
			}
		}
		//信息返回给前台
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 获取选择该老师的学生信息
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 上午9:51:01
	 * @param request
	 * @param response
	 * @param teacherId
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/getStudent")
	public void getStudent(HttpServletRequest request, HttpServletResponse response,
			String teacherId) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result = new PlClientBaseModel();
		//判空
		if(StringUtils.isBlank(teacherId)){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			List<UserInfo> userInfoList = iTeacherService.getStudentInfo(teacherId);
			result.setData(userInfoList);
		}
		//信息返回给前台
		writeJSON(response, result);
	}
	
	/**
	 * 
	 * //TODO 获得该老师发布的所有题目信息
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月9日 上午11:40:01
	 * @param request
	 * @param response
	 * @param teacherId
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws BusinessException
	 */
	@RequestMapping("/getPublishedSubject")
	public void getPublishedSubject(HttpServletRequest request, HttpServletResponse response,
			String teacherId) throws IOException, NoSuchAlgorithmException, BusinessException {
		PlClientBaseModel result = new PlClientBaseModel();
		//判空
		if(StringUtils.isBlank(teacherId)){
			result.setCode(EmBusinessError.PARAMETER_IS_NULL.getErrCode());
			result.setData(EmBusinessError.PARAMETER_IS_NULL.getErrMsg());
			result.setMessage("error");
		}else{
			List<SubjectInfo> subjectInfoList = iTeacherService.getSubjectInfo(teacherId);
			result.setData(subjectInfoList);
		}
		//信息返回给前台
		writeJSON(response, result);
	}
	

}
