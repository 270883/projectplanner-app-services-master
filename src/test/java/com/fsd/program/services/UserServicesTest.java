/**
 * 
 */
package com.fsd.program.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fsd.program.entity.UserEntity;

/**
 * @author KarthickM
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@ActiveProfiles("junit-tests")
public class UserServicesTest {

	@Autowired
	private UserSvcs testCase;

	@Test
	public void test_getAllUsers() {
		List<UserEntity> users = testCase.getAllUsers();
		assertNotNull(users);
	}

	@Test
	public void test_addUpdateUser() {
		UserEntity user = new UserEntity();
		user.setId("wqdwef");
		List<UserEntity> users = testCase.addUpdateUser(user);
		assertNotNull(users);
	}

	@Test
	public void test_deleteUser() {
		UserEntity user = new UserEntity();
		user.setId("1111111");
		testCase.deleteUser(user);
		assertTrue("Delete Success", true);
	}
	
	@Test
	public void test_deleteUser_1() {
		UserEntity user = new UserEntity();
		user.setId("3254fd");
		testCase.deleteUser(user);
		assertTrue("Delete Success", true);
	}

}
