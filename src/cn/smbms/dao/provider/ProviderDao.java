package cn.smbms.dao.provider;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;

import cn.smbms.pojo.Provider;

@MapperScan
public interface ProviderDao {
	
	/**
	 * 增加供应商
	 * @param connection
	 * @param provider
	 * @return
	 * @throws Exception
	 */
	@Insert("insert into smbms_provider (proCode,proName,proDesc," +
					"proContact,proPhone,proAddress,proFax,createdBy,creationDate,proFile) " +
					"values(#{proCode},#{proName},#{proDesc},#{proContact},#{proPhone},#{proAddress},#{proFax},#{createdBy},#{creationDate},#{proFile})")
	public int add(Provider provider)throws Exception;


	/**
	 * 通过供应商名称、编码获取供应商列表-模糊查询-providerList
	 * @param connection
	 * @param proName
	 * @return
	 * @throws Exception
	 */
	
	public List<Provider> getProviderList(@Param("proName")String proName,@Param("proCode")String proCode)throws Exception;
	
	/**
	 * 通过proId删除Provider
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	@Delete("delete from smbms_provider where id=#{delId}")
	public int deleteProviderById(Integer delId)throws Exception; 
	
	
	/**
	 * 通过proId获取Provider
	 * @param connection
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Select("select * from smbms_provider where id=#{id}")
	public Provider getProviderById(Integer id)throws Exception; 
	
	/**
	 * 修改用户信息
	 * @param connection
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Update("update smbms_provider set proName=#{proName},proDesc=#{proDesc},proContact=#{proContact}," +
					"proPhone=#{proPhone},proAddress=#{proAddress},proFax=#{proFax},modifyBy=#{modifyBy},modifyDate=#{modifyDate},proFile=#{proFile} where id = #{id} ")
	public int modify(Provider provider)throws Exception;
	
	
}
