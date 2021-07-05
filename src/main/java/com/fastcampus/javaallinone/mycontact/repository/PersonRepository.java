package com.fastcampus.javaallinone.mycontact.repository;

import com.fastcampus.javaallinone.mycontact.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
