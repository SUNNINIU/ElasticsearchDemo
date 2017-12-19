package com.net.esdemo.controller;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.net.esdemo.manager.ESCRUDManager;
import com.net.esdemo.model.GoodsFilter2ES;
import com.net.esdemo.model.ResultEntity;

@RestController
@RequestMapping("/searchController")
public class SearchController {
	protected final Logger logger = LoggerFactory
			.getLogger(SearchController.class);
	@Autowired
	private  ESCRUDManager escrudManager;
	/**
	 * 关键字搜索提示
	 * 
	 * @param keyWord
	 * @return
	 */
	@RequestMapping("/serch")
	public ResultEntity serch(@Param("keyWord") String keyWord) {
		ResultEntity resultEntity = null;
		if (keyWord != null) {
			GoodsFilter2ES filter = new GoodsFilter2ES();

			filter.setQueryStr(keyWord);
			filter.setRegionId("r3");
			try {
				resultEntity = escrudManager.search("goods", "name", filter);
				logger.info(resultEntity.toString());

			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
		return resultEntity;
	}

	/**
	 * 关键字搜索返回结果
	 * 
	 * @param keyWord
	 * @return
	 */
	@RequestMapping("/serchBykeyword")
	public Map<String, Object> serchBykeyword(@Param("keyWord") String keyWord,
			@Param("pageSize") int pageSize,
			@Param("currentPage") int currentPage) {
		Map<String, Object> result = null;
		if (keyWord != null && keyWord != "") {
			GoodsFilter2ES filter = new GoodsFilter2ES();
			filter.setQueryStr(keyWord);
			filter.setRegionId("r3");
			if (currentPage == 0) {
				currentPage++;
			}
			int from = pageSize * (currentPage - 1);// 跳过前from个doc查询

			logger.info(keyWord);
			try {
				result = escrudManager.searchDocHighlight("goods", "name", filter,
						from, pageSize);
				result.put("currentPage", currentPage);
				logger.info(result.toString());
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}

		return result;

	}
}