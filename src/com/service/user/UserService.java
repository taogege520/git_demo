package com.service.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Provider;
import com.entity.Role;
import com.entity.User;

public interface UserService {
	public List<User> getById(int id);

	public User login(String userCode, String userPassword);

	public List<User> getUserList(@Param("queryUserName")String queryUserName,@Param("_queryUseRole") int _queryUseRole,@Param("currentPageNo") int currentPageNo, @Param("pageSize")int pageSize);
	
	public int getUserCount(@Param("queryUserName")String queryUserName,@Param("_queryUseRole") int _queryUseRole);
	
	public List<Provider> getProviderList(@Param("proCode")String proCode,@Param("proName") String proName,@Param("currentPageNo") int currentPageNo, @Param("pageSize")int pageSize);
	
	public int getProviderCount(@Param("proCode")String proCode,@Param("proName") String proName);
	
	public List<Role> getRoleList();
	
	public boolean add(Provider provider);
	
	public Provider getProviderById(String id);
	
	public boolean modifiyProviderSave(Provider provider);
	
	public User selectUserPwd(int id,String userPassword);
	
	public int modify(int id,String oldpassword);

}
