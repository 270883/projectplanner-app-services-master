/**
 * 
 */
package com.fsd.program.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.fsd.program.entity.ParestTaskEntity;
import com.fsd.program.entity.ProjectEntity;
import com.fsd.program.entity.TaskEntity;
import com.fsd.program.entity.UserEntity;
import com.fsd.program.repo.ParentTaskRepo;
import com.fsd.program.repo.ProjectRepo;
import com.fsd.program.repo.TaskRepo;
import com.fsd.program.repo.UserRepo;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

/**
 * @author KarthickM
 *
 */
@Configuration
@Profile("junit-tests")
@ComponentScan(basePackages = { "com.fsd.program.services", "com.fsd.program.repo" })
public class TestConfig {

	@Bean
	@Primary
	public UserRepo userRepository() throws IOException {
		UserRepo mock = Mockito.spy(UserRepo.class);
		String contentFromJSON = new String(Files.readAllBytes(Paths.get("src/test/resources/users.json")));
		Gson gson = new Gson();
		@SuppressWarnings("unchecked")
		List<UserEntity> users = gson.fromJson(contentFromJSON, List.class);
		Mockito.when(mock.findAll()).thenReturn(users);
		Mockito.when(mock.save(Mockito.any(UserEntity.class))).thenReturn(new UserEntity());
		Mockito.doNothing().when(mock).delete(Mockito.any(UserEntity.class));

		String testUserContentFromJSON = new String(Files.readAllBytes(Paths.get("src/test/resources/testUser.json")));
		UserEntity testuser = gson.fromJson(testUserContentFromJSON, UserEntity.class);
		Optional<UserEntity> optionaluser = Optional.of(testuser);
		Mockito.when(mock.findById("1111")).thenReturn(optionaluser);

		Optional<UserEntity> optionaluser2 = Optional.ofNullable(null);
		Mockito.when(mock.findById("2222")).thenReturn(optionaluser2);

		return mock;
	}

	@Bean
	@Primary
	public ProjectRepo projectRepository() throws IOException {
		ProjectRepo mock = Mockito.spy(ProjectRepo.class);
		String contentFromJSON = new String(Files.readAllBytes(Paths.get("src/test/resources/projects.json")));
		Gson gson = new Gson();
		List<ProjectEntity> projects = new ArrayList<>();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<LinkedTreeMap> tasksJson = gson.fromJson(contentFromJSON, List.class);
		for (LinkedTreeMap<?, ?> jsonElement : tasksJson) {
			projects.add(gson.fromJson(gson.toJson(jsonElement), ProjectEntity.class));
		}
		Mockito.when(mock.findAll()).thenReturn(projects);
		Mockito.when(mock.findByManagerId("1111111")).thenReturn(projects);
		Mockito.when(mock.save(Mockito.any(ProjectEntity.class))).thenReturn(new ProjectEntity());
		Mockito.doNothing().when(mock).delete(Mockito.any(ProjectEntity.class));
		String projectContentFromJSON = new String(
				Files.readAllBytes(Paths.get("src/test/resources/testProject.json")));
		ProjectEntity project = gson.fromJson(projectContentFromJSON, ProjectEntity.class);
		Optional<ProjectEntity> optionalProject = Optional.ofNullable(project);
		Mockito.when(mock.findById(Mockito.anyString())).thenReturn(optionalProject);
		return mock;
	}

	@Bean
	@Primary
	public TaskRepo taskRepository() throws IOException {
		TaskRepo mock = Mockito.spy(TaskRepo.class);
		String contentFromJSON = new String(Files.readAllBytes(Paths.get("src/test/resources/tasks.json")));
		Gson gson = new Gson();
		List<TaskEntity> tasks = new ArrayList<>();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<LinkedTreeMap> tasksJson = gson.fromJson(contentFromJSON, List.class);
		for (LinkedTreeMap<?, ?> jsonElement : tasksJson) {
			tasks.add(gson.fromJson(gson.toJson(jsonElement), TaskEntity.class));
		}
		Mockito.when(mock.findAll()).thenReturn(tasks);

		Mockito.when(mock.save(Mockito.any(TaskEntity.class))).thenReturn(new TaskEntity());
		
		Mockito.when(mock.findByProjectId("1111111")).thenReturn(tasks);
		
		Mockito.when(mock.findByUserId("1111111")).thenReturn(tasks);
		
		Mockito.doNothing().when(mock).delete(Mockito.any(TaskEntity.class));
		return mock;
	}

	@Bean
	@Primary
	public ParentTaskRepo parentTaskRepository() throws IOException {
		ParentTaskRepo mock = Mockito.spy(ParentTaskRepo.class);
		String contentFromJSON = new String(Files.readAllBytes(Paths.get("src/test/resources/parentTasks.json")));
		Gson gson = new Gson();
		@SuppressWarnings("unchecked")
		List<ParestTaskEntity> parentTasks = gson.fromJson(contentFromJSON, List.class);
		Mockito.when(mock.findAll()).thenReturn(parentTasks);

		Mockito.when(mock.save(Mockito.any(ParestTaskEntity.class))).thenReturn(new ParestTaskEntity());
		String parentTaskContentFromJSON = new String(
				Files.readAllBytes(Paths.get("src/test/resources/parentTask.json")));
		ParestTaskEntity parentTask = gson.fromJson(parentTaskContentFromJSON, ParestTaskEntity.class);
		Optional<ParestTaskEntity> optionalPTask = Optional.ofNullable(parentTask);
		Mockito.when(mock.findById(Mockito.anyString())).thenReturn(optionalPTask);

		return mock;
	}

}
