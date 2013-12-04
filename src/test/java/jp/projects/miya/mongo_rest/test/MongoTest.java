package jp.projects.miya.mongo_rest.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jp.projects.miya.mongo_rest.model.User;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MongoTest {
	@Test
	public void addUser() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		MongoOperations mongoOperation = (MongoOperations) ctx.getBean(MongoTemplate.class);

        try {
            FileReader in = new FileReader("data/csv/users.csv");
            BufferedReader br = new BufferedReader(in);
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
        		User user = new User(data[0], data[1]);

        		mongoOperation.save(user);
            }
            br.close();
            in.close();
        } catch (IOException ex) { }

		List<User> listUser = mongoOperation.findAll(User.class);
		System.out.println("Users: " + listUser.size());
	}

	@Test
	public void dropUser() throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		MongoOperations mongoOperation = (MongoOperations) ctx.getBean(MongoTemplate.class);

		mongoOperation.dropCollection(User.class);
	}

	@Test
	public void addImage() throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		GridFsOperations gridOperations =
				(GridFsOperations) ctx.getBean(org.springframework.data.mongodb.gridfs.GridFsTemplate.class);

		DBObject metaData = new BasicDBObject();
		metaData.put("meta1", "meta data 1");

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("data/images/sample.jpg");
			gridOperations.store(inputStream, "sample.jpg", "image/jpeg", metaData);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void dropImages() throws Exception {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		GridFsOperations gridOperations =
				(GridFsOperations) ctx.getBean(org.springframework.data.mongodb.gridfs.GridFsTemplate.class);

		gridOperations.delete(new Query(Criteria.where("_id").exists(true)));
	}

}