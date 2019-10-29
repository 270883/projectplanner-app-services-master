/**
 * 
 */
package com.fsd.program.services;

import java.util.List;

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
@RequestMapping("/users")
public class UserSvcs {

	private static final Logger logger = LoggerFactory.getLogger(UserSvcs.class);

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private TaskRepo taskRepository;

	@Autowired
	private ProjectRepo projectRepository;

	@RequestMapping("/getAllUsers")
	public List<UserEntity> getAllUsers() {
		logger.info("Method getAllUsers() executed");
		return userRepository.findAll();
	}

	@RequestMapping("/addUpdate")
	public List<UserEntity> addUpdateUser(@RequestBody UserEntity user) {
		userRepository.save(user);
		return userRepository.findAll();
	}

	@RequestMapping("/deleteUser")
	public List<UserEntity> deleteUser(@RequestBody UserEntity user) {
		userRepository.delete(user);
		List<TaskEntity> tasks = taskRepository.findByUserId(user.getId());
		for (TaskEntity task : tasks) {
			task.setUserId("");
			taskRepository.save(task);
		}
		List<ProjectEntity> projects = projectRepository.findByManagerId(user.getId());
		for (ProjectEntity project : projects) {
			project.setManagerId("");
			project.setManagerName("");
			projectRepository.save(project);
		}
		return userRepository.findAll();
	}

}
