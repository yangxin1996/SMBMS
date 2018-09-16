package cn.smbms.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;

import cn.smbms.pojo.User;

@MapperScan
public interface UserDao {
	/**
	 * 增加用户信息
	 * @param connection
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Insert("insert into smbms_user "
			+ "(userCode,userName,userPassword,userRole,gender,"
			+ "birthday,phone,address,creationDate,createdBy) "
			+ "values(#{userCode},#{userName},#{userPassword},#{userRole},"
			+ "#{gender},#{birthday},#{phone},#{address},#{creationDate},#{createdBy})")
	public int add(User user)throws Exception;

	/**
	 * 通过userCode获取User
	 * @param connection
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	@Select("select * from smbms_user where userCode=#{userCode}")
	public User getLoginUser(String userCode)throws Exception;

	/**
	 * 通过条件查询-userList
	 * @param connection
	 * @param userName
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserList(@Param("userName")String userName,@Param("userRole")int userRole,@Param("currentPageNo")int currentPageNo,@Param("pageSize")int pageSize)throws Exception;
	/**
	 * 通过条件查询-用户表记录数
	 * @param connection
	 * @param userName
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	public int getUserCount(@Param("userName")String userName,@Param("userRole")int userRole)throws Exception;
	
	/**
	 * 通过userId删除user
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	@Delete("delete from smbms_user where id=#{delId}")
	public int deleteUserById(Integer delId)throws Exception; 
	
	
	/**
	 * 通过userId获取user
	 * @param connection
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Select("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.id=#{id} and u.userRole = r.id")
	public User getUserById(Integer id)throws Exception; 
	
	/**
	 * 修改用户信息
	 * @param connection
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Update("update smbms_user set "
			+ "userName=#{userName},gender=#{gender},birthday=#{birthday},phone=#{phone},"
			+ "address=#{address},userRole=#{userRole},modifyBy=#{modifyBy},modifyDate=#{modifyDate} "
			+ "where id = #{id} ")
	public int modify(User user)throws Exception;
	
	
	/**
	 * 修改当前用户密码
	 * @param connection
	 * @param id
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	@Update("update smbms_user set userPassword= #{userPassword} where id = #{id}")
	public int updatePwd(@Param("id")int id,@Param("userPassword")String pwd)throws Exception;
	
	
}
