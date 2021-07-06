package com.fastcampus.javaallinone.mycontact.repository;

import com.fastcampus.javaallinone.mycontact.domain.Person;
import com.fastcampus.javaallinone.mycontact.domain.dto.Birthday;
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
public
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void crud(){
        Person person = new Person();
        person.setName("John");
        person.setAge(10);
        person.setBloodType("A");
        personRepository.save(person);

        List<Person> result = personRepository.findByName("John");
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("John");
        assertThat(result.get(0).getAge()).isEqualTo(10);
        assertThat(result.get(0).getBloodType()).isEqualTo("A");

    }


    @Test
    void findByBloodType(){
        createPersonList();
        List<Person> result = personRepository.findByBloodType("A");

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("Martin");
        assertThat(result.get(1).getName()).isEqualTo("Benny");
    }

    @Test
    void findByBirthdayBetween(){
        createPersonList();

        List<Person> result =
                personRepository.findByMonthOfBirthday(8);

        result.forEach(System.out::println);
    }

    /* 원래는 이 밑으로 다 삭제해야. 그러나 .sql 파일이 안 되어서 남겨 둠 */

    public void createPersonList(){
        givenPerson("Martin", 10, "A", LocalDate.of(1991, 8, 15));
        givenPerson("David", 9, "B", LocalDate.of(1992,7, 21));
        givenPerson("Dennis", 8, "O", LocalDate.of(1993, 10, 15));
        givenPerson("Sophia", 7, "AB", LocalDate.of(1994, 8, 31));
        givenPerson("Benny", 6, "A", LocalDate.of(1995, 12, 23));
    }

    private void givenPerson(String name, int age, String bloodType, LocalDate birthday){
        Person person = new Person(name, age, bloodType);
        person.setBirthday(new Birthday(birthday));
        personRepository.save(person);
    }
}