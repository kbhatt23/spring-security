package com.learning.mongoinjection.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Value("${spring.data.mongodb.url}")
	private String mongoUrl;

	@Value("${mongodb.database}")
	private String mongoDb;

	@Override
	protected String getDatabaseName() {
		return mongoDb;
	}

	@Override
	public MongoClient mongoClient() {
		log.info("mongo config url: "+mongoUrl);
		ConnectionString connectionString = new ConnectionString(mongoUrl);
		MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.build();

		return MongoClients.create(mongoClientSettings);
	}

	@Bean
	public Jackson2RepositoryPopulatorFactoryBean initData() {
		MongoTemplate mongoTemplate = mongoTemplate();
		mongoTemplate.getDb().getCollection("users").drop();
		mongoTemplate.getDb().getCollection("user_roles").drop();
		Resource sourceData = new ClassPathResource("init-data.json");
		Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
		factory.setResources(new Resource[] { sourceData });
		return factory;
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), getDatabaseName());
	}
}
