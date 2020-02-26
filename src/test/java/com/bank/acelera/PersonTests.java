package com.bank.acelera;

import com.bank.acelera.model.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.bank.acelera.repository.PersonRepository;

@SpringBootTest
class PersonTests {
 
    @Autowired
    private PersonRepository personRepository;
    
    @Test
    public void whenFindByName_thenReturnPerson() {
        // given
        String name = "alex";
        Person alex = new Person();
        alex.setName(name);
        personRepository.save(alex);

        // when
        Person found = personRepository.findByName(alex.getName());

        // then
        Assertions.assertThat(found.getName())
          .isEqualTo(name);
    }

}
