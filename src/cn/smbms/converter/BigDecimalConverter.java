package cn.smbms.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;

//Converter<S, T>
//S:source,需要转换的源的类型
//T:target,需要转换的目标类型
public class BigDecimalConverter implements Converter<String, BigDecimal> {

	@Override
	public BigDecimal convert(String source) {	
		BigDecimal bigDecimal  = null;
		try {
			bigDecimal = new BigDecimal(source).setScale(2,BigDecimal.ROUND_DOWN);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bigDecimal;
	}
}