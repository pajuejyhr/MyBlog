package com.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blog.bean.LeaveWordFull;
import com.blog.service.LeaveWordService;

@Controller
public class LeaveWordController {
	private static final int PAGE_SIZE = 15;
	@Resource
	private LeaveWordService leaveWordService;

	/**
	 * 加载评论
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "leave_word.do")
	public String leave_word_index(HttpServletRequest request, Integer curPage, Model model) {
		String bloggerIdStr = null;
		if (request.getSession().getAttribute("bloggerId") != null)
			bloggerIdStr = request.getSession().getAttribute("bloggerId").toString();
		int bloggerId;
		if (bloggerIdStr == null || bloggerIdStr.trim().equals(""))
			bloggerId = 1;
		else
			bloggerId = Integer.parseInt(bloggerIdStr);
		Map<String, Integer> map = new HashMap<String, Integer>();
		// 获取总条数
		int count = leaveWordService.getCount(1);
		model.addAttribute("count", count);
		// 准备替换为blogger_id
		if (curPage == null || curPage <= 0)
			curPage = 1;
		int index = (curPage - 1) * PAGE_SIZE;
		map.put("id", bloggerId);
		map.put("index", index);
		map.put("pageSize", PAGE_SIZE);
		List<LeaveWordFull> list = leaveWordService.getByBloggerId(map);
		model.addAttribute("curPage", curPage);
		model.addAttribute("list", list);
		return "front/leave_word";
	}
}
