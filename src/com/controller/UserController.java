package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.entity.PageSupport;
import com.entity.Provider;
import com.entity.Role;
import com.entity.User;
import com.mysql.jdbc.Constants;
import com.mysql.jdbc.StringUtils;
import com.service.user.UserService;

@Controller
//这句注解的意思就是给这个类添加一个url
@RequestMapping("/user")
public class UserController {
	private Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private UserService userService;
//	@Resource
//	private RoleService roleService;
	
	@RequestMapping("/welcome")//RequestMethod.GET,如果设置了，那么前台就必须以对应的方式来提交。
	public String index(@RequestParam(value="name",required=false) String name){
		System.out.println("hello,SpringMVC");
		return "index";
	}
	@RequestMapping("/select.html")
	public String select(@RequestParam(value="id",required=false)int id,Model model){
		List<User> list = userService.getById(id); 
		model.addAttribute("user", list.get(0));
		return "index";
	}
	@RequestMapping(value="/providerlist.html")
	public String getProviderList(Model model,@RequestParam(value="proCode",required=false)String proCode,
		@RequestParam(value="proName",required=false)String proName,
		@RequestParam(value="pageIndex",required=false)String pageIndex) {
		String proName1 = null;
		List<Provider> providerList = null;
		int pageSize = 5;
		int currentPageNo = 1;
		if(proCode == null) {
			proCode = "";
		}
		if(proName != null && !proName.equals("")) {
			proName1 = proName;
		}
		if (pageIndex==null) {
			pageIndex="1";
		}
		if(pageIndex!=null) {
			try {
				currentPageNo = Integer.valueOf(pageIndex);
			} catch (Exception e) {
				return "redirect:/user/syserror.html";
			}
		}
		int totalCount = userService.getProviderCount(proCode, proName1);
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		if(currentPageNo < 1) {
			currentPageNo = 1;
		}else if(currentPageNo > totalPageCount) {
			currentPageNo = totalPageCount;
		}
		providerList = userService.getProviderList(proCode, proName, (Integer.parseInt(pageIndex)-1)*pageSize, pageSize);
		System.out.println(providerList.size());
		model.addAttribute("getProviderList",providerList);
		//List<Role> roleList = null;
		//roleList =userService.getUserList(queryUserName, _queryUseRole, currentPageNo, pageSize)
		//model.addAttribute("userList",roleList);
		model.addAttribute("proName",proName);
		model.addAttribute("proCode",proCode);
		model.addAttribute("totalPageCount",totalPageCount);
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("currentPageNo",currentPageNo);
		return "providerlist";
	}
	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}
	@RequestMapping(value="/dologin.html",method=RequestMethod.POST)
	public String doLogin(@RequestParam String userCode,@RequestParam String userPassword,HttpSession session,HttpServletRequest request) {
		
		User user = userService.login(userCode,userPassword);
		if(null!=user) {
			session.setAttribute("user", user);
			return "redirect:/user/main.html";
		}else {
			request.setAttribute("error", "用户名或密码不正确");
			return "login";
		}
	}
	@RequestMapping(value="/main.html")
	public String main(HttpSession session) {
		if(session.getAttribute("user") == null) {
			return "redirect:/user/login.html";
		}
		return "frame";
	}
	@RequestMapping(value="/userlist.html")
	public String getUserList(Model model,@RequestParam(value="queryname",required=false)String queryUserName,
			@RequestParam(value="queryUserRole",required=false)String queryUserRole,
			@RequestParam(value="pageIndex",required=false)String pageIndex) {
		logger.info("getUserList ------ > queryUserName:"+queryUserName);
		logger.info("getUserList ------ > queryUserRole:"+queryUserRole);
		logger.info("getUserList ------ > pageIndex:"+pageIndex);
		int _queryUseRole = 0;
		List<User> userList = null;
		int pageSize = 5;
		int currentPageNo = 1;
		if(queryUserName == null) {
			queryUserName = "";
		}
		if(queryUserRole != null && !queryUserRole.equals("")) {
			_queryUseRole = Integer.getInteger(queryUserRole);
		}
		if (pageIndex==null) {
			pageIndex="1";
		}
		if(pageIndex!=null) {
			try {
				currentPageNo = Integer.valueOf(pageIndex);
			} catch (Exception e) {
				return "redirect:/user/syserror.html";
			}
		}
		int totalCount = userService.getUserCount(queryUserName,_queryUseRole);
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		if(currentPageNo < 1) {
			currentPageNo = 1;
		}else if(currentPageNo > totalPageCount) {
			currentPageNo = totalPageCount;
		}
		userList = userService.getUserList(queryUserName,_queryUseRole,(Integer.parseInt(pageIndex)-1)*pageSize,pageSize);
		System.out.println(userList.size());
		model.addAttribute("userList",userList);
		//List<Role> roleList = null;
		//roleList =userService.getUserList(queryUserName, _queryUseRole, currentPageNo, pageSize)
		//model.addAttribute("userList",roleList);
		model.addAttribute("queryUserName",queryUserName);
		model.addAttribute("queryUserRole",queryUserRole);
		model.addAttribute("totalPageCount",totalPageCount);
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("currentPageNo",currentPageNo);
		return "userlist";
	}
	@RequestMapping(value="/pwdmodify.html")
	public String pwdmodify() {
		return "pwdmodify";
	}
	@RequestMapping(value="/pwdmodify",method=RequestMethod.GET)
	@ResponseBody
	public Object pwdIsExit(@RequestParam String oldpassword,HttpSession session) {
		HashMap<String, String> resultMap = new HashMap<String,String>();
		if(StringUtils.isNullOrEmpty(oldpassword)) {
			resultMap.put("result", "error");
		}else {
			User user2=((User)session.getAttribute("user"));
			int id=user2.getId();
			User user = userService.selectUserPwd(id,oldpassword);
			if(user2.getUserPassword().equals(oldpassword)) {
				System.out.println(user2.getUserPassword());
				resultMap.put("result", "false");
			}else if(id==0){
				resultMap.put("result","sessionerror");
			}else{
				resultMap.put("result", "true");
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
}
