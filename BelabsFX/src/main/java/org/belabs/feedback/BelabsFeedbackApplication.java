package org.belabs.feedback;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.belabs.feedback.controller.FeedbackController;
import org.belabs.feedback.controller.LaboratoryController;
import org.belabs.feedback.controller.LoginController;
import org.belabs.feedback.controller.MenuController;
import org.belabs.feedback.controller.SkillController;
import org.belabs.feedback.repository.AdminUserRepository;
import org.belabs.feedback.repository.CoderRepository;
import org.belabs.feedback.repository.DatabaseConnection;
import org.belabs.feedback.repository.LaboratoryRepository;
import org.belabs.feedback.repository.SkillRepository;
import org.belabs.feedback.service.AuthService;
import org.belabs.feedback.service.EmailService;
import org.belabs.feedback.service.FeedbackService;
import org.belabs.feedback.service.LaboratoryService;
import org.belabs.feedback.service.SkillService;
import org.belabs.feedback.util.AlertUtil;
import org.belabs.feedback.view.FeedbackView;
import org.belabs.feedback.view.LaboratoryView;
import org.belabs.feedback.view.LoginView;
import org.belabs.feedback.view.MenuView;
import org.belabs.feedback.view.SkillView;

public class BelabsFeedbackApplication extends Application {
    @Override
    public void start(Stage stage) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            databaseConnection.initializeDatabase();
        } catch (Exception exception) {
            AlertUtil.showError("Database Error", exception.getMessage());
            return;
        }

        SkillRepository skillRepository = new SkillRepository(databaseConnection);
        LaboratoryRepository laboratoryRepository = new LaboratoryRepository(databaseConnection);
        CoderRepository coderRepository = new CoderRepository(databaseConnection);
        AdminUserRepository adminUserRepository = new AdminUserRepository(databaseConnection);

        SkillService skillService = new SkillService(skillRepository);
        LaboratoryService laboratoryService = new LaboratoryService(laboratoryRepository, skillRepository);
        FeedbackService feedbackService = new FeedbackService();
        EmailService emailService = new EmailService();
        AuthService authService = new AuthService(adminUserRepository);

        AppNavigator navigator = new AppNavigator(stage);
        LoginController loginController = new LoginController(authService, navigator);
        SkillController skillController = new SkillController(skillService);
        LaboratoryController laboratoryController = new LaboratoryController(laboratoryService, skillService);
        FeedbackController feedbackController = new FeedbackController(feedbackService, emailService, laboratoryService, coderRepository);

        LoginView loginView = new LoginView(loginController);
        MenuView[] menuViewRef = new MenuView[1];
        Runnable backToMenu = () -> navigator.show("Belabs Feedback Generator", menuViewRef[0].getScene());
        SkillView skillView = new SkillView(skillController, backToMenu);
        LaboratoryView laboratoryView = new LaboratoryView(laboratoryController, backToMenu);
        FeedbackView feedbackView = new FeedbackView(feedbackController, backToMenu);
        MenuController menuController = new MenuController(navigator, skillView, laboratoryView, feedbackView);
        MenuView menuView = new MenuView(menuController);
        menuViewRef[0] = menuView;

        loginController.setMenuView(menuView);
        skillController.setView(skillView);
        laboratoryController.setView(laboratoryView);
        feedbackController.setView(feedbackView);

        navigator.show("Belabs Feedback Generator", loginView.getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class AppNavigator {
        private final Stage stage;

        public AppNavigator(Stage stage) {
            this.stage = stage;
        }

        public void show(String title, Scene scene) {
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        }
    }
}
