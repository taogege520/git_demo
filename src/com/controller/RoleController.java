package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.entity.Provider;
import com.entity.Role;
import com.entity.User;
import com.mysql.jdbc.Constants;
import com.service.user.UserService;

@Controller
@RequestMapping("/Role")
public class RoleController {
	@Resource
	private UserService userService;
	
	@RequestMapping(value="/rolelist.html",method=RequestMethod.GET)
	public String add(Model model) {
		List<Role> roleList=new ArrayList<Role>();
		roleList=userService.getRoleList();
		model.addAttribute("roleList",roleList);
		return "rolelist";
	}
	@RequestMapping(value="/provideradd.html",method=RequestMethod.GET)
	public String insert(@ModelAttribute("provider") Provider provider) {
		return "provideradd";
	}
	@RequestMapping(value="/provideradd.html",method=RequestMethod.POST)
	public String insert(@ModelAttribute("provider")Provider provider,HttpSession session) {
		provider.setCreateBy(((User)session.getAttribute("user")).getId());
		provider.setCreationDate(new Date());
		if(userService.add(provider)) {
			return "redirect:/Role/providerlist.html";
		}
		return "provideradd";
	}
	
	@RequestMapping(value="/providerlist.html")
	public String providerlist() {
		return "providerlist";
	}
	@RequestMapping(value="/view/{id}",method=RequestMethod.GET)
	public String view(@PathVariable String id,Model model) {
		Provider provider = userService.getProviderById(id);
		model.addAttribute(provider);
		return "providerview";
		
	}
	@RequestMapping(value="providermodify/{pid}",method=RequestMethod.GET)
	public String getProviderById(@PathVariable String pid,Model model) {
		Provider provider = userService.getProviderById(pid);
		model.addAttribute(provider);
		return "providermodify";
		
	}
	@RequestMapping(value="/providermodifysave.html",method=RequestMethod.POST)
	public String modifyProviderSave(Provider provider,HttpSession session,int id) {
		//provider.setModifyBy(((Provider)session.getAttribute("user")).getId());
		provider.setModifyDate(new Date());
		System.out.println(provider.getProAddress());
		if(userService.modifiyProviderSave(provider)) {
			return "redirect:/user/providerlist.html";
		}
		return "providermodify";
	}
}
