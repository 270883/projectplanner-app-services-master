/**
 * 
 */
package com.fsd.program.services;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.program.entity.ParestTaskEntity;
import com.fsd.program.entity.ProjectEntity;
import com.fsd.program.entity.TaskEntity;
import com.fsd.program.entity.UserEntity;
import com.fsd.program.repo.ParentTaskRepo;
import com.fsd.program.repo.ProjectRepo;
import com.fsd.program.repo.TaskRepo;
import com.fsd.program.repo.UserRepo;

/**
 * @author KarthickM
 *
 */
@RestController
@RequestMapping("/task")
public class TaskSvcs {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskSvcs.class);

	@Autowired
	private TaskRepo taskRepository;

	@Autowired
	private ProjectRepo projectRepository;

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private ParentTaskRepo parentTaskRepository;

	@RequestMapping("/getTasks")
	public List<TaskEntity> getTasks() {
		logger.info("Method getTasks() executed");
		List<TaskEntity> tasks = taskRepository.findAll();

		for (TaskEntity task : tasks) {
			String parentId = task.getParentId();
			if (parentId != null) {
				ParestTaskEntity parentTask = parentTaskRepository.findById(parentId).get();
				task.setParentTask(parentTask.getParentTask());
				
			}
			if (task.getUserId() != null && !task.getUserId().isEmpty()) {
				try {
					UserEntity user = userRepository.findById(task.getUserId()).get();
					ProjectEntity project = projectRepository.findById(task.getProjectId()).get();
					task.setProjectName(project.getProjectName());
					task.setUserName(user.getFirstName() + " " + user.getLastName());
				} catch (Exception e) {
					logger.error("Error occured -- No UserId matching recored");
				}
			}
		}

		return tasks;
	}

	@RequestMapping("/getParentTasks")
	public List<ParestTaskEntity> getParentTasks() {
		return parentTaskRepository.findAll();
	}

	@RequestMapping("/addUpdate")
	public List<TaskEntity> addUpdateTask(@RequestBody Map<String, String> requestMap) throws ParseException {
		logger.info("Method addUpdateTask() executed");
		taskRepository.save(updateTaskEntity(requestMap));
		return getTasks();
	}

	@RequestMapping("/endTask")
	public List<TaskEntity> endTask(@RequestBody Map<String, String> requestMap) throws ParseException {
		TaskEntity taskEntity = updateTaskEntity(requestMap);
		taskEntity.setStatus("Completed");
		taskRepository.save(taskEntity);
		return getTasks();
	}

	@RequestMapping("/deleteTask")
	public List<TaskEntity> deleteTask(@RequestBody TaskEntity task) {
		taskRepository.delete(task);
		return taskRepository.findAll();
	}

	public TaskEntity updateTaskEntity(Map<String, String> requestMap) {
		TaskEntity taskEntity = new TaskEntity();
		ParestTaskEntity parentTaskEntity = new ParestTaskEntity();
		taskEntity.setId(requestMap.get("id"));
		taskEntity.setTask(requestMap.get("task"));
		taskEntity.setEndDate(requestMap.get("endDate"));
		taskEntity.setStartDate(requestMap.get("startDate"));
		String parentId = null;
		if ("true".equalsIgnoreCase(requestMap.get("isParentTask"))) {
			parentId = requestMap.get("parentId") != null ? requestMap.get("parentId") : UUID.randomUUID().toString();
			parentTaskEntity.setId(parentId);
			parentTaskEntity.setTaskId(requestMap.get("id"));
			parentTaskEntity.setParentTask(requestMap.get("task"));
			parentTaskRepository.save(parentTaskEntity);
			taskEntity.setParentTask(true);
		}
		taskEntity.setParentId(parentId);
		taskEntity.setStatus(requestMap.get("status"));
		taskEntity.setPriority(Integer.parseInt(requestMap.get("priority")));

		taskEntity.setProjectId(requestMap.get("projectId"));

		taskEntity.setUserId(requestMap.get("userId"));
		return taskEntity;
	}

}
