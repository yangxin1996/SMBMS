package cn.smbms.controller.provider;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;

import cn.smbms.pojo.Provider;
import cn.smbms.service.provider.ProviderService;

@Controller
@RequestMapping("provider")
public class ProviderController {
	@Autowired
	private ProviderService providerService;
	
	@RequestMapping(value="query")
	public String query(Model model,@ModelAttribute(value="queryProName") String queryProName,@ModelAttribute(value="queryProCode") String queryProCode) throws Exception {		
		if(StringUtils.isNullOrEmpty(queryProName)){
			queryProName = "";
		}
		if(StringUtils.isNullOrEmpty(queryProCode)){
			queryProCode = "";
		}
		List<Provider> providerList = new ArrayList<Provider>();
		providerList = providerService.getProviderList(queryProName,queryProCode);
		model.addAttribute("providerList", providerList);
		return "forward:../jsp/providerlist.jsp";
	}
	@RequestMapping(value="add")
	public String add(Provider provider,MultipartFile proPic) throws Exception {	
		provider.setCreationDate(new Date());
		boolean flag = false;
		flag = providerService.add(provider,proPic);
		if(flag){
			return "redirect:/provider/query.action";
		}else{
			return "provideradd";
		}
	}
	@RequestMapping(value="view")
	public String getProviderByIdView(Model model,@RequestParam(value = "proid",required = false) String id) throws Exception {	
		if(!StringUtils.isNullOrEmpty(id)){
			Provider provider = null;
			provider = providerService.getProviderById(id);
			model.addAttribute("provider", provider);
			return "providerview";
		}
		return null;
	}
	@RequestMapping(value="modify")
	public String getProviderByIdModify(Model model,@RequestParam(value = "proid",required = false) String id) throws Exception {	
		if(!StringUtils.isNullOrEmpty(id)){
			Provider provider = null;
			provider = providerService.getProviderById(id);
			model.addAttribute("provider", provider);
			return "providermodify";
		}
		return null;
	}
	@RequestMapping(value="modifysave")
	public String modify(Provider provider,MultipartFile proPic) throws Exception {
		provider.setModifyDate(new Date());
		boolean flag = false;
		flag = providerService.modify(provider,proPic);
		if(flag){
			return "redirect:/provider/query.action";
		}else{
			return "providermodify";
		}
	}
	@RequestMapping(value="delprovider",produces="application/json")
	@ResponseBody
	public HashMap<String, String> delProvider(@RequestBody @RequestParam(value="proid",required = false) String id) throws Exception {	
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(!StringUtils.isNullOrEmpty(id)){
			int flag = providerService.deleteProviderById(id);
			if(flag == 0){//删除成功
				resultMap.put("delResult", "true");
			}else if(flag == -1){//删除失败
				resultMap.put("delResult", "false");
			}else if(flag > 0){//该供应商下有订单，不能删除，返回订单数
				resultMap.put("delResult", String.valueOf(flag));
			}
		}else{
			resultMap.put("delResult", "notexit");
		}
		return resultMap;
	}
	@RequestMapping(value="download")
	public ResponseEntity<byte[]> download(@RequestParam(value="proFile",required = false) String proFile) throws Exception {
		if(null!=proFile&&!"".equals(proFile)){
			String relpath = "E:\\upload\\img\\"+proFile;
			HttpHeaders headers = new HttpHeaders();    
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);    
	        headers.setContentDispositionFormData("attachment", proFile);    
	        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(relpath)),headers, HttpStatus.CREATED); 		
			
		}else {
			return null;
		}
	}
}
