package com.pluralsight.security.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pluralsight.security.entity.PortfolioAccessControl;

public interface PortfolioAccessControlRepository extends MongoRepository<PortfolioAccessControl, String>{

	List<PortfolioAccessControl> findAllByUsername(String username);
	
}
