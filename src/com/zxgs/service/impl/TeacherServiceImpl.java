/**
 * 
 */
package com.zxgs.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zxgs.dao.StuTeacherMappingMapper;
import com.zxgs.dao.SubjectCaseMapper;
import com.zxgs.dao.SubjectInfoMapper;
import com.zxgs.dao.UserInfoMapper;
import com.zxgs.entity.ObjectReturnMessage;
import com.zxgs.entity.StuTeacherMapping;
import com.zxgs.entity.StuTeacherMappingExample;
import com.zxgs.entity.SubjectCase;
import com.zxgs.entity.SubjectInfo;
import com.zxgs.entity.SubjectInfoExample;
import com.zxgs.entity.TestDataInfo;
import com.zxgs.entity.UserInfo;
import com.zxgs.error.EmBusinessError;
import com.zxgs.service.ITeacherService;
import com.zxgs.test.TestCase;
import com.zxgs.tools.ToolsImpl;
import com.zxgs.util.PlClientBaseModel;

/**
 * @Desc //TODO 老师服务实现类
 * @author zxk
 *
 * @Date 2019年5月7日 下午4:00:31
 */
@Service("ITeacherService")
public class TeacherServiceImpl implements ITeacherService {
	
	@Autowired
	private SubjectInfoMapper subjectInfoMapper;
	
	@Autowired
	private SubjectCaseMapper subjectCaseMapper;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private StuTeacherMappingMapper stuTeacherMappingMapper;

	/**
	 * //TODO 
	 * @auther zxk
	 * @ 2019年5月7日 下午4:00:31
	 */
	public TeacherServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * 
	 * @auther zxk
	 * @2019年5月7日 下午4:12:52
	 * @param result
	 * @param requestPage
	 * @param requestNumber
	 * @return
	 */
	@Override
	public PlClientBaseModel getPage(PlClientBaseModel result, Integer requestPage, Integer requestNumber) {
		// TODO Auto-generated method stub
		List<SubjectInfo> requstList = new ArrayList<SubjectInfo>();//返回要求的数量
			List<SubjectInfo> list = subjectInfoMapper.selectAll();
			ObjectReturnMessage objectReturnMessage = new ObjectReturnMessage();
			if(list.size()<requestNumber){//请求数量大于总数量
				objectReturnMessage = new ObjectReturnMessage(list, EmBusinessError.NUMBER_LESS_THAN_REQUEST.getErrMsg(),1,list.size());
			}else if(requestNumber*requestPage>list.size()){//请求的页数过大
				int temp = 0;//总共多少页
				if(list.size()%requestNumber != 0)
					temp = list.size()/requestNumber+1;
				else
					temp = list.size()/requestNumber;
				if(temp == requestPage){
					//请求的页数与最后一页相等
					for(int i=0;i<list.size()%requestNumber;i++){
						requstList.add(list.get((temp-1)*requestNumber+i));
						objectReturnMessage = new ObjectReturnMessage(requstList,EmBusinessError.REQUEST_PAGE_MORE_BIGGER.getErrMsg(),1,list.size());
					}
				}else{
					//请求的页数不是最后一页,1页返回所有信息
					objectReturnMessage = new ObjectReturnMessage(list,EmBusinessError.REQUEST_PAGE_MORE_BIGGER.getErrMsg(),1,list.size());
				}				
				result.setMessage("success");
			}else{//有数据
				if((list.size()-(requestPage-1)*requestNumber)<requestNumber)//剩下的法规数小于需要的法规数
					for(int i=0;i<(list.size()-(requestPage-1)*requestNumber);i++)
						requstList.add(list.get((requestPage-1)*requestNumber+i));
				else{//剩下的数据大于等于请求数
					int temp = 0;//总共多少页
					for(int i=0;i<requestNumber;i++)
						requstList.add(list.get((requestPage-1)*requestNumber+i));
					if(list.size()%requestNumber != 0)
						temp = list.size()/requestNumber+1;
					else
						temp = list.size()/requestNumber;
					objectReturnMessage = new ObjectReturnMessage(requstList, "查询成功",temp,list.size());
				}
			}
		result.setData(objectReturnMessage);
		return result;
	}

	/* 
	 * @auther zxk
	 * @2019年5月7日 下午4:50:29
	 * @param subjectInfo
	 * @param listCase
	 */
	@Override
	public Integer insert(List<TestDataInfo> testDataInfos,String path) throws IOException {
		// TODO Auto-generated method stub
		int result = -1;
		String className = "";//类名
		ToolsImpl toolsImpl = new ToolsImpl();
		String mainPath = "";
		String mainCode = testDataInfos.get(0).getSubjectInfo().getMainCodeUrl();
		
		List<SubjectCase> listCase = new ArrayList<SubjectCase>();
		SubjectCase subjectCase = new SubjectCase();
		Integer teacherId = testDataInfos.get(0).getSubjectInfo().getTeacherId();
		SubjectInfoExample subjectInfoExample = new SubjectInfoExample();
		subjectInfoExample.or().andSubjectNameEqualTo(testDataInfos.get(0).getSubjectInfo().getSubjectName());
		className = mainCode.split("public class")[1].split("\\{")[0].trim();//类名
		mainPath = path + className+".java";
		testDataInfos.get(0).getSubjectInfo().setMainCodeUrl(mainPath);
		List<SubjectInfo> subjectInfos = subjectInfoMapper.selectByExample(subjectInfoExample);
		
		if(!subjectInfos.isEmpty()){//有老师布置过相同的题目			
			for(int i = 0;i<subjectInfos.size();i++){
				if(subjectInfos.get(i).getTeacherId().compareTo(teacherId) == 0){//如果是同一个老师,则题目名字相同，不能插入
					result = 3;
					break;
				}else{
					result = 1;
				}
			}
		}
		if(result == 1 || result == -1){
			if(subjectInfoMapper.insert(testDataInfos.get(0).getSubjectInfo()) > 0 ){//先插入到subject_info表
				toolsImpl.createFile(path, testDataInfos.get(0).getSubjectInfo().getSubjectName()+".java");
				//将主函数写入txt
				toolsImpl.writeToFile(mainPath,mainCode);
				
				for(int i = 0;i<testDataInfos.size();i++){
					subjectCase.setExpectedValue(testDataInfos.get(i).getExpectedValue());
					subjectCase.setScore(testDataInfos.get(i).getScore());
					subjectCase.setTestCase(testDataInfos.get(i).getTestCase());

					List<SubjectInfo> list = subjectInfoMapper.selectByExample(subjectInfoExample);
					
					subjectCase.setSubjectId(list.get(0).getId());
					listCase.add(subjectCase);
					
					if(subjectCaseMapper.insert(listCase.get(i)) > 0){//插入到subject_case表
						result = 1;//插入成功
						
					}else{
						result = 0;//插入失败
					}
				}
			}else{
				result = 0;//插入失败
			}
		}
		return result;
	}

	/* 
	 * @auther zxk
	 * @2019年5月9日 上午9:32:29
	 * @param teacherId
	 */
	@Override
	public List<UserInfo> getStudentInfo(String teacherId) {
		// TODO Auto-generated method stub
		StuTeacherMappingExample stuTeacherMappingExample = new StuTeacherMappingExample();
		stuTeacherMappingExample.or().andTeacherIdEqualTo(Integer.parseInt(teacherId));
		List<StuTeacherMapping> list = stuTeacherMappingMapper.selectByExample(stuTeacherMappingExample);
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		if(list.isEmpty()){//老师没有任何学生选择
			return null;
		}else{
			for(int i = 0;i<list.size();i++){
				userInfoList.set(i, userInfoMapper.selectByPrimaryKey(list.get(i).getStudentId()));			
			}
			return userInfoList;
		}
		
	}

	/* 
	 * @auther zxk
	 * @2019年5月9日 上午9:57:37
	 * @param teacherId
	 * @return
	 */
	@Override
	public List<SubjectInfo> getSubjectInfo(String teacherId) {
		// TODO Auto-generated method stub
		SubjectInfoExample subjectInfoExample = new SubjectInfoExample();
		subjectInfoExample.or().andTeacherIdEqualTo(Integer.parseInt(teacherId));
		List<SubjectInfo> list = subjectInfoMapper.selectByExampleWithBLOBs(subjectInfoExample);
		return list.isEmpty()? null : list;
	}


}
