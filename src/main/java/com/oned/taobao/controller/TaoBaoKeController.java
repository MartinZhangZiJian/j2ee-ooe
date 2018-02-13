package com.oned.taobao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.oned.taobao.url.Url;
import com.oned.taobao.utils.DataOutput;
import com.oned.taobao.utils.Key;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.response.TbkItemGetResponse;

/*
 * 淘宝客专用接口
 */
@Controller
@RequestMapping(value = Url.taobaoke)
public class TaoBaoKeController {
	
	final Long platform = 2L;
	final Long page_size = 20L;

	/**
	 * 淘宝客商品查询
	 * http://open.taobao.com/doc2/apiDetail.htm?apiId=24515&scopeId=11655  
	 * 
	 * @param q					查询词
	 * @param cat				后台类目ID，用,分割，最大10个，该ID可以通过taobao.itemcats.get接口获取到
	 * @param itemloc			所在地
	 * @param sort				排序_des（降序），排序_asc（升序），销量（total_sales），淘客佣金比率（tk_rate）， 累计推广量（tk_total_sales），总支出佣金（tk_total_commi）
	 * @param is_tmall			是否商城商品，设置为true表示该商品是属于淘宝商城商品，设置为false或不设置表示不判断这个属性
	 * @param is_overseas		是否海外商品，设置为true表示该商品是属于海外商品，设置为false或不设置表示不判断这个属性
	 * @param start_price		折扣价范围下限，单位：元
	 * @param end_price			折扣价范围上限，单位：元
	 * @param start_tk_rate		淘客佣金比率上限，如：1234表示12.34%
	 * @param end_tk_rate		淘客佣金比率下限，如：1234表示12.34%
	 * @param platform			链接形式：1：PC，2：无线，默认：１
	 * @param page_no			第几页，默认：１
	 * @param page_size			页大小，默认20，1~100
	 * @return
	 */
	@RequestMapping(value = Url.product + "/" + Url.search, method = RequestMethod.GET)
	public ModelAndView TaoBaoKeGoodsSearch(@RequestParam(required = true)String q, 
			@RequestParam(required = false)String cat, @RequestParam(required = false)String itemloc, 
			@RequestParam(required = false)String sort, @RequestParam(required = false)Long start_price, 
			@RequestParam(required = false)Long end_price, @RequestParam(required = false)Long start_tk_rate, 
			@RequestParam(required = false)Long end_tk_rate, @RequestParam(required = false)Long page_no) {
		TaobaoClient client = new DefaultTaobaoClient(Url.taobaoke_url, Key.getAppKey(), Key.getAppSecret());
		TbkItemGetRequest req = new TbkItemGetRequest();
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
		req.setQ(q);
		req.setCat(cat);
		req.setItemloc(itemloc);
		req.setSort(sort);
		req.setIsTmall(false);
		req.setIsOverseas(false);
		req.setStartPrice(start_price);
		req.setEndPrice(end_price);
		req.setStartTkRate(start_tk_rate);
		req.setEndTkRate(end_tk_rate);
		req.setPlatform(platform);
		req.setPageNo(page_no);
		req.setPageSize(page_size);
		TbkItemGetResponse rsp;
		try {
			rsp = client.execute(req);
			return DataOutput.DataReturn(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataOutput.DataReturn(e.toString());
		}
	}
	
}
