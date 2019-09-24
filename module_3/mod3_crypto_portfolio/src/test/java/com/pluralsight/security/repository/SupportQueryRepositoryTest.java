package com.pluralsight.security.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pluralsight.security.entity.SupportQuery;

@RunWith(SpringRunner.class)
@DataMongoTest
public class SupportQueryRepositoryTest {

	@Autowired
	private SupportQueryRepository supportRepository;

	@Before
	public void setup() {
		supportRepository.deleteAll();
		SupportQuery query = new SupportQuery("snakamoto","Unable to Remove Transaction");
		supportRepository.save(query);
	}
	
	@Test
	public void testRetrieveSupportQuery() {
		List<SupportQuery> supportQueries = supportRepository.findByUsername("snakamoto");
		assertEquals(1, supportQueries.size());
		assertNotNull(supportQueries.get(0).getId());
	}
	
	@Test
	public void testUpdateSUpportQueryWithNewPost() {
		SupportQuery query = supportRepository.findByUsername("snakamoto").get(0);
		String queryId = query.getId();
		query.addPost("Cannot remove transaction 12345", "snakamoto");
		supportRepository.save(query);
		Optional<SupportQuery> supportQuery = supportRepository.findById(queryId);
		assertEquals(1, supportQuery.get().getPosts().size());
		assertEquals("Cannot remove transaction 12345", supportQuery.get().getPosts().get(0).getContent());
	}
	
}
