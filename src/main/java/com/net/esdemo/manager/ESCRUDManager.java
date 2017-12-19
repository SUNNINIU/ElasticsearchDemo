package com.net.esdemo.manager;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.net.esdemo.model.Goods;
import com.net.esdemo.model.GoodsFilter2ES;
import com.net.esdemo.model.ResultEntity;

/**
 * 
 * @author 作者 E-mail: issacgo@outlook.com
 * @date 创建时间：2017年12月14日 下午5:27:30
 * @explain elasticsearch 相关操作工具类
 * @return
 *
 */
@Component
public class ESCRUDManager {

	protected  Logger logger = LoggerFactory
			.getLogger(ESCRUDManager.class);
	/**
	 * esClientManager
	 */
	@Autowired
	private  ESClientManager esClientManager;
	
	/**
	 * jackson用于序列化操作的mapper
	 */
	private static final ObjectMapper mapper = new ObjectMapper();
	

	/**
	 * 
	 * @param goodsList
	 * @param type
	 * @throws UnknownHostException
	 * @throws JsonProcessingException
	 */
	public  void createIndex(List<Goods> goodsList,String type)
			throws UnknownHostException, JsonProcessingException {
	     Client client = esClientManager.getClient();  
	        // 如果存在就先删除索引  
	        if (client.admin().indices().prepareExists(esClientManager.getIndex()).get().isExists()) {  
	            client.admin().indices().prepareDelete(esClientManager.getIndex()).get();  
	        }  
	        // 创建索引,并设置mapping.  
	        String mappingStr = "{ \"goods\" : { \"properties\": { \"id\": { \"type\": \"long\" }, \"name\": {\"type\": \"string\", \"analyzer\": \"ik_max_word\"}, \"regionIds\": {\"type\": \"string\",\"index\": \"not_analyzed\"}}}}";  
	       
	        client.admin().indices().prepareCreate(esClientManager.getIndex()).addMapping(type, mappingStr).get();  
	  
	        // 批量处理request  
	        BulkRequestBuilder bulkRequest = client.prepareBulk();  
	  
	        byte[] json;  
	        for (Goods goods : goodsList) {  
	            json = mapper.writeValueAsBytes(goods);  
	            bulkRequest.add(new IndexRequest(esClientManager.getIndex(), type, goods.getId() + "").source(json));  
	        }  
	  
	        // 执行批量处理request  
	        BulkResponse bulkResponse = bulkRequest.get();  
	  
	        // 处理错误信息  
	        if (bulkResponse.hasFailures()) {  
	        	logger.info("====================批量创建索引过程中出现错误 下面是错误信息==========================");
	            long count = 0L;  
	            for (BulkItemResponse bulkItemResponse : bulkResponse) {  
		        	logger.info("发生错误的 索引id为 : "+bulkItemResponse.getId()+" ，错误信息为："+ bulkItemResponse.getFailureMessage());
	                count++;  
	            }  
	        	logger.info("====================批量创建索引过程中出现错误 上面是错误信息 共有: "+count+" 条记录==========================");
	        }  
	  
	        client.close();  
	    
	}

	
	/**
	 * 显示查询列表信息
	 * 
	 * @param type
	 * @param fieldName
	 * @param filter
	 * @param from
	 *            跳过前from个文档
	 * @param size
	 *            查询个数
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public  ResultEntity search(String type, String fieldName,
			GoodsFilter2ES filter) throws JsonParseException,
			JsonMappingException, IOException {
		Client client = esClientManager.getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(esClientManager.getIndex())
				.setTypes(type);
		responsebuilder.setExplain(true);

		if (null != filter.getQueryStr() && filter.getQueryStr() != "") {
			
			QueryBuilder qb = new BoolQueryBuilder().should(
					QueryBuilders.matchQuery(fieldName, filter.getQueryStr()))
					.must(QueryBuilders.termQuery("regionIds",
							filter.getRegionId()));
		/*	 QueryBuilder qb = new BoolQueryBuilder().should(QueryBuilders.wildcardQuery("name", "*" + filter.getQueryStr() + "*")).should(
						QueryBuilders.termQuery("regionIds", filter.getRegionId()));*/
			
		/* QueryBuilder qb = new BoolQueryBuilder().should(QueryBuilders.matchQuery("name",filter.getQueryStr() )).must(QueryBuilders.matchQuery("regionIds", filter.getRegionId()));
		 */
			/*	 QueryBuilder qb = new BoolQueryBuilder().should(QueryBuilders.fuzzyQuery("name", filter.getQueryStr()).fuzziness(Fuzziness.ONE)).must(
						QueryBuilders.termQuery("regionIds", filter.getRegionId()));*/	

			// 查询
			responsebuilder.setQuery(qb);

		}

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits searchHits = myresponse.getHits();
		// 总命中数
		long total = searchHits.getTotalHits();

		List<Object> resultStr = new ArrayList<Object>();
		for (SearchHit hit : myresponse.getHits().getHits()) {
			Map<String, Object> source = hit.getSource();
			resultStr.add(source.get(fieldName));

		}
		ResultEntity resultEntity = new ResultEntity();
		resultEntity.setTotal(total);
		resultEntity.setStatus(ResultEntity.SUCCESS);
		resultEntity.setResList(resultStr);
		client.close();
		return resultEntity;
	}

	/**
	 * 限定查询个数并且关键字设置高亮
	 * 
	 * @param type
	 * @param fieldName
	 * @param filter
	 * @param from
	 *            跳过前from个文档
	 * @param size
	 *            查询个数
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public  Map<String, Object> searchDocHighlight(String type,
			String fieldName, GoodsFilter2ES filter, int from, int size)
			throws JsonParseException, JsonMappingException, IOException {
		Client client = esClientManager.getClient();

		SearchRequestBuilder responsebuilder = client.prepareSearch(esClientManager.getIndex())
				.setTypes(type);
		responsebuilder.setFrom(from); // 跳过前from个文档
		responsebuilder.setSize(size);// 条数
		responsebuilder.setExplain(true);

		if (null != filter.getQueryStr() && filter.getQueryStr() != "") {
			QueryBuilder qb = new BoolQueryBuilder().must(
					QueryBuilders.matchQuery(fieldName, filter.getQueryStr()))
					.must(QueryBuilders.termQuery("regionIds",
							filter.getRegionId()));
			// 查询
			responsebuilder.setQuery(qb);

			// 设置搜索关键字高亮
			responsebuilder.addHighlightedField(fieldName);
			responsebuilder.setHighlighterPreTags("<span style=\"color:red\">")
					.setHighlighterPostTags("</span>");
		}

		SearchResponse myresponse = responsebuilder.execute().actionGet();
		SearchHits searchHits = myresponse.getHits();

		// 总命中数
		long total = searchHits.getTotalHits();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (SearchHit hit : myresponse.getHits().getHits()) {
			Map<String, Object> source = hit.getSource();
			HighlightField hField = hit.getHighlightFields().get(fieldName);
			String name = "";
			if (hField.fragments()!=null) {
				for (Text text : hField.fragments()) {
					name += text;
				}
				source.put(fieldName, name);
			}
			
			list.add(source);
		}
		map.put("total", total);
		map.put("rows", list);
		client.close();
		return map;
	}

	/**
	 * 新增document
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            类型名称
	 * @param goods
	 *            商品dto
	 * @throws UnknownHostException
	 * @throws JsonProcessingException
	 */
	public void addDocument(String index, String type, Goods goods)
			throws UnknownHostException, JsonProcessingException {
		Client client = esClientManager.getClient();

		byte[] json = mapper.writeValueAsBytes(goods);
		System.out.println(json);

		IndexResponse response = client
				.prepareIndex(index, type, goods.getId() + "").setSource(json)
				.get();

		client.close();
	}

	/**
	 * 删除document
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            类型名称
	 * @param goodsId
	 *            要删除的商品id
	 * @throws UnknownHostException
	 */
	public  void deleteDocument(String index, String type, Long goodsId)
			throws UnknownHostException {
		Client client = esClientManager.getClient();
	
		  DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index)
                  .execute().actionGet();
		    if (dResponse.isAcknowledged()) {
                System.out.println("delete index "+index+"  successfully!");
            }else{
                System.out.println("Fail to delete index "+index);
            }
			client.close();
	}

	/**
	 * 更新document
	 * 
	 * @param index
	 *            索引名称
	 * @param type
	 *            类型名称
	 * @param goods
	 *            商品dto
	 * @throws JsonProcessingException
	 * @throws UnknownHostException
	 */
	public  void updateDocument(String index, String type, Goods goods)
			throws UnknownHostException, JsonProcessingException {
		// 如果新增的时候index存在，就是更新操作
		addDocument(index, type, goods);
	}

}