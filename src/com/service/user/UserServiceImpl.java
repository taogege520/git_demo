package com.service.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.user.UserMapper;
import com.entity.Provider;
import com.entity.Role;
import com.entity.User;
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserMapper userMapper;

	@Override
	public List<User> getById(int id) {
		List<User> list = new ArrayList<User>();
		try {
			list = userMapper.getById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public User login(String userCode, String userPassword) {
		User user=null;
		try {
			user=userMapper.selectNamepwd(userCode, userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public int getUserCount(String queryUserName, int _queryUseRole) {
		int num = 0;
		try {
			num = userMapper.getUserCount(queryUserName, _queryUseRole);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public List<User> getUserList(String queryUserName, int _queryUseRole, int currentPageNo, int pageSize) {
		List<User> users = new ArrayList<User>();
		try {
			users = userMapper.getUserList(queryUserName, _queryUseRole, currentPageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public List<Provider> getProviderList(String proCode, String proName, int currentPageNo, int pageSize) {
		List<Provider> providers = new ArrayList<Provider>();
		try {
			providers = userMapper.getProviderList(proCode, proName, currentPageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return providers;
	}
	@Override
	public int getProviderCount(String proCode, String proName) {
		int num = 0;
		try {
			num = userMapper.getProviderCount(proCode, proName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public List<Role> getRoleList() {
		List<Role> list = new ArrayList<Role>();
		try {
			list = userMapper.getRoleList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean add(Provider provider) {
		boolean num = false;
		try {
			num = userMapper.add(provider);;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public Provider getProviderById(String id) {
		Provider provider = null;
		try {
			provider = userMapper.getProviderById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return provider;
	}

	@Override
	public boolean modifiyProviderSave(Provider provider) {
		boolean num = false;
		try {
			num = userMapper.modifiyProviderSave(provider);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public User selectUserPwd(int id,String userPassword) {
		User user = null;
		try {
			user = userMapper.selectUserPwd(id,userPassword);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public int modify(int id, String oldpassword) {
		// TODO Auto-generated method stub
		return userMapper.pwdmodify(id, oldpassword);
	}
}
