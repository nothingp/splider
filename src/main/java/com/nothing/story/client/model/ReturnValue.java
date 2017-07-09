package com.nothing.story.client.model;
import lombok.Data;

/**
 * 接口返回值对象
 */
@Data
public class ReturnValue {

	private static final long serialVersionUID = -5655757386004172231L;

	/**
	 * 返回码
	 */
	private String code;
	
	/**
	 * 返回描述
	 */
	private String desc;

	/**
	 * 返回时间
	 */
	private Long responseTime = System.currentTimeMillis();
	

}
