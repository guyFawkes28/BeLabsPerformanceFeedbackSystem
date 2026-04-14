package org.example.service;

import org.example.model.Feedback;

public class JsonFeedbackService {

    public Feedback desdeJson(String json) {

        String intro = extraer(json, "introduccion");
        String desarrollo = extraer(json, "desarrollo");
        String cierre = extraer(json, "cierre");

        return new Feedback(intro, desarrollo, cierre);
    }

    private String extraer(String json, String campo) {
        String clave = "\"" + campo + "\":";
        int inicio = json.indexOf(clave) + clave.length();
        int fin = json.indexOf("\"", inicio + 2);
        return json.substring(inicio + 2, fin);
    }
}