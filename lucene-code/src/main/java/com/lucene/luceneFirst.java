package com.lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/**
 * @ClassName luceneFirst
 * @Description TODO
 * @Author gzq
 * @Date 2020/6/6 11:24
 * @Version 1.0
 */
public class luceneFirst {
    /**
     * 1、创建一个directory对象，指定索引库位置
     * 2、基于directory对象创建一个indexwriter对象
     * 3、读取磁盘文件，对应每个文件创建一个文档对象
     * 4、将文档添加到域中
     * 5、把文档对象写入索引库
     * 6、关闭indexwriter对象
     */
    @Test
    public void createIndex() throws Exception{
        Directory directory = FSDirectory.open(new File("D:\\lucene\\index"));
        IndexWriterConfig writerConfig = new IndexWriterConfig(Version.LUCENE_47,new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory,writerConfig);
        File direc = new File("C:\\Users\\gzq\\Desktop\\四川政务\\资阳二期");
        File[] files = direc.listFiles();
        for (File file:
             files) {
            String path = file.getPath();
            String name = file.getName();
            String content = FileUtils.readFileToString(file,"GBK");
            long size = FileUtils.sizeOf(file);
            Field fieldName = new TextField("filename",name, Field.Store.YES);
            Field fieldPath = new TextField("filepath",path, Field.Store.YES);
            Field fieldContent = new TextField("fileContent",content, Field.Store.YES);
            Field fieldSize = new TextField("fileSize",size+"", Field.Store.YES);
            Document document = new Document();
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
            document.add(fieldSize);
            indexWriter.addDocument(document);

        }
        indexWriter.close();
    }

    @Test
    public void searchIndex() throws Exception{
        Directory directory = FSDirectory.open(new File("D:\\lucene\\index"));
        IndexReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
        Query query = new TermQuery(new Term("filename","联"));
        TopDocs topDocs = indexSearcher.search(query, 10);
        System.out.println("------"+topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc doc:
             scoreDocs) {
            int docId = doc.doc;
            Document document = indexSearcher.doc(docId);
            System.out.println(document.get("fileContent"));
            System.out.println(document.get("filename"));
        }
        directoryReader.close();
    }
}
