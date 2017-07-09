package com.nothing.story.client.model;

import lombok.Data;

/**
 * 数据值对象（返回单个对象）
 */
@Data
public class DataValue<T> extends ReturnValue {
	/**
	 * 返回的数据对象
	 */
	private T data;


}
