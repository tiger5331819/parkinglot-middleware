package com.parkinglotmiddleware.universal.extension;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest拓展
 *
 * @author Suhuyuan
 */
@Component
public class HttpServletRequestExtension implements IExtensionFuction {

    @Override
    public void setToken() {
        HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        httpRequest.setAttribute("token", true);
    }

    @Override
    public boolean getToken() {
        HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return (boolean) httpRequest.getAttribute("token");
    }

    @Override
    public void removeToken() {
        HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        httpRequest.removeAttribute("token");
    }
}
