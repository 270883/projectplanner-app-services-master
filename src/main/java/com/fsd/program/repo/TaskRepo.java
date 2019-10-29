/**
 * 
 */
package com.fsd.program.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fsd.program.entity.TaskEntity;

/**
 * @author KarthickM
 *
 */
public interface TaskRepo extends MongoRepository<TaskEntity, String> {

	public TaskEntity findByTaskId(String taskId);

	public List<TaskEntity> findByUserId(String id);
		
	public List<TaskEntity> findByProjectId(String projectId);

}
