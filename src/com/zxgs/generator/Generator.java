package com.zxgs.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Generator {

	/**
	 * @param args
	 * @throws Exception
	 * @throws IOException
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
//		String url = Generator.class.getResource("/").getPath().toString();
		try{
			//String path = url + "com/ljxy/Gnerator/Generator.xml";
			String path = "D:\\base\\zhsq_cms\\src\\com\\zxgs\\generator\\Generator.xml";
			//System.out.println(url);
			File configFile = new File(path);
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(configFile);
		      
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
					callback, warnings);
			myBatisGenerator.generate(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}

}
