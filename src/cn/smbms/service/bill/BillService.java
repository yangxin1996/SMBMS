package cn.smbms.service.bill;

import java.util.List;

import cn.smbms.pojo.Bill;

public interface BillService {
	/**
	 * 增加订单
	 * @param bill
	 * @return
	 */
	public boolean add(Bill bill) throws Exception ;


	/**
	 * 通过条件获取订单列表-模糊查询-billList
	 * @param bill
	 * @return
	 */
	public List<Bill> getBillList(Bill bill) throws Exception ;
	
	/**
	 * 通过billId删除Bill
	 * @param delId
	 * @return
	 */
	public boolean deleteBillById(String delId) throws Exception ;
	
	
	/**
	 * 通过billId获取Bill
	 * @param id
	 * @return
	 */
	public Bill getBillById(String id) throws Exception ;
	
	/**
	 * 修改订单信息
	 * @param bill
	 * @return
	 */
	public boolean modify(Bill bill) throws Exception ;
	
}
