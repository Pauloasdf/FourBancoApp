package com.bank.acelera.controller.converter;

import com.bank.acelera.controller.request.PersonRequest;
import com.bank.acelera.model.Legal;
import com.bank.acelera.model.Physical;
import com.bank.acelera.model.abstrac.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter {

    public Person converter(PersonRequest personRequest) {
        if(personRequest.getCpf() != null ){
            return this.makePhysical(personRequest);
        }
                
        if(personRequest.getCnpj() != null){
            return this.makeLegal(personRequest);
        }
        
        return null;
    }
    
    private Physical makePhysical(PersonRequest personRequest){
        return new Physical(personRequest.getName(), personRequest.getCpf());
    }
    
    private Legal makeLegal(PersonRequest personRequest){
        return new Legal(personRequest.getName(), personRequest.getCnpj());
    }    
}
