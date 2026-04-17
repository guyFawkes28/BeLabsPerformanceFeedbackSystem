package org.example.service;

import org.example.dto.EvaluacionRequest;
import org.example.model.Feedback;

public class FeedbackService implements FeedbackGeneratorPort {

    private boolean esAlto(int valor) {
        return valor >= 7;
    }

    // -------------------------
    // Feedback individual
    // -------------------------
    private String feedbackLiderazgo(boolean alto) {
        return alto
                ? "Demuestra una buena capacidad para liderar y guiar al equipo. "
                : "Se recomienda fortalecer sus habilidades de liderazgo para guiar mejor al equipo. ";
    }

    private String feedbackComunicacion(boolean alto) {
        return alto
                ? "Se comunica de forma clara y efectiva con los demás. "
                : "Debe mejorar la claridad y asertividad en su comunicación. ";
    }

    private String feedbackTiempo(boolean alto) {
        return alto
                ? "Gestiona adecuadamente su tiempo y cumple con sus responsabilidades. "
                : "Se sugiere mejorar la organización y gestión del tiempo. ";
    }

    private String feedbackResolucion(boolean alto) {
        return alto
                ? "Resuelve problemas de manera eficiente y oportuna. "
                : "Debe fortalecer sus habilidades para analizar y resolver problemas. ";
    }

    // -------------------------
    // Feedback por combinación
    // -------------------------
    private String feedbackCombinacion(String codigo) {
        switch (codigo) {
            case "0000":
                return "El desempeño general es bajo, se recomienda trabajar de manera integral en todas las competencias.";

            case "0001":
                return "Destaca en la resolución de problemas, pero requiere fortalecer las demás competencias para lograr un desempeño equilibrado.";

            case "0010":
                return "Muestra buena gestión del tiempo, aunque necesita mejorar en liderazgo, comunicación y resolución de problemas.";

            case "0011":
                return "Cuenta con un buen enfoque operativo, destacándose en gestión del tiempo y resolución de problemas.";

            case "0100":
                return "Se destaca en comunicación, pero requiere fortalecer habilidades de liderazgo, organización y resolución de problemas.";

            case "0101":
                return "Presenta fortalezas en comunicación y análisis, lo que favorece la comprensión y solución de situaciones.";

            case "0110":
                return "Muestra buena organización y comunicación, facilitando el trabajo estructurado y la interacción con otros.";

            case "0111":
                return "Posee un perfil colaborativo fuerte, con habilidades destacadas en comunicación, organización y resolución.";

            case "1000":
                return "Tiene iniciativa de liderazgo, pero necesita fortalecer habilidades de apoyo en comunicación, organización y resolución.";

            case "1001":
                return "Combina liderazgo con capacidad de resolución, lo que le permite enfrentar desafíos con iniciativa.";

            case "1010":
                return "Presenta liderazgo y buena organización, lo que favorece la planificación y dirección de tareas.";

            case "1011":
                return "Cuenta con un liderazgo operativo fuerte, integrando organización y resolución de problemas.";

            case "1100":
                return "Demuestra liderazgo comunicativo, facilitando la dirección de equipos mediante una buena interacción.";

            case "1101":
                return "Cuenta con un perfil estratégico, destacándose en liderazgo, comunicación y resolución.";

            case "1110":
                return "Presenta un alto rendimiento general, con oportunidad de fortalecer la resolución de problemas.";

            case "1111":
                return "Presenta un desempeño sobresaliente en todas las áreas evaluadas.";

            default:
                return "Presenta un perfil mixto con oportunidades de mejora específicas.";
        }
    }

    @Override
    public Feedback generarFeedback(EvaluacionRequest request) {

        // 1. Convertir a alto/bajo
        boolean l = esAlto(request.getLiderazgo());
        boolean c = esAlto(request.getComunicacion());
        boolean t = esAlto(request.getTiempo());
        boolean r = esAlto(request.getResolucionProblemas());

        // 2. Feedback individual
        String desarrollo =
                feedbackLiderazgo(l) +
                        feedbackComunicacion(c) +
                        feedbackTiempo(t) +
                        feedbackResolucion(r);

        // 3. Código binario
        String codigo =
                (l ? "1" : "0") +
                        (c ? "1" : "0") +
                        (t ? "1" : "0") +
                        (r ? "1" : "0");

        // 4. Feedback combinación
        desarrollo += " " + feedbackCombinacion(codigo);

        // 5. Introducción y cierre
        String introduccion = "A continuación se presenta un análisis de su desempeño en las competencias evaluadas. ";

        String cierre = "Se recomienda continuar fortaleciendo sus habilidades para alcanzar un desarrollo integral.";

        return new Feedback(introduccion, desarrollo, cierre);
    }
    
    public Feedback editarFeedback(Feedback feedback) {
        return feedback;
    }
}
