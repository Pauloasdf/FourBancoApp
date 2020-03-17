package com.bank.acelera.repository;

import com.bank.acelera.repository.person.PhysicalRepository;
import com.bank.acelera.model.Physical;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonRepositoryTests {
 
    @Autowired
    private PhysicalRepository physicalRepository;
    
    @Test
    public void whenFindByName_thenReturnPhysical() {
        // given
        String name = "alex";
        Physical physical = new Physical();
        physical.setName(name);
        physical.setCpf("123.123.123-53");
        physicalRepository.save(physical);
        
        // when
        Physical found = physicalRepository.findByName(physical.getName());

        // then
        Assertions.assertThat(found.getName())
          .isEqualTo(physical.getName());
    }
}
