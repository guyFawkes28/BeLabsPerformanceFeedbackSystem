package org.belabs.feedback.controller;

import org.belabs.feedback.BelabsFeedbackApplication.AppNavigator;
import org.belabs.feedback.service.AuthService;
import org.belabs.feedback.util.AlertUtil;
import org.belabs.feedback.view.MenuView;

public class LoginController {
    private final AuthService authService;
    private final AppNavigator navigator;
    private MenuView menuView;

    public LoginController(AuthService authService, AppNavigator navigator) {
        this.authService = authService;
        this.navigator = navigator;
    }

    public void setMenuView(MenuView menuView) {
        this.menuView = menuView;
    }

    public void handleLogin(String email, String password) {
        if (authService.login(email, password)) {
            navigator.show("Belabs Feedback Generator", menuView.getScene());
        } else {
            AlertUtil.showError("Login", "Credenciales invalidas.");
        }
    }
}
