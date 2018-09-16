package cn.smbms.dao.bill;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mybatis.spring.annotation.MapperScan;

import cn.smbms.pojo.Bill;

@MapperScan
public interface BillDao {
	/**
	 * 增加订单
	 * @param connection
	 * @param bill
	 * @return
	 * @throws Exception
	 */
	@Insert("insert into smbms_bill "
			+ "(billCode,productName,productDesc,productUnit,productCount,"
			+ "totalPrice,isPayment,providerId,createdBy,creationDate) "
			+ "values(#{billCode},#{productName},#{productDesc},#{productUnit},#{productCount},"
			+ "#{totalPrice},#{isPayment},#{providerId},#{createdBy},#{creationDate})")
	public int add(Bill bill)throws Exception;


	/**
	 * 通过查询条件获取供应商列表-模糊查询-getBillList
	 * @param connection
	 * @param bill
	 * @return
	 * @throws Exception
	 */
	public List<Bill> getBillList(Bill bill)throws Exception;
	
	/**
	 * 通过delId删除Bill
	 * @param connection
	 * @param delId
	 * @return
	 * @throws Exception
	 */
	@Delete("delete from smbms_bill where id=#{delId}")
	public int deleteBillById(Integer delId)throws Exception; 
	
	/**
	 * 通过billId获取Bill
	 * @param connection
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Select("select b.*,p.proName as providerName from smbms_bill b, smbms_provider p " + "where b.providerId = p.id and b.id=#{id}")
	public Bill getBillById(Integer id)throws Exception; 
	
	/**
	 * 修改订单信息
	 * @param connection
	 * @param bill
	 * @return
	 * @throws Exception
	 */
	@Update("update smbms_bill set "
			+ "productName=#{productName},productDesc=#{productDesc},productUnit=#{productUnit},"
			+ "productCount=#{productCount},totalPrice=#{totalPrice},isPayment=#{isPayment},"
			+ "providerId=#{providerId},modifyBy=#{createdBy},modifyDate=#{creationDate} "
			+ "where id = #{id}")
	public int modify(Bill bill)throws Exception;

	/**
	 * 根据供应商ID查询订单数量
	 * @param connection
	 * @param providerId
	 * @return
	 * @throws Exception
	 */
	@Select("select count(1) as billCount from smbms_bill where" + " providerId = #{providerId}")
	public int getBillCountByProviderId(Integer providerId)throws Exception;

}
