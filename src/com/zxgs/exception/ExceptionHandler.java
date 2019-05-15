package com.zxgs.exception;

import com.google.gson.Gson;
import com.zxgs.common.SysCodeMsg;
import com.zxgs.util.ReturnData;

import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常捕获
 *
 * @ClassName: ExceptionHandler
 */

public class ExceptionHandler implements HandlerExceptionResolver {
    private static final Logger LOG = Logger.getLogger(ExceptionHandler.class);

    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ex", ex);
        SysCodeMsg codeMessage;
        // 根据不同错误
        if (ex instanceof NullPointerException) { //空值异常
            codeMessage = SysCodeMsg.NULL_POINTER_EXCEPTION;
        } else if (ex instanceof ClassCastException) { // 数据类型转换异常
            codeMessage = SysCodeMsg.CLASS_CAST_EXCEPTION;
        } else if (ex instanceof IOException) { // IO流异常
            codeMessage = SysCodeMsg.IO_EXCEPTION;
        } else if (ex instanceof NoSuchMethodException) { // 未知方法异常
            codeMessage = SysCodeMsg.NO_SUCH_METHOD_EXCEPTION;
        } else if (ex instanceof IndexOutOfBoundsException) { // 数组越界异常
            codeMessage = SysCodeMsg.INDEX_OUT_OF_BOUNDS_EXCEPTION;
        } else if (ex instanceof SocketException) { // 网络异常
            codeMessage = SysCodeMsg.SOCKET_EXCEPTION;
        } else if (ex instanceof ConnectException) {// 连接异常
            codeMessage = SysCodeMsg.CONNECT_EXCEPTION;
        } else if (ex instanceof SQLException) { // 非运行异常
            codeMessage = SysCodeMsg.SQL_EXCEPTION;
        } else if (ex instanceof ServletRequestBindingException) {
            codeMessage = SysCodeMsg.PARAM_IS_NULL;
        } else if (ex instanceof RuntimeException) { // 运行异常
            codeMessage = SysCodeMsg.RUNTIME_EXCEPTION;
        } else {// 错误
            codeMessage = SysCodeMsg.FAIL;
        }
        LOG.error(codeMessage.getDescribe(), ex);
        try {
            ReturnData redata = new ReturnData(codeMessage.getCode(),
                    codeMessage.getDescribe());
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(redata));
            out.flush();
            return null;
        } catch (IOException e) {
            return new ModelAndView("error/error", model);
        }
    }
}
