/**
 * 
 */
package com.fsd.program.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fsd.program.entity.ParestTaskEntity;

/**
 * @author KarthickM
 *
 */
public interface ParentTaskRepo extends MongoRepository<ParestTaskEntity, String> {

	public ParestTaskEntity findByParentId(String parentId);
	
}
