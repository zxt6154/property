package com.ziroom.suzaku.main.exception;

import cn.hutool.core.map.MapUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.ziroom.suzaku.base.base.BaseException;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.utils.UUIDUtil;
import com.ziroom.tech.enums.ResponseEnum;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author libingsi
 * @date 2021/6/23 9:22
 */
@RestControllerAdvice
@ConditionalOnProperty(prefix="suzaku.exception", name={"enable"}, matchIfMissing=true)
@Order
@Slf4j
public class SuzakuExceptionHandlerAdvice {

    @Value("${spring.profiles.active:}")
    private String profile;
    private static String PROFILE_PROD = "prod";

    private static final Log logger = LogFactory.get();


    @ExceptionHandler({BaseException.class})
    @ResponseStatus(HttpStatus.OK)
    public Object processCustomException(BaseException e)
    {
        logger.error(e, e.getMessage(), new Object[0]);
        return JsonResult.error(e.getCode(), e.getMsg());
    }

    @ExceptionHandler({ServletRequestBindingException.class})
    @ResponseStatus(HttpStatus.OK)
    public Object processCustomException(ServletRequestBindingException e)
    {
        logger.error(e, e.getMessage(), new Object[0]);
        return JsonResult.error(500, "请求参数有误" + e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public Object processValidateException(MethodArgumentNotValidException e)
    {
        StringBuilder errorMsg = new StringBuilder();
        Iterator var3 = e.getBindingResult().getAllErrors().iterator();
        while (var3.hasNext())
        {
            ObjectError oe = (ObjectError)var3.next();
            errorMsg.append(oe.getDefaultMessage()).append(";");
        }
        logger.error(e, errorMsg.toString(), new Object[0]);
        return JsonResult.error(500, errorMsg.toString());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.OK)
    public Object processCustomException(HttpMessageNotReadableException e)
    {
        logger.error(e, e.getMessage(), new Object[0]);
        return JsonResult.error(500, "请求参数有误");
    }

    @ExceptionHandler({SQLException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object sqlexception(Exception e, HttpServletRequest req)
    {
        String errorId = UUIDUtil.getId();
        reportError(req, e, errorId);
        return JsonResult.error(500, "系统开小差了，请联系管理员");
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public Object exception(Exception e, HttpServletRequest req)
    {
        String errorId = UUIDUtil.getId();
        reportError(req, e, errorId);
        if ((StringUtils.isBlank(this.profile)) || (PROFILE_PROD.equalsIgnoreCase(this.profile))) {
            return JsonResult.error(500, "系统开小差了，请联系管理员");
        }
        return JsonResult.error(500, "系统开小差了，请联系管理员");
    }

    @ExceptionHandler(SuzakuBussinesException.class)
    public ModelResponse handleParamCheckException(SuzakuBussinesException e) {
        log.error(String.format("system is error , message is {}", e.getMessage()), e);
        return ModelResponseUtil.error(e.getCode(), e.getErrorMessage());
    }

    private void reportError(HttpServletRequest request, Exception e, String errorId)
    {
        Map<String, Object> informParam = MapUtil.of("errorId", errorId);
        informParam.put("url", request.getRequestURL().toString());
        informParam.put("method", request.getMethod());
        logger.error(e, "{} - url {}, method {}", new Object[] { errorId, request.getRequestURL().toString(), request.getMethod() });
    }
}
