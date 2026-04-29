package org.belabs.feedback.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.belabs.feedback.controller.LoginController;

public class LoginView {
    private final Scene scene;

    public LoginView(LoginController controller) {
        Label title = new Label("Belabs Feedback Generator");
        Label hint = new Label("Usuario inicial: admin@belabs.com / admin123");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Ingresar");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setOnAction(event -> controller.handleLogin(emailField.getText(), passwordField.getText()));

        VBox root = new VBox(10, title, hint, emailField, passwordField, loginButton);
        root.setPadding(new Insets(20));
        this.scene = new Scene(root, 420, 220);
    }

    public Scene getScene() {
        return scene;
    }
}
