package com.pluralsight.security.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.pluralsight.security.entity.SupportQuery;
import com.pluralsight.security.model.SupportQueryDto;
import com.pluralsight.security.repository.SupportQueryRepository;

@RunWith(MockitoJUnitRunner.class)
public class SupportQueryServiceNoSqlTest {

	@Mock
	private SupportQueryRepository supportRepositoryMock;
	@InjectMocks
	private SupportQueryServiceNoSql supportService;
	
	@Test
	public void testGetSupportQueries() {
		SupportQuery query = new SupportQuery("bob", "Cannot remove Transaction");
		List<SupportQuery> queries = new ArrayList<>();
		queries.add(query);
		when(supportRepositoryMock.findByUsername(Mockito.anyString())).thenReturn(queries);
		List<SupportQueryDto> supportQueries =  supportService.getSupportQueriesForUser();
		assertEquals(query.getUsername(), supportQueries.get(0).getUsername());
		assertEquals(query.getSubject(), supportQueries.get(0).getSubject());
		assertEquals(query.getCreated(), supportQueries.get(0).getCreationTime());
	}
}
