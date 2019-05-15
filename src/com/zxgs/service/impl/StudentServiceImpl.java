/**
 * 
 */
package com.zxgs.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxgs.common.Constant;
import com.zxgs.dao.StuTeacherMappingMapper;
import com.zxgs.dao.StudentScoreMapper;
import com.zxgs.dao.SubjectCaseMapper;
import com.zxgs.dao.SubjectInfoMapper;
import com.zxgs.dao.UserInfoMapper;
import com.zxgs.entity.StuTeacherMapping;
import com.zxgs.entity.StuTeacherMappingExample;
import com.zxgs.entity.StudentCompletion;
import com.zxgs.entity.StudentScore;
import com.zxgs.entity.StudentScoreExample;
import com.zxgs.entity.SubjectCase;
import com.zxgs.entity.SubjectCaseExample;
import com.zxgs.entity.SubjectInfo;
import com.zxgs.entity.SubjectInfoExample;
import com.zxgs.entity.UserInfo;
import com.zxgs.entity.UserInfoExample;
import com.zxgs.service.IStudentService;
import com.zxgs.tools.ToolsImpl;

/**
 * @Desc //TODO 实现学生服务接口
 * @author zxk
 *
 * @Date 2019年5月8日 上午11:18:18
 */
@Service("IStudentService")
public class StudentServiceImpl implements IStudentService {
	
	@Autowired
	private StuTeacherMappingMapper stuTeacherMappingMapper;
	
	@Autowired
	private StudentScoreMapper studentScoreMapper;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private SubjectInfoMapper subjectInfoMapper;
	
	@Autowired
	private SubjectCaseMapper subjectCaseMapper;
	
	

	/**
	 * //TODO 
	 * @auther zxk
	 * @ 2019年5月8日 上午11:18:18
	 */
	public StudentServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/* 
	 * @auther zxk
	 * @2019年5月8日 上午11:38:13
	 * @param id
	 * @return
	 */
	@Override
	public List<StuTeacherMapping> getTeacherId(String studenId) {
		// TODO Auto-generated method stub
		StuTeacherMappingExample stuTeacherMappingExample = new StuTeacherMappingExample();
		stuTeacherMappingExample.or().andStudentIdEqualTo(Integer.parseInt(studenId));
		List<StuTeacherMapping> list = stuTeacherMappingMapper.selectByExample(stuTeacherMappingExample);
		return list.isEmpty()? null : list;
	}

	/* 
	 * @auther zxk
	 * @2019年5月8日 下午5:00:30
	 * @param studenId
	 * @return
	 */
	@Override
	public List<List<StudentCompletion>> getDoneSubject(String studenId) {
		// TODO Auto-generated method stub
		StudentScoreExample studentScoreExample = new StudentScoreExample();
		studentScoreExample.or().andStudentIdEqualTo(Integer.parseInt(studenId));
		List<StudentScore> list = studentScoreMapper.selectByExample(studentScoreExample);
		
		List<StudentCompletion> passList = new ArrayList<StudentCompletion>();
		List<StudentCompletion> notPassList = new ArrayList<StudentCompletion>();
		List<List<StudentCompletion>> result = new ArrayList<List<StudentCompletion>>();
		if(list.isEmpty()){//如果该学生还没有提交过一道题
			return null;
		}else{
			for(int i = 0;i<list.size();i++){
				UserInfo userInfo = userInfoMapper.selectByPrimaryKey(list.get(i).getTeacherId());
				SubjectInfo subjectInfo = subjectInfoMapper.selectByPrimaryKey(list.get(i).getSubjectId());
				 //获取老师姓名
				String teacherName = userInfo.getUserName();
				//获取题目难度
				Integer level = subjectInfo.getLevel();
				//获取题目名称
				String subjectName = subjectInfo.getSubjectName();
				
				StudentCompletion studentCompletion = 
						new StudentCompletion(subjectName, level, teacherName, list.get(i).getStuScore());
				if(list.get(i).getStuScore() >= 60 && list.get(i).getStuScore() <= 100){
					//分数[60,100]
					passList.add(studentCompletion);				
				}else if(list.get(i).getStuScore() < 60 && list.get(i).getStuScore() > 0){
					//分数（0,60）之间
					notPassList.add(studentCompletion);				
				}
			}
			result.add(passList);//通过的题目
			result.add(notPassList);//未通过的题目
			return result;
		}
	}


	/* 
	 * @auther zxk
	 * @2019年5月8日 下午5:00:30
	 * @param studenId
	 * @return
	 */
	@Override
	public List<StudentCompletion> getUndoSubject(String studenId) {
		// TODO Auto-generated method stub
		List<StudentCompletion> list = new ArrayList<StudentCompletion>();
		List<SubjectInfo> leftList = subjectInfoMapper.selectLeftSubject(Integer.parseInt(studenId));
		
		if(leftList.isEmpty()){
			return null;//做完该老师的题目或该老师没有布置过题目
		}else{
			for(int i = 0;i<leftList.size();i++){
				UserInfo userInfo = userInfoMapper.selectByPrimaryKey(leftList.get(i).getTeacherId());
				StudentCompletion studentCompletion = 
						new StudentCompletion(leftList.get(i).getSubjectName(),
								leftList.get(i).getLevel(), userInfo.getUserName(), 0);
				list.add(studentCompletion);
			}
			return list.isEmpty()? null : list;
		}
	}

	/* 
	 * @auther zxk
	 * @2019年5月9日 上午10:33:52
	 * @return
	 */
	@Override
	public List<UserInfo> getAllTeacher() {
		// TODO Auto-generated method stub
		UserInfoExample userInfoExample = new UserInfoExample();
		userInfoExample.or().andRoleEqualTo(2);
		List<UserInfo> userInfoList = userInfoMapper.selectByExample(userInfoExample);
		return userInfoList.isEmpty()? null : userInfoList;
	}

	/* 
	 * @auther zxk
	 * @2019年5月9日 上午10:39:56
	 * @param teacherId
	 * @return
	 */
	@Override
	public List<StudentCompletion> getTeacherSubject(StuTeacherMapping stuTeacherMapping) {
		// TODO Auto-generated method stub
		SubjectInfoExample subjectInfoExample = new SubjectInfoExample();
		subjectInfoExample.or().andTeacherIdEqualTo(stuTeacherMapping.getTeacherId());
		List<SubjectInfo> list = subjectInfoMapper.selectByExampleWithBLOBs(subjectInfoExample);//该老师发布的题目
		List<StudentCompletion> studentCompletionList = new ArrayList<StudentCompletion>();
		
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(stuTeacherMapping.getTeacherId());
		if(stuTeacherMappingMapper.insert(stuTeacherMapping) > 0){//插入学生老师对应表失败
			if(list.isEmpty()){//该老师没有发布过题目
				return null;
			}else{
				for(int i = 0;i<list.size();i++){
					StudentCompletion studentCompletion = 
							new StudentCompletion(list.get(i).getSubjectName(), list.get(i).getLevel(), 
									userInfo.getUserName(), 0);
					studentCompletionList.add(studentCompletion);
				}
				return studentCompletionList;
			}
		}else{
			return null;
		}
	}

	/* 
	 * @auther zxk
	 * @2019年5月9日 下午5:04:33
	 * @return
	 */
	@Override
	public List<UserInfo> getLeftTeacher(UserInfo userInfo) {
		// TODO Auto-generated method stub
		List<UserInfo> userInfos = userInfoMapper.selectLeftTeacher(userInfo.getId());
		return userInfos.isEmpty()? null : userInfos;
	}

	/* 
	 * @auther zxk
	 * @2019年5月14日 上午11:57:23
	 * @param code
	 * @return
	 */
	@Override
	public Integer runCode(HttpServletRequest request, String code,String url,String subjectName,String teacherName) throws IOException {
		// TODO Auto-generated method stub
		Integer flag = -1;
		Integer score = 0;
		ToolsImpl toolsImpl = new ToolsImpl();
		String className = toolsImpl.getFile(Constant.CODEPATH+teacherName+"\\"+subjectName);
		toolsImpl.createFile(url, className+".java");
		toolsImpl.createFile(url, "err.txt");//错误输出
		toolsImpl.createFile(url, "input.txt");//输入
		toolsImpl.createFile(url, "output.txt");//正确输出
		code = toolsImpl.buildCode(Constant.CODEPATH+teacherName+"\\"+subjectName+"\\"+className+".java",code);
		
		toolsImpl.writeToFile(url+className+".java", code);
		List<SubjectCase> subjectCases = null;
		
		UserInfoExample userInfoExample = new UserInfoExample();
		userInfoExample.or().andUserNameEqualTo(teacherName);
		List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
		request.getSession().setAttribute(Constant.TEACHERID,userInfos.get(0).getId()+"");
		if(!userInfos.isEmpty()){//存在该老师
			SubjectInfoExample subjectInfoExample = new SubjectInfoExample();
			subjectInfoExample.or()
			.andSubjectNameEqualTo(subjectName)
			.andTeacherIdEqualTo(userInfos.get(0).getId());//where subject_name = ? and teacher_id = ?
			List<SubjectInfo> subjectInfos = subjectInfoMapper.selectByExample(subjectInfoExample);
			if(!subjectInfos.isEmpty()){
				//存在该老师发布的该题目
				SubjectCaseExample subjectCaseExample = new SubjectCaseExample();
				subjectCaseExample.or().andSubjectIdEqualTo(subjectInfos.get(0).getId());
				request.getSession().setAttribute(Constant.SUBJECTID,subjectInfos.get(0).getId()+"");
				subjectCases = subjectCaseMapper.selectByExample(subjectCaseExample);			
			}
		}
		
		if(toolsImpl.compiler(url+className+".java",url)){
			//编译通过
			flag = 0;
			//测试用例存在
			for(int i = 0;i<subjectCases.size();i++){
				//将测试用例写入文件
				toolsImpl.writeToFile(url+"input.txt", subjectCases.get(i).getTestCase());
				if(toolsImpl.run(url,className)){
					String re = toolsImpl.readFile(url+"output.txt");//运行结果
					if(subjectCases.get(i).getExpectedValue().equals(re)){
						//运行结果和期望结果相同
						score += subjectCases.get(i).getScore();
						System.out.println("score:"+score);
					}
					flag = score;//编译通过且运行成功
				}else{
					flag = 2;//编译通过但运行不成功
					return flag;
				}
			}
		}else{
			//编译未通过
			flag = -2;
		}
		return flag;
	}

	/* 
	 * @auther zxk
	 * @2019年5月15日 上午10:28:11
	 * @param studentScore
	 */
	@Override
	public void updateScoreInfo(StudentScore studentScore) {
		// TODO Auto-generated method stub
		studentScoreMapper.insertSelective(studentScore);
	}
}
