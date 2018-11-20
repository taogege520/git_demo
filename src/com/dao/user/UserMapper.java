package com.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.entity.Provider;
import com.entity.Role;
import com.entity.User;

public interface UserMapper {
	/**
	 * 根据id查询用户信息
	 * @param id
	 * @return
	 */
	public List<User> getById(@Param("id")int id);
	
	public User selectNamepwd(@Param("userCode")String userCode,@Param("userPassword")String userPassword);
	
	public List<User> getUserList(@Param("queryUserName")String queryUserName,@Param("_queryUseRole") int _queryUseRole,@Param("currentPageNo") int currentPageNo, @Param("pageSize")int pageSize);
	
	public int getUserCount(@Param("queryUserName")String queryUserName,@Param("_queryUseRole") int _queryUseRole);
	
	public List<Provider> getProviderList(@Param("proCode")String proCode,@Param("proName")String proName,@Param("currentPageNo") int currentPageNo, @Param("pageSize")int pageSize);
	
	public int getProviderCount(@Param("proCode")String proCode,@Param("proName") String proName);
	
	public List<Role> getRoleList();
	
	public boolean add(Provider provider);
	
	public Provider getProviderById(@Param("id")String id);
	
	public int modifiyProviderSave(@Param("id")int id);
	
	public boolean modifiyProviderSave(Provider provider);
	
	public User selectUserPwd(@Param("id")int id,@Param("userPassword")String userPassword);
	public int pwdmodify(@Param("id")int id,@Param("userPassword")String oldpassword);
}
