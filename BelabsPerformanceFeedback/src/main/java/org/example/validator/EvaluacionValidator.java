package org.example.validator;

import org.example.dto.EvaluacionRequest;

public class EvaluacionValidator {
    public static boolean isValid(EvaluacionRequest request) {
        int l = request.getLiderazgo();
        int c = request.getComunicacion();
        int t = request.getTiempo();
        int r = request.getResolucionProblemas();
        return (l >= 1 && l <= 10) && (c >= 1 && c <= 10) && (t >= 1 && t <= 10) && (r >= 1 && r <= 10);
    }
}
