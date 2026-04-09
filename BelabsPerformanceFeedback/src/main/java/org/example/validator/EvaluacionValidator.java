package org.example.validator;

import org.example.dto.EvaluacionRequest;

public class EvaluacionValidator implements  EvaluacionValidatorPort{

    @Override
    public void validar(EvaluacionRequest request){

        if (request.getLiderazgo()< 1 || request.getLiderazgo() > 10 ){
            throw new IllegalArgumentException("Note invalide");
        }

        if (request.getComunicacion() <1 || request.getComunicacion()>10){
            throw new IllegalArgumentException("Note invalide");

        }
    }
}
