package com.bank.acelera.controller.converter;

import com.bank.acelera.controller.request.MovementRequest;
import com.bank.acelera.controller.response.MovementResponse;
import com.bank.acelera.model.Movement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Formats the movements list
     *
     * @param movements
     * @return
     */
    public List<MovementResponse> converterList(List<Movement> movements) {
        List<MovementResponse> movementResponses = new ArrayList<>();

        for (Movement movement:
             movements) {
            MovementResponse movementResponse = new MovementResponse();
            movementResponse.setData(movement.getDate());
            movementResponse.setType(movement.getType().ordinal());
            movementResponse.setValue(movement.getValue());

            movementResponses.add(movementResponse);
        }

        return movementResponses;
    }
}
