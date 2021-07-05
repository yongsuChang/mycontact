package com.fastcampus.javaallinone.mycontact.service;

import com.fastcampus.javaallinone.mycontact.domain.Block;
import com.fastcampus.javaallinone.mycontact.domain.Person;
import com.fastcampus.javaallinone.mycontact.repository.BlockRepository;
import com.fastcampus.javaallinone.mycontact.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private BlockRepository blockRepository;

    @Test
    public void getPeopleExcludeBlocks(){
        givenPersonList();

        List<Person> result = personService.getPeopleElcludeBlocks();
//        System.out.println(result);
        result.forEach(System.out::println);
    }

    @Test
    void cascadeTest(){
        givenPersonList();
        List<Person> result = personRepository.findAll();

        result.forEach(System.out::println);

        // CascadeType.Merge 테스트
        Person person = result.get(3);
        person.getBlock().setStartDate(LocalDate.now());
        person.getBlock().setEndDate(LocalDate.now());
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);

        // CascadeType.REMOVE 테스트
//        personRepository.delete(person);
//        personRepository.findAll().forEach(System.out::println);
//        blockRepository.findAll().forEach(System.out::println);

        person.setBlock(null);
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);
        blockRepository.findAll().forEach(System.out::println);
    }

    @Test
    void getPerson(){
        givenPersonList();
        Person person = personService.getPerson(3L);
        System.out.println(person);
    }

    private void givenPersonList() {
        givenPerson("Martin", 10, "A");
        givenPerson("David", 9, "B");
        givenBlockPerson("Dennis", 7, "O");
        givenBlockPerson("Martin", 11, "AB");
    }

    private void givenPerson(String name, int age, String bloodType) {
        personRepository.save(new Person(name, age, bloodType));

    }

    private void givenBlockPerson(String name, int age, String bloodType){
        Person blockPerson = new Person(name, age, bloodType);
        // Person 클래스와 Block 클래스의 관계에 CascadeType.PERSIST있어서 가능
        blockPerson.setBlock(new Block(name));

        personRepository.save(blockPerson);
    }
}