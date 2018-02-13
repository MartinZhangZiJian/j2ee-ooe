package com.oned.taobao.utils;

import org.springframework.web.servlet.ModelAndView;

/**
 * 输出数据
 */
public class DataOutput {

	public static ModelAndView DataReturn(Object data) {
		ModelAndView mv = new ModelAndView("post");
		mv.addObject("message", data);
		return mv;
	};
	
}
