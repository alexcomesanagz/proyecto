package comentarios.comentarios;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MongoConfig {
    @Bean
    public MongoClient mongoClient() {
        // Aquí le obligas a usar tu URI sin mirar ningún archivo .properties
        return MongoClients.create("mongodb://localhost:27017/comentariosProyecto");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        // Aquí le obligas a que la base de datos sea esta y no 'test'
        return new MongoTemplate(mongoClient(), "comentariosProyecto");
    }
}
