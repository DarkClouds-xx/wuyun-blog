package com.wuyun.interceptor;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuyun.utils.PageUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.wuyun.constant.PageConstant.*;


/**
 * 分页拦截器
 *
 * @author DarkClouds
 * @date 2023/05/10
 */
public class PageableInterceptor implements HandlerInterceptor {

    /**
     * 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        //获取当前请求的页码
        String currentPage = request.getParameter(CURRENT);
        ///获取当前请求的分页条数
        String pageSize = Optional.ofNullable(request.getParameter(SIZE)).orElse(DEFAULT_SIZE);
        //如果获取到了页码，则开启分页线程
        if (StringUtils.hasText(currentPage)) {
            //开启线程
            PageUtils.setCurrentPage(new Page<>(Long.parseLong(currentPage), Long.parseLong(pageSize)));
        }
        return true;
    }


    /**
     * 在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理程序
     * @param ex       前女友
     */
    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
        //调用完成销毁此线程
        PageUtils.remove();
    }

}