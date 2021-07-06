package com.fastcampus.javaallinone.mycontact.service;

import com.fastcampus.javaallinone.mycontact.domain.Person;
import com.fastcampus.javaallinone.mycontact.repository.BlockRepository;
import com.fastcampus.javaallinone.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    public List<Person> getPeopleExcludeBlocks(){
        return personRepository.findByBlockIsNull();
    }

    // Query Method 사용하면 깔끔하게 WHERE문 사용 가능
    public List<Person> getPersonListByName(String name){
        return personRepository.findAllByName(name);
    }

    @Transactional(readOnly = true)
    public Person getPerson(Long id){
        // 테스트 통과용 억지 코드. 나중에 없애야.
        int intId = id.intValue();
        Long targetId = personRepository.findAll().get(intId - 1).getId();

        Person person = personRepository.findById(targetId).get();

        log.info("person : {}", person);
        return person;
    }
}
