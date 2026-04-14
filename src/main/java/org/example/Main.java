package org.example;

import org.example.model.Feedback;
import org.example.repository.InMemoryFeedbackRepository;
import org.example.service.FeedbackService;
import org.example.service.FileFeedbackService;
import org.example.service.JsonFeedbackService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        var repo = new InMemoryFeedbackRepository();
        var feedbackService = new FeedbackService(repo);
        var jsonService = new JsonFeedbackService();
        var fileService = new FileFeedbackService();

        System.out.println("=== PRUEBA DESDE JSON ===");

        String json = """
        {
            "introduccion": "Buen inicio",
            "desarrollo": "Comunicación aceptable",
            "cierre": "Debe mejorar"
        }
        """;

        Feedback feedbackJson = jsonService.desdeJson(json);

        feedbackJson = feedbackService.editarSeccion(
                feedbackJson,
                "desarrollo",
                "Mejoró mucho la claridad del mensaje"
        );

        System.out.println(feedbackJson.toSingleText());

        System.out.println("\n=== PRUEBA DESDE TXT ===");

        Path archivoEntrada = Path.of("feedback.txt");

        Files.write(archivoEntrada, List.of(
                "Participó activamente al inicio",
                "Debe organizar mejor sus ideas",
                "Tiene potencial de mejora"
        ));

        Feedback feedbackTxt = fileService.leerTxt(archivoEntrada);

        feedbackTxt = feedbackService.editarSeccion(
                feedbackTxt,
                "cierre",
                "Se recomienda continuar practicando y reforzar su comunicación."
        );

        Path archivoSalida = Path.of("feedback_editado.txt");
        fileService.guardarTxt(archivoSalida, feedbackTxt);

        System.out.println(feedbackTxt.toSingleText());
        System.out.println("\nArchivo guardado en: " + archivoSalida.toAbsolutePath());
    }
}