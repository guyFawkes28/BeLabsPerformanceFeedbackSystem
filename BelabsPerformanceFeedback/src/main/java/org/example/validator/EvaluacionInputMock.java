package org.example.validator;

import org.example.dto.EvaluacionRequest;

public class EvaluacionInputMock implements EvaluacionInputPort{
    @Override
    public EvaluacionRequest capturarEvaluacion(){
        return new EvaluacionRequest(8,7,7,6,"nose@gmail.com");
    }
}
