package cn.smbms.dao.role;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import cn.smbms.pojo.Role;

@MapperScan
public interface RoleDao {
	
	@Select("select * from smbms_role")
	public List<Role> getRoleList()throws Exception;

}
