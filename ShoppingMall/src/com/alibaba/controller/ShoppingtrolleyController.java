package com.alibaba.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.entity.TrolleyInfo;
import com.alibaba.entity.User;
import com.alibaba.service.impl.ShoppingtrolleyServiceImpl;

@Controller
@RequestMapping("Shoppingtrolley")
public class ShoppingtrolleyController {

	@Autowired
	private ShoppingtrolleyServiceImpl service;

	@RequestMapping("addGoods")
	public String addGoods(HttpServletRequest request, @RequestParam(value = "price", required = false) double price,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "color", required = false) String color,
			@RequestParam(value = "number", required = false) int number,
			@RequestParam(value = "rear", required = false) String rear,
			@RequestParam(value = "front", required = false) String front,
			@RequestParam(value = "capacity", required = false) String capacity,
			@RequestParam(value = "goodsId", required = false) int goodsId) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user != null) {
			int shoppingtrolleyId = (Integer) session.getAttribute("shoppingtrolleyId");
			service.addGoods(price, goodsId, number, shoppingtrolleyId, rear, front, capacity, type);
		}
		request.setAttribute("type", type);
		request.setAttribute("color", color);
		request.setAttribute("rear", rear);
		request.setAttribute("front", front);
		request.setAttribute("capacity", capacity);
		request.setAttribute("number", number);
		request.setAttribute("goodsId", goodsId);
		return "inform";
	}

	@RequestMapping("queryTrolleyInfo")
	public String queryTrolleyInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		int trolleyId = 0;
		if (user != null) {
			trolleyId = (Integer) session.getAttribute("shoppingtrolleyId");
			TrolleyInfo info = service.queryTrolley(trolleyId);
			session.setAttribute("prices", info.getPrices());
			session.setAttribute("names", info.getNames());
			session.setAttribute("goodsInTrolley", info.getTrolley());
			session.setAttribute("totalPrice", info.getTotalPrice());
			session.setAttribute("totalQuantity", info.getTotalQuantity());
			session.setAttribute("quantities", info.getQuantities());
			return "QueryTrolley";
		} 
		return "";

	}

	@RequestMapping("deleteGoods")
	@ResponseBody
	public Map<String, String> deleteGoods(@RequestBody String data) {
		JSONObject jsonObj;
		Map<String, String> map = new HashMap<>();
		try {
			jsonObj = new JSONObject(data);
			int goodsId = (int) jsonObj.get("goodsId");
			int trolleyId = (int) jsonObj.get("trolleyId");
			service.deleteGoods(goodsId, trolleyId);
			map.put("msg", "OK");
		} catch (JSONException e) {
			e.printStackTrace();
			map.put("msg", "error");
		}
		return map;
	}

	@RequestMapping("updateQuantity")
	@ResponseBody
	public Map<String, String> updateQuantity(@RequestBody String data) {
		JSONObject jsonObj;
		Map<String, String> map = new HashMap<>();
		try {
			jsonObj = new JSONObject(data);
			int goodsId = (int) jsonObj.get("goodsId");
			int number = (int) jsonObj.get("number");
			int trolleyId = (int) jsonObj.get("trolleyId");
			int price = (int) jsonObj.get("price");
			service.modifyGoodsQuantity(number, price * number, trolleyId, goodsId);
			map.put("msg", "OK");
		} catch (JSONException e) {
			e.printStackTrace();
			map.put("msg", "error");
		}
		return map;
	}

}
