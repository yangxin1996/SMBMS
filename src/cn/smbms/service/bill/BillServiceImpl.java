package cn.smbms.service.bill;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.smbms.dao.bill.BillDao;
import cn.smbms.exception.ExpectException;
import cn.smbms.pojo.Bill;
@Service
public class BillServiceImpl implements BillService {
	
	@Resource
	private BillDao billDao;

	@Override
	public boolean add(Bill bill)  throws Exception {
		boolean flag = false;
		if(billDao.add(bill) > 0){
			flag = true;
		}else{
			throw new ExpectException("添加失败");
		}
		return flag;
	}

	@Override
	public List<Bill> getBillList(Bill bill)  throws Exception {
		List<Bill> billList = null;
		billList = billDao.getBillList(bill);
		return billList;
	}

	@Override
	public boolean deleteBillById(String delId)  throws Exception {
		boolean flag = false;
		if(delId!=null&&!"".equals(delId)){
			if(billDao.deleteBillById(Integer.valueOf(delId)) > 0)
				flag = true;
		}
		return flag;
	}

	@Override
	public Bill getBillById(String id)  throws Exception {
		Bill bill = null;
		if(id!=null &&!"".equals(id))
			bill = billDao.getBillById(Integer.valueOf(id));	
		return bill;
	}

	@Override
	public boolean modify(Bill bill)  throws Exception {
		boolean flag = false;
		if(billDao.modify(bill) > 0)
			flag = true;		
		return flag;
	}

}
