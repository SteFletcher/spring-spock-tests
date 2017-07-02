package com.stefletcher.spring.repositories

import com.stefletcher.spring.beans.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

/**
 * Created by stefletcher on 02/07/2017.
 */
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByLastName(@Param("name") String name);

}
