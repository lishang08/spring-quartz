package com.quartz.exception;

import com.dexcoder.commons.exceptions.DexcoderException;

/**
* Author: fulishang
* Create Time  : 2017年5月7日,下午9:57:40
* Modify Time :
* Desc  : 自定义异常
* Blog : https://lishang08.github.io/
*/

public class ScheduleException extends DexcoderException{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
    
	/**
	 *  初始化
	 * @param e
	 */
	public ScheduleException (Throwable e){
		super(e);
	}
	
	/**
	 * Constructor
	 * @param message
	 */
	public ScheduleException (String message) {
		super(message);
	}
	
	/**
	 * Constructor
	 * @param code
	 * @param message
	 */
	public ScheduleException (String code, String message) {
		super(code, message);
	}
	
}
