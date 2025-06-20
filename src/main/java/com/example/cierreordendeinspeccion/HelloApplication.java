package com.example.cierreordendeinspeccion;

import com.example.cierreordendeinspeccion.Boundary.PantallaOrdenInspeccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpaUnit");
        EntityManager em = emf.createEntityManager();

        // Campos de entrada
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(250);
        usernameField.setPrefHeight(35);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(250);
        passwordField.setPrefHeight(35);

        Button loginButton = new Button("Iniciar sesión");
        loginButton.setPrefWidth(311);
        loginButton.setPrefHeight(35);
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            try {
                // Verificar el usuario en la base de datos
                Usuario usuario = em.createQuery(
                                "SELECT u FROM Usuario u WHERE u.username = :username AND u.password = :password",
                                Usuario.class)
                        .setParameter("username", username)
                        .setParameter("password", password)
                        .getSingleResult();

                // Si el usuario existe, abrir la nueva ventana de bienvenida
                if (usuario != null) {
                    showAlert(Alert.AlertType.INFORMATION,
                            "¡Éxito!",
                            "Inicio de sesión exitoso. Bienvenido " + usuario.getUsername());
                    PantallaOrdenInspeccion pantallaOrdenInspeccion = new PantallaOrdenInspeccion();
                    pantallaOrdenInspeccion.habilitarVentana(usuario.getUsername());
                    primaryStage.close();
                }

            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR,
                        "Error",
                        "Credenciales incorrectas.");
            }
        });

        VBox vbox = new VBox(20, usernameField, passwordField, loginButton);
        vbox.setPadding(new Insets(40));
        vbox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(vbox);
        root.setPrefSize(400, 400);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Aplicación de Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch();
    }
}