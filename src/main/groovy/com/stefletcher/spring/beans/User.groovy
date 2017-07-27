package com.stefletcher.spring.beans

import org.joda.time.DateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.mongodb.core.mapping.Document


@Document
public class User {

    @Id String id;

    String firstName
    String lastName

    @CreatedDate
    Date createdDate
    @LastModifiedDate
    Date lastModifiedDate

    String email


}