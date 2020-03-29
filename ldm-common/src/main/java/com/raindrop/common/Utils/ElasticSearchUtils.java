package com.raindrop.common.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.netty.util.internal.StringUtil;
import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/*
 * @Description TODO ElasticSearch工具类
 * @Author zhang heson
 * @Date 10:49 2020/3/29
 **/
public class ElasticSearchUtils {
    private static RestHighLevelClient client = null;

    /*
     * @Description TODO 获取ES的服务器
     * @param: []
     * @return: org.elasticsearch.client.RestHighLevelClient
     * @auther: zhang hesong
     * @date: 10:52 2020/3/29
     */
    public RestHighLevelClient getClient() {
        if (client == null) {
            synchronized (ElasticSearchUtils.class) {
                if (client == null) {
                    client = new RestHighLevelClient(
                            RestClient.builder(
                                    new HttpHost("localhost", 9200, "http")     //ES服务器的配置信息
//                                    , new HttpHost("localhost", 9200, "http")                         多服务器配置
                            )
                    );
                }
            }
        }
        return client;
    }

    /*
     * @Description TODO 创建索引
     * @param: [indexName, alias, mappings, setting]
     * @return: boolean
     * @auther: zhang hesong
     * @date: 11:43 2020/3/29
     */
    public boolean createIndex(String indexName, String alias, JSONObject mappings, Settings setting){
        boolean result = true;
        try(RestHighLevelClient client = getClient();){
            //创建索引名字
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            //设置索引的settings
            if (Optional.ofNullable(setting).isPresent()) {
                request.settings(setting);
            }
            //设置索引的mapping
            if (Optional.ofNullable(mappings).isPresent()) {
                request.mapping(mappings.toJSONString(), XContentType.JSON);
            }
            //设置索引的别名
            if (!StringUtil.isNullOrEmpty(alias)) {
                request.alias(new Alias(alias));
            }

            //发送请求至ES----同步
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

            boolean acknowledged = response.isAcknowledged();
            boolean shardAsknowledged = response.isShardsAcknowledged();
            System.out.println("请求状态:" + (acknowledged && shardAsknowledged));

            //发送请求至ES----异步（监听器形式）
//            ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
//                @Override
//                public void onResponse(CreateIndexResponse createIndexResponse) {
//
//                    boolean acknowledged = createIndexResponse.isAcknowledged();
//                    boolean shardAsknowledged = createIndexResponse.isShardsAcknowledged();
//                    System.out.println("请求状态:" + (acknowledged && shardAsknowledged));
//                }
//
//                @Override
//                public void onFailure(Exception e) {
//                    System.out.println("缩影创建异常");
//                }
//            };
//            client.indices().createAsync(request, RequestOptions.DEFAULT, listener);
        } catch (Exception e) {
            e.printStackTrace();
            result=false;
        }
        return result;
    }

    /*
     * @Description TODO 创建索引测试
     * @param: [args]
     * @return: void
     * @auther: zhang hesong
     * @date: 11:47 2020/3/29
     */
//    public static void main(String[] args) {
//        ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
//        Settings settings = Settings.builder()
//                .put("index.number_of_shards", 3)
//                .put("index.number_of_replicas", 2).build();
//        System.out.println(elasticSearchUtils.createIndex("test", null, null, settings));
//    }

    /*
     * @Description TODO 向索引中添加数据或者修改数据
     * @param: [index, id, data]
     * @return: boolean
     * @auther: zhang hesong
     * @date: 15:43 2020/3/29
     */
    public boolean addDocument(String index, String id, Map<String, Object> data){
        boolean result = true;
        try(RestHighLevelClient client = getClient();){
            //设置插入的索引名和主键
            IndexRequest request = new IndexRequest(index);
            if (!StringUtil.isNullOrEmpty(id)){
                request.id(id);
            }
            //设置类型，不推荐使用
//            request.type("_doc");
            request.source(data);
            IndexResponse response = null;
            try{
                response = client.index(request, RequestOptions.DEFAULT);
            }catch (ElasticsearchException e){
                //判断是否存在版本冲突
                if(e.status() == RestStatus.CONFLICT){
                    System.out.println("ES版本与Client-Jar版本不一致");
                }
                System.out.println("索引异常：" + e);
            }

            //处理返回的数据逻辑——同步
            if(response != null){
                String res_index = response.getIndex();
                String res_type = response.getType();
                String res_id = response.getId();
                long version = response.getVersion();

                System.out.println("index: " + res_index + ";\n" +
                        "type: " + res_type + ";\n" +
                        "id: " + res_id + ";\n" +
                        "version: " + version + ";\n");

                if(response.getResult() == DocWriteResponse.Result.CREATED){
                    System.out.println("新增文档成功");
                }else if (response.getResult() == DocWriteResponse.Result.UPDATED){
                    System.out.println("更新文档成功");
                }
                //分片处理信息
                ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
                if(shardInfo.getTotal() != shardInfo.getSuccessful()){

                }
                //如果有分片副本失败，可以获取失败原因信息
                if(shardInfo.getFailed() > 0){
                    for(ReplicationResponse.ShardInfo.Failure failure: shardInfo.getFailures()){
                        String reason = failure.reason();
                        System.out.println("副本失败原因: " + reason);
                    }
                }
            }

            //处理返回的数据逻辑——同步
//            ActionListener<IndexResponse> listener = new ActionListener<IndexResponse>() {
//                @Override
//                public void onResponse(IndexResponse indexResponse) {
//
//                }
//
//                @Override
//                public void onFailure(Exception e) {
//
//                }
//            };
//            client.indexAsync(request, RequestOptions.DEFAULT, listener);
        }catch (IOException e){
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    /*
     * @Description TODO 向索引中添加数据或者修改数据测试
     * @param: [args]
     * @return: void
     * @auther: zhang hesong
     * @date: 15:52 2020/3/29
     */
//    public static void main(String[] args) {
//        //add
//        ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
//        Map<String, Object> data = new HashMap<>();
//        data.put("name", "Jack");
//        data.put("age", 18);
//        data.put("birthday", "2020-03-29");
//
//        elasticSearchUtils.addDocument("test", null, data);
//        //update
////        ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
////        Map<String, Object> data = new HashMap<>();
////        data.put("name", "Jack2");
////        data.put("age", 18);
////        data.put("birthday", "2020-03-29");
////
////        elasticSearchUtils.addDocument("test", "phdUJXEB7FKJ1xYIRsxH", data);
//    }

    /*
     * @Description TODO 删除数据
     * @param: [index, id]
     * @return: boolean
     * @auther: zhang hesong
     * @date: 16:28 2020/3/29
     */
    public boolean deleteDocument(String index, String id){
        boolean result = true;
        try(RestHighLevelClient client = getClient();){
            DeleteRequest request = new DeleteRequest(index);
            request.id(id);

            DeleteResponse response = null;
            try{
                response = client.delete(request, RequestOptions.DEFAULT);
            }catch (ElasticsearchException e){
                //判断是否存在版本冲突
                if(e.status() == RestStatus.CONFLICT){
                    System.out.println("ES版本与Client-Jar版本不一致");
                }
                System.out.println("索引异常：" + e);
            }
            if(Optional.ofNullable(response).isPresent()){
                   System.out.println("数据删除成功,ID:" + id);
            }
            //异步请求的写法和上面差不多，我就不重复写了
        }catch (IOException e){
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    /*
     * @Description TODO 删除数据测试
     * @param: [args]
     * @return: void
     * @auther: zhang hesong
     * @date: 16:29 2020/3/29
     */
//    public static void main(String[] args) {
//        ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
//        elasticSearchUtils.deleteDocument("test", "phdUJXEB7FKJ1xYIRsxH");
//    }

    /*
     * @Description TODO 获取单条数据
     * @param: [index, id, includes, excludes]
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @auther: zhang hesong
     * @date: 16:45 2020/3/29
     */
    public Map<String, Object> getDocument(String index, String id, String[] includes, String[] excludes){
        Map<String, Object> result = null;
        try(RestHighLevelClient client = getClient();) {
            GetRequest request = new GetRequest(index);
            request.id(id);
            if(includes == null || includes.length == 0){
                includes = Strings.EMPTY_ARRAY;
            }
            if(excludes == null || excludes.length == 0){
                excludes = Strings.EMPTY_ARRAY;
            }
            FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
            request.fetchSourceContext(fetchSourceContext);
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            if(response != null){
                String res_index = response.getIndex();
                String res_id = response.getId();
                if(response.isExists()){
                    long version = response.getVersion();
                    result = response.getSourceAsMap();
                    //获取字节串
//                    byte[] result = response.getSourceAsBytes();
                    //获取字节串
//                    String result = response.getSourceAsString();

                }else{
                    System.out.println("无此数据");
                }
            }else{
                System.out.println("无此数据");
            }

            //异步请求的写法和上面差不多，我就不重复写了
        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }

    /*
     * @Description TODO 获取单个数据的测试
     * @param: [args]
     * @return: void
     * @auther: zhang hesong
     * @date: 18:11 2020/3/29
     */
//    public static void main(String[] args) {
//        ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
//        Map<String, Object> map =  elasticSearchUtils.getDocument("test", "pRdNJXEB7FKJ1xYI9MzX", null, new String[]{"name", "age"});
//        System.out.println(JSON.toJSONString(map));
//    }

    /*
     * @Description TODO 文档搜索功能关于QueryBuilder建议查看源码
     * @param: [index, queryBuilder, includes, excludes, pageNum, pageSize]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @auther: zhang hesong
     * @date: 19:13 2020/3/29
     */
    public List<Map<String, Object>> searchDocument(String index, QueryBuilder queryBuilder,
                                                    String[] includes, String[] excludes,
                                                    Integer pageNum, Integer pageSize){
        List<Map<String, Object>> result = new ArrayList<>();
        try(RestHighLevelClient client = getClient();){
            SearchRequest searchRequest = new SearchRequest(index);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //设置查询体
            searchSourceBuilder.query(queryBuilder == null ? QueryBuilders.matchAllQuery() : queryBuilder);
            //设置页码
            searchSourceBuilder.from(pageNum == null ? 0 : pageNum);
            //设置每页显示数量
            searchSourceBuilder.size(pageSize == null ? 10 : pageSize);
            //设置查询超时时间
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            //根据评分进行排序
//            searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
            //根据主键进行排序
            searchSourceBuilder.sort(new FieldSortBuilder("age").order(SortOrder.ASC));
            if(includes == null || includes.length == 0){
                includes = Strings.EMPTY_ARRAY;
            }
            if(excludes == null || excludes.length == 0){
                excludes = Strings.EMPTY_ARRAY;
            }
            searchSourceBuilder.fetchSource(includes, excludes);
            // 高亮设置
//            HighlightBuilder highlightBuilder = new HighlightBuilder();
//            HighlightBuilder.Field highlightTitle =
//                    new HighlightBuilder.Field("title");
//            highlightTitle.highlighterType("unified");
//            highlightBuilder.field(highlightTitle);
//            HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("user");
//            highlightBuilder.field(highlightUser);
//            searchSourceBuilder.highlighter(highlightBuilder);

            //加入聚合
//            TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_company")
//                    .field("company.keyword");
//            aggregation.subAggregation(AggregationBuilders.avg("average_age")
//                    .field("age"));
//            searchSourceBuilder.aggregation(aggregation);

            //做查询建议
//            SuggestionBuilder termSuggestionBuilder =
//                    SuggestBuilders.termSuggestion("user").text("kmichy");
//                SuggestBuilder suggestBuilder = new SuggestBuilder();
//                suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder);
//            searchSourceBuilder.suggest(suggestBuilder);

            searchRequest.source(searchSourceBuilder);
            SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);
            //结果状态信息
            RestStatus status = response.status();
            TimeValue took = response.getTook();
            Boolean terminatedEarly = response.isTerminatedEarly();
            Boolean timeOut = response.isTimedOut();

            //分片搜索情况
            int totalShards = response.getTotalShards();
            int successfulShards = response.getSuccessfulShards();
            int failedShards = response.getFailedShards();
            for (ShardSearchFailure failure : response.getShardFailures()) {
                // 搜索失败处理
            }

            //处理搜索命中文档结果
            SearchHits hits = response.getHits();

            //搜索命中文档总数以及最高分
            TotalHits totalHits = hits.getTotalHits();
            float maxScore = hits.getMaxScore();

            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit: searchHits){
                String hit_index = hit.getIndex();
                String hit_id = hit.getId();
                float hit_score = hit.getScore();

                Map<String, Object> result_map = hit.getSourceAsMap();
                //获取字节串
//                    byte[] result_byte = hit.getSourceAsBytes();
                //获取字节串
//                    String result_string = hit.getSourceAsString();
                result.add(result_map);
            }

            //异步请求的写法和上面差不多，我就不重复写了
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    /*
     * @Description TODO 文档搜索功能测试
     * @param: [args]
     * @return: void
     * @auther: zhang hesong
     * @date: 19:14 2020/3/29
     */
//    public static void main(String[] args) {
//        ElasticSearchUtils elasticSearchUtils = new ElasticSearchUtils();
//        QueryBuilder queryBuilder = new MatchQueryBuilder("name", "Jack");
//        List<Map<String, Object>> list = elasticSearchUtils.searchDocument("test", queryBuilder, new String[]{"name"} , null, null, null);
//
//        System.out.println(JSON.toJSONString(list));
//    }
}
