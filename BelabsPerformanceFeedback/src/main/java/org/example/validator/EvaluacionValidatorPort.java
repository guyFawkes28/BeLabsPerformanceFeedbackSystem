package org.example.validator;

import org.example.dto.EvaluacionRequest;

public interface EvaluacionValidatorPort {
    void validar(EvaluacionRequest request);
}
