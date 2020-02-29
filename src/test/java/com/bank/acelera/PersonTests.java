package com.bank.acelera;

import com.bank.acelera.model.Physical;
import com.bank.acelera.repository.PhysicalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PhysicalTests {
 
    @Autowired
    private PhysicalRepository personRepository;
    
    @Test
    public void whenFindByName_thenReturnPhysical() {
        // given
        String name = "alex";
        Physical alex = new Physical();
        alex.setName(name);
        alex.setCpf("123.123.123-53");
        personRepository.save(alex);
        
        // when
        Physical found = personRepository.findByName(alex.getName());

        // then
        Assertions.assertThat(found.getName())
          .isEqualTo(alex.getName());
    }

}
