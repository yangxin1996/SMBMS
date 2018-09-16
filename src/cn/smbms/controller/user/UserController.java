package cn.smbms.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StringUtils;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="add")
	public  String add(User user) throws Exception {	
		user.setCreationDate(new Date());		
		if(userService.add(user)){
			return "redirect:/user/query.action";
		}else{
			return "useradd";
		}
	}		
	
	@RequestMapping(value="query")
	public String query(Model model,@ModelAttribute(value="queryname") String queryUserName,@ModelAttribute(value="queryUserRole") String temp,String pageIndex) throws Exception {	
		//查询用户列表
		int queryUserRole = 0;	
		List<User> userList = null;
		//设置页面容量
    	int pageSize = Constants.pageSize;
    	//当前页码
    	int currentPageNo = 1;
		if(queryUserName == null){
			queryUserName = "";
		}
		if(temp != null && !temp.equals("")){
			queryUserRole = Integer.parseInt(temp);
		}		
    	if(pageIndex != null){
    		try{
    			currentPageNo = Integer.valueOf(pageIndex);
    		}catch(NumberFormatException e){
    			return "error";
    		}
    	}	
    	//总数量（表）	
    	int totalCount	= userService.getUserCount(queryUserName,queryUserRole);
    	//总页数
    	PageSupport pages=new PageSupport();
    	pages.setCurrentPageNo(currentPageNo);
    	pages.setPageSize(pageSize);
    	pages.setTotalCount(totalCount);   	
    	int totalPageCount = pages.getTotalPageCount();    	
    	//控制首页和尾页
    	if(currentPageNo < 1){
    		currentPageNo = 1;
    	}else if(currentPageNo > totalPageCount){
    		currentPageNo = totalPageCount;
    	}	
		userList = userService.getUserList(queryUserName,queryUserRole,currentPageNo, pageSize);
		model.addAttribute("userList", userList);
		List<Role> roleList = null;		
		roleList = roleService.getRoleList();
		model.addAttribute("roleList", roleList);
		model.addAttribute("queryUserName", queryUserName);
		model.addAttribute("queryUserRole", queryUserRole);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentPageNo", currentPageNo);
		return "userlist";
	}
	@RequestMapping(value="getrolelist",produces="application/json")
	@ResponseBody
	public  List<Role> getRoleList() throws Exception {	
		List<Role> roleList = null;
		roleList = roleService.getRoleList();
		return roleList;
	}
	@RequestMapping(value="ucexist",produces="application/json")
	@ResponseBody
	public HashMap<String, String> userCodeExist(@RequestBody @RequestParam(value="userCode",required = false) String userCode) throws Exception {	
		//判断用户账号是否可用	
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(userCode)){
			resultMap.put("userCode", "exist");
		}else{
			User user = userService.selectUserCodeExist(userCode);
			if(null != user){
				resultMap.put("userCode","exist");
			}else{
				resultMap.put("userCode", "notexist");
			}
		}		
		return resultMap;
	}	
	@RequestMapping(value="deluser",produces="application/json")
	@ResponseBody
	public HashMap<String, String> delUser(@RequestBody @RequestParam(value="uid",required = false) String id) throws Exception {	
		Integer delId = 0;
		try{
			delId = Integer.parseInt(id);
		}catch (Exception e) {
			delId = 0;
		}
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(delId <= 0){
			resultMap.put("delResult", "notexist");
		}else{
			if(userService.deleteUserById(delId)){
				resultMap.put("delResult", "true");
			}else{
				resultMap.put("delResult", "false");
			}
		}	
		return resultMap;
	}	
	@RequestMapping(value="view")
	public String getUserByIdView(Model model,@RequestParam(value="uid",required = false) String id) throws Exception {	
		if(!StringUtils.isNullOrEmpty(id)){
			//调用后台方法得到user对象
			User user = userService.getUserById(id);			
			model.addAttribute("user", user);
			return "userview";
		}
		return null;
	}
	@RequestMapping(value="modify")
	public String getUserByIdModify(Model model,@RequestParam(value="uid",required = false) String id) throws Exception {	
		if(!StringUtils.isNullOrEmpty(id)){
			//调用后台方法得到user对象
			User user = userService.getUserById(id);			
			model.addAttribute("user", user);
			return "usermodify";
		}
		return null;
	}	
	@RequestMapping(value="modifyexe")
	public String modify(User user) throws Exception {	
		user.setModifyDate(new Date());
		if(userService.modify(user)){
			return "redirect:/user/query.action";
		}else{
			return "usermodify";
		}
	}	
	
	@RequestMapping(value="pwdmodify",produces="application/json")
	@ResponseBody
	public Map<String, String> getPwdByUserId(HttpServletRequest request,String oldpassword) throws Exception {	
		Object o = request.getSession().getAttribute(Constants.USER_SESSION);
		Map<String, String> resultMap = new HashMap<String, String>();
		if(null == o ){//session过期
			resultMap.put("result", "sessionerror");
		}else if(StringUtils.isNullOrEmpty(oldpassword)){//旧密码输入为空
			resultMap.put("result", "error");
		}else{
			String sessionPwd = ((User)o).getUserPassword();
			if(oldpassword.equals(sessionPwd)){
				resultMap.put("result", "true");
			}else{//旧密码输入不正确
				resultMap.put("result", "false");
			}
		}
		return resultMap;
	}
	@RequestMapping(value="savepwd")
	public String updatePwd(HttpServletRequest request,Model model,String newpassword) throws Exception {	
		boolean flag = false;
		Object o = request.getSession().getAttribute(Constants.USER_SESSION);
		if(o != null && !StringUtils.isNullOrEmpty(newpassword)){
			flag = userService.updatePwd(((User)o).getId(),newpassword);
			if(flag){
				model.addAttribute(Constants.SYS_MESSAGE, "修改密码成功,请退出并使用新密码重新登录！");
				request.getSession().removeAttribute(Constants.USER_SESSION);//session注销
			}else{
				model.addAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
			}
		}else{
			model.addAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
		}
		return "pwdmodify";
	}
}
