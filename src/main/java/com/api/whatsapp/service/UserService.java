//package com.api.whatsapp.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Service;
//
//import com.api.whatsapp.model.User;
//import com.mongodb.client.result.DeleteResult;
//
//@Service
//public class UserService {
//
//	@Autowired
//	MongoTemplate mongoTemplate;
//	
//	private final static String COLLECTION_NAME = "user";
//	
//	public User save(User user) {
//		return mongoTemplate.save(user, COLLECTION_NAME);
//	}
//
//	public List<User> list() {
//		return mongoTemplate.findAll(User.class, COLLECTION_NAME);
//	}
//
//	public DeleteResult delete(String id) {
//		Query query = new Query(Criteria.where("id").is(id));
//		return mongoTemplate.remove(query, User.class, COLLECTION_NAME);
//
//	}
//
//	public User edit(User user) {
//		Query query = new Query(Criteria.where("id").is(user.getId()));
//		User user2 = mongoTemplate.findOne(query, User.class);
//		if(user2 != null) {
//			return mongoTemplate.save(user, COLLECTION_NAME);
//		}
//		throw new RuntimeException("Usuário não encontrado");
//	}
//
//	public List<User> findByName(String name) {
//		Query query = new Query(Criteria.where("name").regex(name));
//		List<User> users = mongoTemplate.find(query, User.class, COLLECTION_NAME);
//		return users;
//	}
//
//}
