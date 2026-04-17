package org.example;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.example.dto.EvaluacionRequest;
import org.example.model.Feedback;
import org.example.service.FeedbackService;
import org.example.validator.EvaluacionValidator;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ingresarNotasYProcesar();
        });
    }

    private static void ingresarNotasYProcesar() {
        String input;
        int liderazgo = 0, comunicacion = 0, tiempo = 0, resolucionProblemas = 0;
        String email = "";

        // Ingreso Liderazgo
        while (liderazgo == 0) {
            input = JOptionPane.showInputDialog(null, "Ingrese nota para Liderazgo (1-10):");
            if (input == null) {
                System.exit(0);
            }
            try {
                liderazgo = Integer.parseInt(input.trim());
                if (liderazgo < 1 || liderazgo > 10) {
                    JOptionPane.showMessageDialog(null, "La nota debe estar entre 1 y 10.", "Error", JOptionPane.ERROR_MESSAGE);
                    liderazgo = 0;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Ingreso Comunicación
        while (comunicacion == 0) {
            input = JOptionPane.showInputDialog(null, "Ingrese nota para Comunicación (1-10):");
            if (input == null) System.exit(0);
            try {
                comunicacion = Integer.parseInt(input.trim());
                if (comunicacion < 1 || comunicacion > 10) {
                    JOptionPane.showMessageDialog(null, "La nota debe estar entre 1 y 10.", "Error", JOptionPane.ERROR_MESSAGE);
                    comunicacion = 0;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Ingreso Tiempo
        while (tiempo == 0) {
            input = JOptionPane.showInputDialog(null, "Ingrese nota para Gestión del Tiempo (1-10):");
            if (input == null) System.exit(0);
            try {
                tiempo = Integer.parseInt(input.trim());
                if (tiempo < 1 || tiempo > 10) {
                    JOptionPane.showMessageDialog(null, "La nota debe estar entre 1 y 10.", "Error", JOptionPane.ERROR_MESSAGE);
                    tiempo = 0;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Ingreso Resolución de Problemas
        while (resolucionProblemas == 0) {
            input = JOptionPane.showInputDialog(null, "Ingrese nota para Resolución de Problemas (1-10):");
            if (input == null) System.exit(0);
            try {
                resolucionProblemas = Integer.parseInt(input.trim());
                if (resolucionProblemas < 1 || resolucionProblemas > 10) {
                    JOptionPane.showMessageDialog(null, "La nota debe estar entre 1 y 10.", "Error", JOptionPane.ERROR_MESSAGE);
                    resolucionProblemas = 0;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Email
        email = JOptionPane.showInputDialog(null, "Ingrese email:");
        if (email == null) email = "";

        // Crear request y validar
        EvaluacionRequest request = new EvaluacionRequest(liderazgo, comunicacion, tiempo, resolucionProblemas, email);
        if (!EvaluacionValidator.isValid(request)) {
            JOptionPane.showMessageDialog(null, "Error en validación: Todas las notas deben ser del 1 al 10.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Generar feedback
        FeedbackService service = new FeedbackService();
        Feedback feedback = service.generarFeedback(request);

        // Mostrar feedback
        String fullFeedback = "INTRODUCCIÓN:\n" + feedback.getIntroduccion() + 
                             "\n\nDESARROLLO:\n" + feedback.getDesarrollo() + 
                             "\n\nCIERRE:\n" + feedback.getCierre();
        JOptionPane.showMessageDialog(null, fullFeedback, "Feedback Generado", JOptionPane.INFORMATION_MESSAGE);

        // Opción editar
        Object[] editOptions = {"Editar", "Continuar"};
        int editChoice = JOptionPane.showOptionDialog(
            null, 
            "¿Desea editar el feedback?", 
            "Editar Feedback", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            editOptions, 
            editOptions[1]
        );

        if (editChoice == JOptionPane.YES_OPTION) {
            editarFeedback(feedback);
            // Mostrar actualizado
            fullFeedback = "INTRODUCCIÓN:\n" + feedback.getIntroduccion() + 
                          "\n\nDESARROLLO:\n" + feedback.getDesarrollo() + 
                          "\n\nCIERRE:\n" + feedback.getCierre();
            JOptionPane.showMessageDialog(null, fullFeedback, "Feedback Editado", JOptionPane.INFORMATION_MESSAGE);
        }

        // Simular envío
        int sendChoice = JOptionPane.showConfirmDialog(
            null, 
            "Enviado feedback por correo a " + email + "?", 
            "Enviar Feedback", 
            JOptionPane.YES_NO_OPTION
        );
        if (sendChoice == JOptionPane.YES_OPTION) {
            service.editarFeedback(feedback); // No-op, pero simula uso
            JOptionPane.showMessageDialog(null, "¡Envío simulado completado a " + email + "!", "Enviado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Proceso cancelado.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void editarFeedback(Feedback feedback) {
        // Editar Introducción
        JTextArea introArea = new JTextArea(feedback.getIntroduccion(), 5, 50);
        introArea.setLineWrap(true);
        JScrollPane introScroll = new JScrollPane(introArea);
        int introResult = JOptionPane.showConfirmDialog(
            null, 
            introScroll, 
            "Editar Introducción", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );
        if (introResult == JOptionPane.OK_OPTION) {
            feedback.setIntroduccion(introArea.getText());
        }

        // Editar Desarrollo
        JTextArea devArea = new JTextArea(feedback.getDesarrollo(), 15, 50);
        devArea.setLineWrap(true);
        JScrollPane devScroll = new JScrollPane(devArea);
        int devResult = JOptionPane.showConfirmDialog(
            null, 
            devScroll, 
            "Editar Desarrollo", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );
        if (devResult == JOptionPane.OK_OPTION) {
            feedback.setDesarrollo(devArea.getText());
        }

        // Editar Cierre
        JTextArea cierreArea = new JTextArea(feedback.getCierre(), 5, 50);
        cierreArea.setLineWrap(true);
        JScrollPane cierreScroll = new JScrollPane(cierreArea);
        int cierreResult = JOptionPane.showConfirmDialog(
            null, 
            cierreScroll, 
            "Editar Cierre", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );
        if (cierreResult == JOptionPane.OK_OPTION) {
            feedback.setCierre(cierreArea.getText());
        }
    }
}
