package org.example.validator;

import org.example.dto.EvaluacionRequest;

public class EvaluacionValidator implements  EvaluacionValidatorPort{

    @Override
    public void validar(EvaluacionRequest request){

        if (request.getLiderazgo() < 1 || request.getLiderazgo() > 10) {
            throw new IllegalArgumentException("Liderazgo should be between 1 and 10");
        }

        if (request.getComunicacion() < 1 || request.getComunicacion() > 10) {
            throw new IllegalArgumentException("Comunicacion should be between 1 and 10");
        }

        if (request.getTiempo() < 1 || request.getTiempo() > 10) {
            throw new IllegalArgumentException("Tiempo should be between 1 and 10 ");
        }

        if (request.getResolucionProblemas() < 1 || request.getResolucionProblemas() > 10) {
            throw new IllegalArgumentException("Resolucion de problemas should be between 1 and 10");
        }

        }
    }

