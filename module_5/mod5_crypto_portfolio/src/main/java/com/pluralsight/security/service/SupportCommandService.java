package com.pluralsight.security.service;

import com.pluralsight.security.entity.PostDto;
import com.pluralsight.security.model.CreateSupportQueryDto;

public interface SupportCommandService {

	void createQuery(CreateSupportQueryDto query);
	void postToQuery(PostDto supportQueryPostModel);
	void resolveQuery(String id);
	
}
