package cn.smbms.service.provider;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.smbms.dao.bill.BillDao;
import cn.smbms.dao.provider.ProviderDao;
import cn.smbms.pojo.Provider;
@Service
public class ProviderServiceImpl implements ProviderService {
	
	@Resource
	private BillDao billDao;	
	@Resource
	private ProviderDao providerDao;

	@Override
	public boolean add(Provider provider,MultipartFile proPic)  throws Exception {
		if(proPic!=null){
			String pic_Path="E:/upload/img/";
			//pic原始名称
			String originalFilename = proPic.getOriginalFilename();		
			// 获取图片后缀
			String extName = UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));
			// 开始上传
			File newFile = new File(pic_Path+extName);
			//写到磁盘
			proPic.transferTo(newFile);
			//插入到provider
			provider.setProFile(extName);
		}
		boolean flag = false;
		if(providerDao.add(provider) > 0)
			flag = true;		
		return flag;
	}

	@Override
	public List<Provider> getProviderList(String proName,String proCode)  throws Exception {
		List<Provider> providerList = null;		
		providerList = providerDao.getProviderList(proName,proCode);
		return providerList;
	}

	/**
	 * 业务：根据ID删除供应商表的数据之前，需要先去订单表里进行查询操作
	 * 若订单表中无该供应商的订单数据，则可以删除
	 * 若有该供应商的订单数据，则不可以删除
	 * 返回值billCount
	 * 1> billCount == 0  删除---1 成功 （0） 2 不成功 （-1）
	 * 2> billCount > 0    不能删除 查询成功（0）查询不成功（-1）
	 * 
	 * ---判断
	 * 如果billCount = -1 失败
	 * 若billCount >= 0 成功
	 */
	@Override
	public int deleteProviderById(String delId)  throws Exception {
		int billCount = -1;
		if(delId!=null&&!"".equals(delId)){
			billCount = billDao.getBillCountByProviderId(Integer.valueOf(delId));				
			if(billCount == 0){
				providerDao.deleteProviderById(Integer.valueOf(delId));
			}				
		}
		return billCount;
	}

	@Override
	public Provider getProviderById(String id)  throws Exception {
		Provider provider = null;	
		if(id!=null&&!"".equals(id))
			provider = providerDao.getProviderById(Integer.valueOf(id));	
		return provider;
	}

	@Override
	public boolean modify(Provider provider,MultipartFile proPic)  throws Exception {
		if(proPic!=null){
			String pic_Path="E:/upload/img/";
			//pic原始名称
			String originalFilename = proPic.getOriginalFilename();		
			// 获取图片后缀
			String extName = UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));
			// 开始上传
			File newFile = new File(pic_Path+extName);
			//写到磁盘
			proPic.transferTo(newFile);
			//插入到provider
			provider.setProFile(extName);
		}
		boolean flag = false;		
		if(providerDao.modify(provider) > 0)
			flag = true;
		return flag;
	}

}
