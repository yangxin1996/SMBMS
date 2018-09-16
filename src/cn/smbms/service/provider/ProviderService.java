package cn.smbms.service.provider;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.smbms.pojo.Provider;

public interface ProviderService {
	/**
	 * 增加供应商
	 * @param provider
	 * @return
	 */
	public boolean add(Provider provider,MultipartFile proPic) throws Exception ;


	/**
	 * 通过供应商名称、编码获取供应商列表-模糊查询-providerList
	 * @param proName
	 * @return
	 */
	public List<Provider> getProviderList(String proName,String proCode) throws Exception ;
	
	/**
	 * 通过proId删除Provider
	 * @param delId
	 * @return
	 */
	public int deleteProviderById(String delId) throws Exception ;
	
	
	/**
	 * 通过proId获取Provider
	 * @param id
	 * @return
	 */
	public Provider getProviderById(String id) throws Exception ;
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public boolean modify(Provider provider,MultipartFile proPic) throws Exception ;
	
}
