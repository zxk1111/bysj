package com.zxgs.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.zxgs.js.JSBaseParameter;
import com.zxgs.web.CustomDateEditor;

public abstract class BaseController<E extends JSBaseParameter>
{
  public static final String CMD_EDIT = "edit";
  public static final String CMD_NEW = "new";
  public static final String CMD_VIEW = "view";
  public static final String MODEL = "model";
  protected String idField;
  protected String statusField;
  protected static final String separator = "/";
  protected static ObjectMapper mapper = new ObjectMapper();

  protected static JsonFactory cqzhsqpro = mapper.getJsonFactory();

 
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(Date.class, new CustomDateEditor());
  }

  protected void writeJSON(HttpServletResponse response, String json) throws IOException {
    response.setContentType("text/html;charset=utf-8");
    response.getWriter().write(json);
  }
  
  protected void writeJSON(HttpServletResponse response, Object obj) throws IOException {
    response.setContentType("text/html;charset=utf-8");
    JsonGenerator responseJsonGenerator = cqzhsqpro.createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
    responseJsonGenerator.writeObject(obj);
  }
  

  protected void beforeSaveNew(E example)
  {
  }

  protected void beforeSaveUpdate(E example)
  {
  }

  protected void beforeList(E example)
  {
  }
}