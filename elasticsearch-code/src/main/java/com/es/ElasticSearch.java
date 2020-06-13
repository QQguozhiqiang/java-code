package com.es;

import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;

/**
 * @ClassName ElasticSearch
 * @Description TODO
 * @Author gzq
 * @Date 2020/6/7 11:23
 * @Version 1.0
 */
public class ElasticSearch {
    @Test
    public void createIndex() throws Exception{
        Settings settings = Settings.builder().put("cluster.name","my-elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9301));
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9302));
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9303));
        client.admin().indices().prepareCreate("index-3").get();
        client.close();
    }
    @Test
    public void setMapping() throws Exception{
        Settings settings = Settings.builder().put("cluster.name","my-elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9301))
        .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9302))
        .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9303));
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                    .startObject("properties")
                        .startObject("id")
                            .field("type","long")
                            .field("index",false)
                            .field("store",true)
                        .endObject()
                        .startObject("title")
                            .field("type","text")
                            .field("index",true)
                            .field("analyzer","ik_smart")
                            .field("store",true)
                        .endObject()
                        .startObject("content")
                            .field("type","text")
                            .field("index",true)
                            .field("store",true)
                            .field("analyzer","ik_smart")
                        .endObject()
                    .endObject()
                .endObject();
        client.admin().indices().preparePutMapping("index-3").setType("type1").setSource(builder).get();
        client.close();
    }
    @Test
    public void setIndicesMappings() throws Exception{
        Settings settings = Settings.builder().put("cluster.name","my-elasticsearch").build();
        TransportClient transportClient = new PreBuiltTransportClient(settings);
        transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9301))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9302))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9303));
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .startObject("properties")
                .startObject("id")
                .field("type","long")
                .field("index",false)
                .field("store",true)
                .endObject()
                .startObject("title")
                .field("type","text")
                .field("index",true)
                .field("analyzer","ik_smart")
                .field("store",true)
                .endObject()
                .startObject("content")
                .field("type","text")
                .field("index",true)
                .field("store",true)
                .field("analyzer","ik_smart")
                .endObject()
                .endObject()
                .endObject();
        transportClient.admin().indices().prepareCreate("index-4").addMapping("type2",builder).get();
        transportClient.close();
    }
    @Test
    public void addDocument() throws Exception{
        Settings settings = Settings.builder().put("cluster.name","my-elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9031))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9302))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9303));
        for (int i = 1; i < 100; i++) {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder();
            contentBuilder.startObject()
                    .field("id",i)
                    .field("title",""+i+"央视记者介入，负责“仝卓事件”相关单位，竟然一问三不知？")
                    .field("content",""+i+"关于歌手仝卓因“修改往届生身份信息，涉嫌高考舞弊”事件，相信大家多少都有些了解，作为歌坛冉冉升起的新星，综艺圈力捧的宠儿，仝卓此番可谓是自毁前程，直接把自己送入了深渊当中。")
                    .endObject();
            client.prepareIndex("index-4","type2",""+i).setSource(contentBuilder).get();
        }
        client.close();
    }
    @Test
    public void searchById() throws Exception{
        Settings settings = Settings.builder().put("cluster.name","my-elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9031))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9302))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9303));
        IdsQueryBuilder queryBuilder = QueryBuilders.idsQuery().addIds("1");
        SearchResponse searchResponse = client.prepareSearch("index-4").setQuery(queryBuilder).get();
        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();
        System.out.println(totalHits.value);
        SearchHit[] hitsHits = hits.getHits();
        for (int i = 0; i < hitsHits.length; i++) {
            System.out.println(hitsHits[i].getSourceAsString());
            System.out.println(hitsHits[i].getSourceAsMap().get("title"));
        }

    }
    @Test
    public void searchByTerm() throws Exception{
        Settings settings = Settings.builder().put("cluster.name","my-elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9031))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9302))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9303));
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "央视记者");
        SearchResponse searchResponse = client.prepareSearch("index-4").setQuery(termQueryBuilder).get();
        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();
        System.out.println(totalHits.value);
        SearchHit[] hitsHits = hits.getHits();
        for (int i = 0; i < hitsHits.length; i++) {
            System.out.println(hitsHits[i].getSourceAsString());
            System.out.println(hitsHits[i].getSourceAsMap().get("title"));
        }
    }
    @Test
    public void searchByQueryString() throws Exception{
        Settings settings = Settings.builder().put("cluster.name","my-elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9031))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9302))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"),9303));
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery("央视记者").defaultField("title");
        SearchResponse searchResponse = client.prepareSearch("index-4").setQuery(queryStringQueryBuilder).setFrom(0).setSize(10)
                .highlighter(new HighlightBuilder().field("title").preTags("<em>").postTags("</em>")).get();
        SearchHits hits = searchResponse.getHits();
        TotalHits totalHits = hits.getTotalHits();
        System.out.println(totalHits.value);
        SearchHit[] hitsHits = hits.getHits();
        for (int i = 0; i < hitsHits.length; i++) {
            System.out.println(hitsHits[i].getSourceAsString());
            System.out.println(hitsHits[i].getSourceAsMap().get("title"));
            System.out.println(hitsHits[i].getHighlightFields().get("title"));
        }
    }
}
