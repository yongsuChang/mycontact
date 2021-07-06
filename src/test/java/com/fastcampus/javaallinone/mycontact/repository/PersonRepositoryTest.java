package com.fastcampus.javaallinone.mycontact.repository;

import com.fastcampus.javaallinone.mycontact.domain.Person;
import com.fastcampus.javaallinone.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void crud(){
        Person person = new Person();
        person.setName("Martin");
        person.setAge(10);
        person.setBloodType("A");
        personRepository.save(person);

        System.out.println(personRepository.findAll());

        List<Person> people = personRepository.findAll();
        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo("Martin");
        assertThat(people.get(0).getAge()).isEqualTo(10);
        assertThat(people.get(0).getBloodType()).isEqualTo("A");
    }

    @Test
    public void hashCodeAndEquals(){
        Person person1 = new Person("Martin",10, "A");
        Person person2 = new Person("Martin",10, "A");

        System.out.println(person1.equals(person2));    // 임의의 equals 함수를 통해 같다고 판단
        System.out.println(person1.hashCode());         // 그러나 hash code는 다름
        System.out.println(person2.hashCode());

        Map<Person, Integer> map = new HashMap<>();
        map.put(person1, person1.getAge());
        System.out.println(map);
        System.out.println(map.get(person2));
    }

    @Test
    void findByBloodType(){
        createPersonList();

        List<Person> result = personRepository.findByBloodType("A");
        result.forEach(System.out::println);
    }

    @Test
    void findByBirthdayBetween(){
        createPersonList();

        List<Person> reuslt =
                personRepository.findByMonthOfBirthday(8);

        reuslt.forEach(System.out::println);
    }

    private void createPersonList(){
        givenPerson("Martin", 10, "A", LocalDate.of(1991, 8, 30));
        givenPerson("David", 9, "B", LocalDate.of(1992,7, 10));
        givenPerson("Dennis", 8, "O", LocalDate.of(1993, 1, 5));
        givenPerson("Sophia", 7, "AB", LocalDate.of(1994, 6, 30));
        givenPerson("Benny", 6, "A", LocalDate.of(1995, 8, 30));
    }

    private void givenPerson(String name, int age, String bloodType, LocalDate birthday){
        Person person = new Person(name, age, bloodType);
        person.setBirthday(new Birthday(birthday));
        personRepository.save(person);
    }
}