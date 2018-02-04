package com.liuyitao.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.cache.LoadingCache;
import com.liuyitao.bean.JsonResult;
import com.liuyitao.bean.SystemException;
import com.liuyitao.bean.UserSessionBean;
import com.liuyitao.bean.enums.errorCode.ParameterErrorCode;
import com.liuyitao.bean.enums.errorCode.PermissionErrorCode;
import com.liuyitao.bean.enums.errorCode.UnknownErrorCode;
import com.liuyitao.bean.enums.errorCode.WebErrorCode;
import com.liuyitao.common.CacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import static com.liuyitao.common.CacheUtil.addRequestRecord;
import static com.liuyitao.common.CacheUtil.deleteSession;
import static com.liuyitao.common.CacheUtil.getSession;

/***
 *@Author: liuyitao
 *@CreateDate:10:25 PM 1/31/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/

@Configuration
public class MyWebConfigAdapter extends WebMvcConfigurerAdapter {

    private static Logger logger= LoggerFactory.getLogger(MyWebConfigAdapter.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("**").allowedOrigins("**").allowedMethods("**").allowedHeaders("*");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter=new FastJsonHttpMessageConverter();
        FastJsonConfig config=new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        config.setCharset(Charset.forName("UTF-8"));
        config.setSerializerFeatures(SerializerFeature.IgnoreNonFieldGetter, SerializerFeature.NotWriteDefaultValue);
        fastJsonHttpMessageConverter.setFastJsonConfig(config);
        fastJsonHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(fastJsonHttpMessageConverter);

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //检查用户是不是短时间内访问了很多次，防止恶意访问
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
                addRequestRecord(getIpAddress(request));
                return true;
            }
        });


        //安全校验：1.用户是否登陆 2.用户的网络环境（IP地址）是否改变
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                if(!request.getRequestURI().endsWith("pass/login"))
                {
                    String username= request.getParameter("username");
                    if(StringUtils.isEmpty(username))
                    {
                        throw new SystemException("you should specify a username logined",ParameterErrorCode.PARAMETER_ERROR_CODE);
                    }else{
                        final UserSessionBean session = getSession(username);
                        if(session==null)
                        {
                            throw new SystemException(String.format("%s,login first please",username),PermissionErrorCode.NotLoginError);
                        }else if(!session.getUserUuid().equals( CacheUtil.getUserSessionUuid(getIpAddress(request),username)))
                        {
                            deleteSession(username);//网络环境改变的话，让用户重新登陆
                            throw new SystemException(String.format( "dear %s,your network enviroment has changed,please login again",username),PermissionErrorCode.EnvirementChanged);
                        }
                    }
                }else{
                    logger.debug(String.format("来自%s的用户执行登陆，参数为%s",getIpAddress(request),request.getQueryString()));
                }

                return true;
            }
        });

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add((request, response, handler, ex) -> {
            JsonResult result=new JsonResult();
            if(ex instanceof SystemException)
            {
                result.setCode(((SystemException) ex).getErrorCode().getCode()+"").setMsg(ex.getMessage());
            }else if(ex instanceof NoHandlerFoundException)
            {
                result.setCode(WebErrorCode.NotFoundError.getCode()+"").setMsg(String.format("api %s is not found",request.getRequestURI()));
            }else if(ex instanceof HttpMessageNotReadableException||ex instanceof HttpMessageConversionException)
            {
                result.setCode(ParameterErrorCode.PARAMETER_ERROR_CODE.getCode()+"").setMsg(String.format( "parameter you upload [%s] is invalid.",getJsonparamFromRequest(request))+JSON.toJSONString(request.getParameterMap()));
            }else if(ex instanceof ServletException)
            {
                result.setCode(WebErrorCode.InnerSystemError.getCode()+"").setMsg(ex.getMessage());
            }
            else{
                result.setCode(UnknownErrorCode.UNKNOWN_ERROR_CODE.getCode()+"").setMsg(String.format("unknown error occourded with api[%s],please check log",request.getRequestURI()));

                String logMessage;
                if(handler instanceof HandlerMethod)
                {
                    logMessage=String.format("api [%s] has error,method:%s.%s,exception message:%s",
                            request.getRequestURI(),
                            ((HandlerMethod) handler).getBean().getClass().getName(),
                            ((HandlerMethod) handler).getMethod().getName(),
                            ex.getMessage());
                }else{
                    logMessage=ex.getMessage();
                }
                logger.error(logMessage,ex);

            }

            responseResult(response,result);

            return new ModelAndView();
        });
    }

    private void responseResult(HttpServletResponse response, JsonResult jsonResult)
    {
        response.setHeader("Content-type","application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);

        try {
            response.getWriter().write(JSON.toJSONString(jsonResult));
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

    }

    private String getJsonparamFromRequest(HttpServletRequest request) {
        BufferedReader bufferedReader;
        StringBuilder sb=new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader( request.getInputStream(),"UTF-8"));

            String str;
            while((str=bufferedReader.readLine())!=null)
            {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }

}
