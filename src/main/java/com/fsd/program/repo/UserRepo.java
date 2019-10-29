/**
 * 
 */
package com.fsd.program.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fsd.program.entity.UserEntity;

/**
 * @author KarthickM
 *
 */
public interface UserRepo extends MongoRepository<UserEntity, String> {

}
