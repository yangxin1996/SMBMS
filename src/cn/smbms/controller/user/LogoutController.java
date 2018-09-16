package cn.smbms.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.smbms.tools.Constants;

@Controller
public class LogoutController {
	
	@RequestMapping(value="logout")
	public String logout(HttpServletRequest request) throws Exception {	
		request.getSession().removeAttribute(Constants.USER_SESSION);
		return "../login";
	}
}
