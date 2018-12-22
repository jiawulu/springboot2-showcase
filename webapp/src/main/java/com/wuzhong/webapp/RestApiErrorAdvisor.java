package com.wuzhong.webapp;

import com.wuzhong.commons.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = {"com.wuzhong"})
public class RestApiErrorAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(RestApiErrorAdvisor.class);

    @Value("${wz.web.debug:true}")
    private boolean debug;

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    ResponseEntity<?> handleRestException(HttpServletRequest request, Throwable ex) {

        String message = "InternalError";
        if (debug) {
            logger.error("Uncaught exception for request {}", requestToString(request), ex);
            StringBuffer sb = new StringBuffer();
            sb.append(ex.toString()).append("\n").append(ex.getMessage());
            message = sb.toString();
        }
        int code = 500;
        return new ResponseEntity<>(Result.err(code, message),
                HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private String requestToString(HttpServletRequest request) {
        if (request == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();

        sb.append("Request method: ");
        sb.append(request.getMethod());
        sb.append(' ');

        sb.append("Request path: ");
        sb.append(request.getRequestURI());
        sb.append(' ');

        return sb.toString();
    }

}
