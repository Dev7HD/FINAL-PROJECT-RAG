package ma.dev7hd.finalexamrag.config;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Component
public class RagDataLoader {
    @Value("classpath:/pdfs/cv.pdf")
    private Resource pdfResource;

    @Value("store-data-v2.json")
    private String storeFile;

    private final JdbcClient jdbcClient;
    private VectorStore vectorStore;

    public RagDataLoader(JdbcClient jdbcClient, VectorStore vectorStore) {
        this.jdbcClient = jdbcClient;
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void initVectorStore(){
        Integer count = jdbcClient.sql("SELECT COUNT(*) FROM vector_store").query(Integer.class).single();
        if(count == 0){
            PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfResource);
            System.out.println("==========PDF was read: Done==========");
            List<Document> documents = reader.get();
            TextSplitter textSplitter = new TokenTextSplitter();
            System.out.println("==========PDF was split: Done==========");
            List<Document> chunks = textSplitter.split(documents);
            System.out.println("==========PDF was chunks: Done============");
            vectorStore.accept(chunks);
            System.out.println("==========Vector accept: Done============");
        }
    }

    //@Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore = new SimpleVectorStore(embeddingModel);
        String storePath = Path.of("src","main","resources","store").toAbsolutePath() + "/" + storeFile;
        File file = new File(storePath);
        if (!file.exists()) {
            PagePdfDocumentReader reader = new PagePdfDocumentReader(pdfResource);
            List<Document> documents = reader.get();
            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> chunks = textSplitter.split(documents);
            vectorStore.accept(chunks);
            vectorStore.save(file);
        } else {
            vectorStore.load(file);
        }
        return vectorStore;
    }


}
