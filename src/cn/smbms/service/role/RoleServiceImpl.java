package cn.smbms.service.role;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.smbms.dao.role.RoleDao;
import cn.smbms.pojo.Role;
@Service
public class RoleServiceImpl implements RoleService{
	
	@Resource
	private RoleDao roleDao;

	@Override
	public List<Role> getRoleList()  throws Exception {
		List<Role> roleList = null;
		roleList = roleDao.getRoleList();
		return roleList;
	}
	
}
