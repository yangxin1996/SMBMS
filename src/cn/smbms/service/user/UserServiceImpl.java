package cn.smbms.service.user;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.smbms.dao.user.UserDao;
import cn.smbms.pojo.User;

/**
 * service层捕获异常，进行事务处理
 * 事务处理：调用不同dao的多个方法，必须使用同一个connection（connection作为参数传递）
 * 事务完成之后，需要在service层进行connection的关闭，在dao层关闭（PreparedStatement和ResultSet对象）
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;	
	
	@Override
	public boolean add(User user)  throws Exception {
		boolean flag = false;
		int updateRows = userDao.add(user);
		if(updateRows > 0){
			flag = true;
			System.out.println("add success!");
		}else{
			System.out.println("add failed!");
		}
		return flag;
	}
	@Override
	public User login(String userCode, String userPassword)  throws Exception {
		User user = null;
		user = userDao.getLoginUser(userCode);	
		//匹配密码
		if(null != user){
			if(!user.getUserPassword().equals(userPassword))
				user = null;
		}	
		return user;
	}
	@Override
	public List<User> getUserList(String queryUserName,int queryUserRole,int currentPageNo, int pageSize)  throws Exception {
		List<User> userList = null;
		userList = userDao.getUserList(queryUserName,queryUserRole,(currentPageNo-1)*pageSize,pageSize);
		return userList;
	}
	@Override
	public User selectUserCodeExist(String userCode)  throws Exception {
		User user = null;
		user = userDao.getLoginUser(userCode);
		return user;
	}
	@Override
	public boolean deleteUserById(Integer delId)  throws Exception {
		boolean flag = false;
		if(userDao.deleteUserById(delId) > 0)
			flag = true;
		return flag;
	}
	@Override
	public User getUserById(String id)  throws Exception {
		User user = null;	
		if(id!=null&&!"".equals(id))
			user = userDao.getUserById(Integer.valueOf(id));	
		return user;
	}
	@Override
	public boolean modify(User user)  throws Exception {
		boolean flag = false;
		if(userDao.modify(user) > 0)
			flag = true;
		return flag;
	}
	@Override
	public boolean updatePwd(int id, String pwd)  throws Exception {
		boolean flag = false;		
		if(userDao.updatePwd(id,pwd) > 0)
			flag = true;
		return flag;
	}
	@Override
	public int getUserCount(String queryUserName, int queryUserRole)  throws Exception {
		int count = 0;
		count = userDao.getUserCount(queryUserName,queryUserRole);
		return count;
	}
	
}
