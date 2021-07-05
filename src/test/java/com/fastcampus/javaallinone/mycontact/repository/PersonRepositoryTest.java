package com.fastcampus.javaallinone.mycontact.repository;

import com.fastcampus.javaallinone.mycontact.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}