package com.yfkyplatform.parkinglotmiddleware.configuration.web;

import com.yfkyplatform.parkinglotmiddleware.universal.extension.IExtensionFuction;

import javax.servlet.*;
import java.io.IOException;

/**
 * 业务系统请求拦截器
 *
 * @author Suhuyuan
 */

public class TestBoxRequestFilter implements Filter {

    private final IExtensionFuction extensionFuction;

    public TestBoxRequestFilter(IExtensionFuction extensionFuction) {
        this.extensionFuction = extensionFuction;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        extensionFuction.setToken();
        filterChain.doFilter(servletRequest, servletResponse);
        extensionFuction.removeToken();
    }
}
