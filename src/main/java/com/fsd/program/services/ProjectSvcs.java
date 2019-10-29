/**
 * 
 */
package com.fsd.program.services;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.program.entity.ProjectEntity;
import com.fsd.program.entity.TaskEntity;
import com.fsd.program.entity.UserEntity;
import com.fsd.program.repo.ProjectRepo;
import com.fsd.program.repo.TaskRepo;
import com.fsd.program.repo.UserRepo;

/**
 * @author KarthickM
 *
 */
@RestController
@RequestMapping("/projects")
public class ProjectSvcs {

	private static final Logger logger = LoggerFactory.getLogger(ProjectSvcs.class);

	@Autowired
	private ProjectRepo projectRepository;

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private TaskRepo taskRepository;

	@RequestMapping("/getProjects")
	public List<ProjectEntity> getProjects() {
		logger.info("Method getproject() executed");
		List<ProjectEntity> projects = projectRepository.findAll();
		projects.stream().forEach((project) -> {
			String projectId = project.getId();
			List<TaskEntity> tasks = taskRepository.findByProjectId(projectId);
			project.setTasksCount(tasks.size());

		});
		return projects;
	}

	@RequestMapping("/addUpdate")
	public List<ProjectEntity> addUpdateProject(@RequestBody Map<String, String> requestMap) throws ParseException {

		ProjectEntity projectEntity = new ProjectEntity();
		projectEntity.setId(requestMap.get("id"));
		projectEntity.setEndDate(requestMap.get("endDate"));
		projectEntity.setStartDate(requestMap.get("startDate"));
		projectEntity.setProjectname(requestMap.get("projectName"));
		projectEntity.setPriority(Integer.parseInt(requestMap.get("priority")));

		String managerId = requestMap.get("managerId");
		UserEntity user = userRepository.findById(managerId).get();
		projectEntity.setManagerId(managerId);
		projectEntity.setManagerName(user.getFirstName() + " " + user.getLastName());
		projectRepository.save(projectEntity);
		return projectRepository.findAll();
	}

	@RequestMapping("/deleteProject")
	public List<ProjectEntity> deleteProject(@RequestBody ProjectEntity project) {
		projectRepository.delete(project);
		return projectRepository.findAll();
	}

}
