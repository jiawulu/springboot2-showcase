package com.wuzhong.webapp;

import com.alibaba.fastjson.JSON;
import com.wuzhong.commons.result.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestReturnValueHandlerConfigurer implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {

        List<HandlerMethodReturnValueHandler> list = handlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> newList = new ArrayList<>();

        if (null != list) {
            for (HandlerMethodReturnValueHandler valueHandler : list) {
                if (valueHandler instanceof RequestResponseBodyMethodProcessor) {
                    HandlerMethodReturnValueHandlerProxy proxy = new HandlerMethodReturnValueHandlerProxy(valueHandler);
                    newList.add(proxy);
                } else {
                    newList.add(valueHandler);
                }
            }
        }

        handlerAdapter.setReturnValueHandlers(newList);
    }

    public static class HandlerMethodReturnValueHandlerProxy implements HandlerMethodReturnValueHandler {

        private HandlerMethodReturnValueHandler proxyObject;

        public HandlerMethodReturnValueHandlerProxy(HandlerMethodReturnValueHandler proxyObject) {
            this.proxyObject = proxyObject;
        }

        @Override
        public boolean supportsReturnType(MethodParameter returnType) {
            return proxyObject.supportsReturnType(returnType);
        }

        @Override
        public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                      NativeWebRequest webRequest) throws Exception {

            if (null == returnValue){
                proxyObject.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
                return;
            }

            String output = null;
            if (returnValue instanceof Result) {
                output = JSON.toJSONString(returnValue);
//            } else if (returnValue instanceof CharSequence){
//                output = returnValue.toString();
            } else {
                output = Result.ok(returnValue).toJSONString();
            }
            proxyObject.handleReturnValue(output, returnType, mavContainer, webRequest);
        }

    }

}