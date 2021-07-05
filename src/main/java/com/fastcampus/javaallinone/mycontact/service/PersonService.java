package com.fastcampus.javaallinone.mycontact.service;

import com.fastcampus.javaallinone.mycontact.domain.Person;
import com.fastcampus.javaallinone.mycontact.repository.BlockRepository;
import com.fastcampus.javaallinone.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    public List<Person> getPeopleElcludeBlocks(){
        List<Person> personList = personRepository.findAll();

        return personList.stream().filter(person ->
                person.getBlock() == null)  // 각 사람의 이름을 blockNameList와 비교
                .collect(Collectors.toList());              // 안 걸리는 사람들만 List로
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id){
        Person person = personRepository.findById(id).get();

        log.info("person : {}", person);
        return person;
    }
}
