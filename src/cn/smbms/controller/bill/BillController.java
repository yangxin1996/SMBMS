package cn.smbms.controller.bill;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StringUtils;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.provider.ProviderService;

@Controller
@RequestMapping("bill")
public class BillController {
	
	@Autowired
	private BillService billService;
	@Autowired
	private ProviderService providerService;	
	
	@RequestMapping(value="query")
	public String queryBill(Model model,@ModelAttribute(value="queryProductName") String queryProductName,@ModelAttribute(value="queryProviderId") String queryProviderId,@ModelAttribute(value="queryIsPayment") String queryIsPayment) throws Exception {		
		List<Provider> providerList = new ArrayList<Provider>();					
		providerList = providerService.getProviderList("","");
		model.addAttribute("providerList", providerList);
		if(StringUtils.isNullOrEmpty(queryProductName)){
			queryProductName = "";
		}
		List<Bill> billList = new ArrayList<Bill>();		
		Bill bill = new Bill();
		if(StringUtils.isNullOrEmpty(queryIsPayment)){
			bill.setIsPayment(0);
		}else{
			bill.setIsPayment(Integer.parseInt(queryIsPayment));
		}		
		if(StringUtils.isNullOrEmpty(queryProviderId)){
			bill.setProviderId(0);
		}else{
			bill.setProviderId(Integer.parseInt(queryProviderId));
		}
		bill.setProductName(queryProductName);
		billList = billService.getBillList(bill);
		model.addAttribute("billList", billList);
		return "billlist";
	}
	
	@RequestMapping(value="add")
	public String addBill(Bill bill) throws Exception {		
		bill.setCreationDate(new Date());
		boolean flag = false;
		flag = billService.add(bill);
		System.out.println("add flag -- > " + flag);
		if(flag){
			return "redirect:/bill/query.action";
		}else{
			return "billadd";
		}
	}
	@RequestMapping(value="view")
	public String getBillByIdView(Model model,@RequestParam(value = "billid",required = false) String billid) throws Exception {	
		if(!StringUtils.isNullOrEmpty(billid)){		
			Bill bill = null;
			bill = billService.getBillById(billid);
			model.addAttribute("bill", bill);
			return "billview";
		}else{
			return null;
		}		
	}
	@RequestMapping(value="modify")
	public String getBillByIdModify(Model model,@RequestParam(value = "billid",required = false) String billid) throws Exception {	
		if(!StringUtils.isNullOrEmpty(billid)){
			Bill bill = null;
			bill = billService.getBillById(billid);
			model.addAttribute("bill", bill);
			return "billmodify";
		}else{
			return null;
		}		
	}
	@RequestMapping(value="modifysave")
	public String modify(Bill bill) throws Exception {	
		System.out.println("modify===============");		
		bill.setModifyDate(new Date());
		boolean flag = false;
		flag = billService.modify(bill);
		if(flag){
			return "redirect:/bill/query.action";
		}else{
			return "billmodify";
		}
	}
	@RequestMapping(value="delbill",produces="application/json")
	@ResponseBody
	public HashMap<String, String> delBill(@RequestBody @RequestParam(value="billid",required = false) String id) throws Exception {	
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(!StringUtils.isNullOrEmpty(id)){
			boolean flag = billService.deleteBillById(id);
			if(flag){//删除成功
				resultMap.put("delResult", "true");
			}else{//删除失败
				resultMap.put("delResult", "false");
			}
		}else{
			resultMap.put("delResult", "notexit");
		}
		return resultMap;
	}
	@RequestMapping(value="getproviderlist",produces="application/json")
	@ResponseBody
	public List<Provider> getProviderlist() throws Exception {	
		System.out.println("getproviderlist ========================= ");	
		List<Provider> providerList = new ArrayList<Provider>();		
		providerList = providerService.getProviderList("","");
		return providerList;

	}
}
