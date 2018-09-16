package cn.smbms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		System.out.println("LoginHandlerInterceptor....preHandle");
		String url = request.getRequestURI();
		System.out.println(url);
		if(url.indexOf("login.action")>=0){
			return true;
		}		
		// 从request中获取session
		HttpSession session = request.getSession();
		// 从session中获取username
		Object user = session.getAttribute("userSession");
		// 判断username是否为null
		if (user != null) {
			// 如果不为空则放行
			return true;
		} else {
			// 如果为空则跳转到登录页面
			System.out.println("为空则跳转到登录页面");
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}

		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("LoginHandlerInterceptor....postHandle");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("LoginHandlerInterceptor....afterCompletion");
		// TODO Auto-generated method stub
		
	}

}
