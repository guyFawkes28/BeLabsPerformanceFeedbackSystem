package org.example.service;

import org.example.model.Feedback;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileFeedbackService {

    public Feedback leerTxt(Path path) throws IOException {
        List<String> lineas = Files.readAllLines(path);

        if (lineas.size() < 3) {
            throw new IllegalArgumentException("El archivo TXT debe tener 3 líneas: introducción, desarrollo y cierre.");
        }

        return new Feedback(
                lineas.get(0),
                lineas.get(1),
                lineas.get(2)
        );
    }

    public void guardarTxt(Path path, Feedback feedback) throws IOException {
        Files.write(path, List.of(
                feedback.getIntroduccion(),
                feedback.getDesarrollo(),
                feedback.getCierre()
        ));
    }
}