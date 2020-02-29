package com.bank.acelera.controller.transaction.converter;

import com.bank.acelera.controller.transaction.request.MovementRequest;
import com.bank.acelera.model.Movement;
import org.springframework.stereotype.Component;

@Component
public class MovementConverter {

    public Movement movementConverter(MovementRequest movementRequest) {
        Movement movement = new Movement(
                movementRequest.getValue(),
                this.getType(movementRequest.getType())
        );

        return movement;

    }

    /**
     * Converts MovementRequest Type attribute to an Movement Type attribute
     *
     * @param type
     * @return
     */
    private Movement.Type getType(Integer type){
        return Movement.Type.values()[type];
    }
}
