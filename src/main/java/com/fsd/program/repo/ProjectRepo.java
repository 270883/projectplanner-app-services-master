/**
 * 
 */
package com.fsd.program.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fsd.program.entity.ProjectEntity;

/**
 * @author KarthickM
 *
 */
public interface ProjectRepo extends MongoRepository<ProjectEntity, String> {

	public ProjectEntity findByProjectId(String projectId);

	public List<ProjectEntity> findByManagerId(String id);

}
