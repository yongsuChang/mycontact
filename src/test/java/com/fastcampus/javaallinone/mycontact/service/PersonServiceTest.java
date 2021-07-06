package com.fastcampus.javaallinone.mycontact.service;

import com.fastcampus.javaallinone.mycontact.domain.Block;
import com.fastcampus.javaallinone.mycontact.domain.Person;
import com.fastcampus.javaallinone.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.mycontact.repository.BlockRepository;
import com.fastcampus.javaallinone.mycontact.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// .sql파일 사용 불가하여, data.sql파일 사용 안 함
@Transactional
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
        createPersonList();

        List<Person> result = personService.getPeopleExcludeBlocks();

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).getName()).isEqualTo("Martin");
        assertThat(result.get(1).getName()).isEqualTo("David");
        assertThat(result.get(2).getName()).isEqualTo("Benny");
    }

    @Test
    public void getPeopleByName(){
        createPersonList();

        List<Person> result = personService.getPersonListByName("Martin");

        result.forEach(System.out::println);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("Martin");
    }

    @Test
    void getPerson(){
        createPersonList();

        Person person = personService.getPerson(3L);

        assertThat(person.getName()).isEqualTo("Dennis");
    }

    /* 원래는 이 밑으로 다 삭제해야. 그러나 .sql 파일이 안 되어서 남겨 둠 */

    public void createPersonList(){
        givenPerson("Martin", 10, "A", LocalDate.of(1991, 8, 15));
        givenPerson("David", 9, "B", LocalDate.of(1992,7, 21));
        givenBlockPerson("Dennis", 8, "O", LocalDate.of(1993, 10, 15));
        givenBlockPerson("Sophia", 7, "AB", LocalDate.of(1994, 8, 31));
        givenPerson("Benny", 6, "A", LocalDate.of(1995, 12, 23));
    }

    private void givenPerson(String name, int age, String bloodType, LocalDate birthday){
        Person person = new Person(name, age, bloodType);
        person.setBirthday(new Birthday(birthday));
        personRepository.save(person);
    }

    private void givenBlockPerson(String name, int age, String bloodType, LocalDate birthday){
        Person blockPerson = new Person(name, age, bloodType);
        // Person 클래스와 Block 클래스의 관계에 CascadeType.PERSIST있어서 가능
        blockPerson.setBlock(new Block(name));
        blockPerson.setBirthday(new Birthday(birthday));

        personRepository.save(blockPerson);
    }
}