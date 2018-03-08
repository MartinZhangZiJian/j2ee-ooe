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
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkItemRecommendGetRequest;
import com.taobao.api.request.TbkJuTqgGetRequest;
import com.taobao.api.request.TbkShopGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemRecommendGetResponse;
import com.taobao.api.response.TbkJuTqgGetResponse;
import com.taobao.api.response.TbkShopGetResponse;

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
	@RequestMapping(value = Url.product_search, method = RequestMethod.GET)
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
	
	/**
	 * 好券清单API【导购】
	 * http://open.taobao.com/docs/api.htm?spm=a219a.7395905.0.0.tD7sbS&scopeId=11655&apiId=29821
	 * 
	 * @param adzone_id		mm_xxx_xxx_xxx的第三位
	 * @param platform		1：PC，2：无线，默认：1
	 * @param cat			后台类目ID，用,分割，最大10个，该ID可以通过taobao.itemcats.get接口获取到
	 * @param page_size		页大小，默认20，1~100
	 * @param q				查询词
	 * @param page_no		第几页，默认：1（当后台类目和查询词均不指定的时候，最多出10000个结果，即page_no*page_size不能超过200；当指定类目或关键词的时候，则最多出100个结果）
	 */
	@RequestMapping(value = Url.coupon_guide, method = RequestMethod.GET)
	public ModelAndView TaoBaoTBKDgItemCouponGet(@RequestParam(required = false)String cat, 
			@RequestParam(required = false)String q, @RequestParam(required = false)Long page_no) {
		
		TaobaoClient client = new DefaultTaobaoClient(Url.taobaoke_url, Key.getAppKey(), Key.getAppSecret());
		TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
		req.setAdzoneId(Key.getAdzoneId());
		req.setPlatform(platform);
		req.setCat(cat);
		req.setPageSize(page_size);
		req.setQ(q);
		req.setPageNo(page_no);
		TbkDgItemCouponGetResponse rsp;
		try {
			rsp = client.execute(req);
			return DataOutput.DataReturn(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataOutput.DataReturn(e.toString());
		}
	}
	
	/**
	 * 淘宝客商品关联推荐查询
	 * 
	 * @param num_iid 	Number 	必须 	商品Id
	 * @param count 		Number 	可选 	返回数量，默认20，最大值40
	 * @param platform 	Number 	可选 	链接形式：1：PC，2：无线，默认：１
	 */
	@RequestMapping(value = Url.product_recommend, method = RequestMethod.GET)
	public ModelAndView TaoBaoKeRecommend(@RequestParam(required = true)Long num_iid) {
		TaobaoClient client = new DefaultTaobaoClient(Url.taobaoke_url, Key.getAppKey(), Key.getAppSecret());
		TbkItemRecommendGetRequest req = new TbkItemRecommendGetRequest();
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url");
		req.setNumIid(num_iid);
		req.setCount(page_size);
		req.setPlatform(platform);
		try {
			TbkItemRecommendGetResponse rsp = client.execute(req);
			rsp = client.execute(req);
			return DataOutput.DataReturn(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataOutput.DataReturn(e.toString());
		}
	}
	
	/**
	 * 淘抢购api
	 * # 获取淘抢购的数据，淘客商品转淘客链接，非淘客商品输出普通链接 #
	 * 
	 * @param adzone_id 		Number 	必须 	推广位id（推广位申请方式：http://club.alimama.com/read.php?spm=0.0.0.0.npQdST&tid=6306396&ds=1&page=1&toread=1）
	 * @param start_time 	Date 	必须 	最早开团时间
	 * @param end_time 		Date 	必须 	最晚开团时间
	 * @param page_no 		Number 	可选 	第几页，默认1，1~100
	 * @param page_size 		Number 	可选 	页大小，默认40，1~40
	 */
	@RequestMapping(value = Url.tqg, method = RequestMethod.GET)
	public ModelAndView TaoBaoKeTQG(@RequestParam(required = true)String start_time, @RequestParam(required = true)String end_time,
			@RequestParam(required = true)Long page) {
		TaobaoClient client = new DefaultTaobaoClient(Url.taobaoke_url, Key.getAppKey(), Key.getAppSecret());
		TbkJuTqgGetRequest req = new TbkJuTqgGetRequest();
		req.setAdzoneId(Key.getAdzoneId());
		req.setFields("click_url,pic_url,reserve_price,zk_final_price,total_amount,sold_num,title,category_name,start_time,end_time");
		req.setStartTime(StringUtils.parseDateTime(start_time));
		req.setEndTime(StringUtils.parseDateTime(end_time));
		req.setPageNo(page);
		req.setPageSize(page_size);
		try {
			TbkJuTqgGetResponse rsp = client.execute(req);
			rsp = client.execute(req);
			return DataOutput.DataReturn(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataOutput.DataReturn(e.toString());
		}
	}
	
	/**
	 * 淘宝客店铺查询
	 * 
	 * @param q 							String 	必须 	查询词
	 * @param sort 						String 	可选  	排序_des（降序），排序_asc（升序），佣金比率（commission_rate）， 商品数量（auction_count），销售总数量（total_auction）
	 * @param is_tmall 					Boolean 可选 	是否商城的店铺，设置为true表示该是属于淘宝商城的店铺，设置为false或不设置表示不判断这个属性
	 * @param start_credit 				Number 	可选 	信用等级下限，1~20
	 * @param end_credit 				Number 	可选 	信用等级上限，1~20
	 * @param start_commission_rate 		Number 	可选 	淘客佣金比率下限，1~10000
	 * @param end_commission_rate	 	Number 	可选 	淘客佣金比率上限，1~10000
	 * @param start_total_action 		Number 	可选 	店铺商品总数下限
	 * @param end_total_action 			Number 	可选 	店铺商品总数上限
	 * @param start_auction_count 		Number 	可选 	累计推广商品下限
	 * @param end_auction_count 			Number 	可选 	累计推广商品上限
	 * @param platform 					Number 	可选 	链接形式：1：PC，2：无线，默认：１
	 * @param page_no 					Number 	可选 	第几页，默认1，1~100
	 * @param page_size 					Number 	可选 	页大小，默认20，1~100
	 */
	@RequestMapping(value = Url.shop_get, method = RequestMethod.GET)
	public ModelAndView TaoBaoKeShopGet(@RequestParam(required = true)String q, @RequestParam(required = false)String sort,
			@RequestParam(required = false)Long start_credit, @RequestParam(required = false)Long end_credit,
			@RequestParam(required = false)Long start_commission_rate, @RequestParam(required = false)Long end_commission_rate,
			@RequestParam(required = false)Long start_total_action, @RequestParam(required = false)Long end_total_action,
			@RequestParam(required = false)Long start_auction_count, @RequestParam(required = false)Long end_auction_count,
			@RequestParam(required = false)Long page_no) {
		TaobaoClient client = new DefaultTaobaoClient(Url.taobaoke_url, Key.getAppKey(), Key.getAppSecret());
		TbkShopGetRequest req = new TbkShopGetRequest();
		req.setFields("user_id,shop_title,shop_type,seller_nick,pict_url,shop_url");
		req.setQ(q);
		req.setSort(sort);
		req.setIsTmall(false);
		req.setStartCredit(start_credit);
		req.setEndCredit(end_credit);
		req.setStartCommissionRate(start_commission_rate);
		req.setEndCommissionRate(end_commission_rate);
		req.setStartTotalAction(start_total_action);
		req.setEndTotalAction(end_total_action);
		req.setStartAuctionCount(start_auction_count);
		req.setEndAuctionCount(end_auction_count);
		req.setPlatform(platform);
		req.setPageNo(page_no);
		req.setPageSize(page_size);
		try {
			TbkShopGetResponse rsp = client.execute(req);
			rsp = client.execute(req);
			return DataOutput.DataReturn(rsp.getBody());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataOutput.DataReturn(e.toString());
		}
	}
}
