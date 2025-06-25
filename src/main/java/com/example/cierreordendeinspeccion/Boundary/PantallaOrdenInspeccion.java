package com.example.cierreordendeinspeccion.Boundary;

import com.example.cierreordendeinspeccion.Controller.GestorOrdenInspeccion;
import com.example.cierreordendeinspeccion.Entity.OrdenInspeccion;
import jakarta.persistence.EntityManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PantallaOrdenInspeccion {

    public void habilitarVentana(EntityManager em, String username) {
        Stage welcomeStage = new Stage();
        welcomeStage.setTitle("Bienvenido");

        // Crear la interfaz de la nueva ventana
        VBox welcomeVBox = new VBox(20);
        welcomeVBox.setPadding(new Insets(40));
        welcomeVBox.setAlignment(Pos.CENTER);

        GestorOrdenInspeccion gestorOrdenInspeccion = new GestorOrdenInspeccion();

        Label welcomeText = new Label("Bienvenido " + gestorOrdenInspeccion.buscarUsuarioLogueado(em, username) + " - Red Sismica");
        welcomeText.setFont(Font.font("Arial", 20));
        welcomeText.setStyle("-fx-fill: black;");

        //Casos de Uso:
        Button cerrarOrdenButton = new Button("Cerrar Orden de Inspección");
        cerrarOrdenButton.setPrefWidth(311);
        cerrarOrdenButton.setPrefHeight(35);
        cerrarOrdenButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        cerrarOrdenButton.setOnAction(event -> {
                PantallaOrdenInspeccion pantallaOrdenInspeccion = new PantallaOrdenInspeccion();
                pantallaOrdenInspeccion.cerrarOrdenDeInspeccion(em, username);
                welcomeStage.close();
            });

        Button registrarButton = new Button("Registrar resultado de revisión manual");
        registrarButton.setPrefWidth(311);
        registrarButton.setPrefHeight(35);
        registrarButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        Button adquirirButton = new Button("Adquirir datos de sismografo");
        adquirirButton.setPrefWidth(311);
        adquirirButton.setPrefHeight(35);
        adquirirButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        registrarButton.setDisable(true);
        adquirirButton.setDisable(true);

        welcomeVBox.getChildren().addAll(welcomeText, cerrarOrdenButton, registrarButton, adquirirButton
        );

        Scene welcomeScene = new Scene(welcomeVBox, 600, 300);
        welcomeStage.setScene(welcomeScene);
        welcomeStage.show();
    }

    public void cerrarOrdenDeInspeccion(EntityManager em, String username) {
        Stage welcomeStage = new Stage();
        welcomeStage.setTitle("CU 37 - Cerrar Orden de Inpsección");

        VBox welcomeVBox = new VBox(20);
        welcomeVBox.setPadding(new Insets(40));
        //welcomeVBox.setAlignment(Pos.CENTER);

        GestorOrdenInspeccion gestorOrdenInspeccion = new GestorOrdenInspeccion();

        Label welcomeText = new Label("CU 37 - Cerrar Orden de Inspección | Usuario logueado: " + gestorOrdenInspeccion.buscarUsuarioLogueado(em, username));
        welcomeText.setFont(Font.font("Arial", 20));
        welcomeText.setStyle("-fx-fill: black;");

        // Crear tabla
        TableView<OrdenInspeccion> tabla = new TableView<>();

        TableColumn<OrdenInspeccion, String> colNumero = new TableColumn<>("N° Orden");

        colNumero.setCellValueFactory(cellData -> {
            OrdenInspeccion orden = cellData.getValue();
            return new SimpleStringProperty(String.valueOf(orden.getNumero()));
        });

        TableColumn<OrdenInspeccion, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstado().getNombre())
        );

        TableColumn<OrdenInspeccion, String> colFecha = new TableColumn<>("Fecha Finalización");
        colFecha.setCellValueFactory(cellData -> {
            OrdenInspeccion orden = cellData.getValue();
            return new SimpleStringProperty(orden.getFechaFinalizacion().toString());
        });

        TableColumn<OrdenInspeccion, String> colEstacion = new TableColumn<>("Nombre Estación");
        colEstacion.setCellValueFactory(cellData -> {
            OrdenInspeccion orden = cellData.getValue();
            return new SimpleStringProperty(orden.getEstacion().getNombre());
        });

        TableColumn<OrdenInspeccion, String> colSismografo = new TableColumn<>("ID Sismógrafo");
        colSismografo.setCellValueFactory(cellData -> {
            OrdenInspeccion orden = cellData.getValue();
            return new SimpleStringProperty(orden.getSismografo().getIdSismografo());
        });

        tabla.getColumns().addAll(colNumero, colEstado, colFecha, colEstacion, colSismografo);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        List<OrdenInspeccion> listaOrdenes = gestorOrdenInspeccion.buscarOrdenesInspeccionRealizadas(em);

        List<OrdenInspeccion> listaOrdenada = gestorOrdenInspeccion.ordenarPorFechaFinalizacion(listaOrdenes);

        ObservableList<OrdenInspeccion> ordenesObservable = FXCollections.observableArrayList(listaOrdenada);

        tabla.setItems(ordenesObservable);

        tabla.setRowFactory(tv -> {
            TableRow<OrdenInspeccion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    OrdenInspeccion orden = row.getItem();
                    abrirOrdenSeleccionada(orden.getNumero(), username);
                }
            });
            return row;
        });


        Button btnVolver = new Button("Volver");
        btnVolver.setPrefWidth(211);
        btnVolver.setPrefHeight(35);
        btnVolver.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnVolver.setOnAction(e -> {
            welcomeStage.close(); // cerrar esta
            habilitarVentana(em, username); // volver a la anterior
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setPrefWidth(211);
        btnCancelar.setPrefHeight(35);
        btnCancelar.setStyle("-fx-background-color: red; -fx-text-fill: white;");


        btnCancelar.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Estás seguro que deseas cancelar?");
            alert.setContentText("Se perderán los datos no guardados.");

            // Mostrar y esperar respuesta
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                welcomeStage.close(); // Cierra la ventana si el usuario confirma
            }
        });

        HBox botonesBox = new HBox(10);
        botonesBox.setAlignment(Pos.CENTER_RIGHT);
        botonesBox.getChildren().addAll(btnVolver, btnCancelar);

        welcomeVBox.getChildren().addAll(welcomeText, tabla, botonesBox);

        Scene welcomeScene = new Scene(welcomeVBox, 900, 600);
        welcomeStage.setScene(welcomeScene);
        welcomeStage.show();
    }

    public void abrirOrdenSeleccionada(int numeroOrden, String username) {
        Stage welcomeStage = new Stage();
        welcomeStage.setTitle("CU 37 - Cerrar Orden de Inpsección");

        VBox welcomeVBox = new VBox(20);
        welcomeVBox.setPadding(new Insets(40));

        Label welcomeText = new Label("Orden seleccionada: " + numeroOrden);
        welcomeText.setFont(Font.font("Arial", 20));
        welcomeText.setStyle("-fx-fill: black;");

        Label comentarioLabel = new Label("Por favor ingrese un comentario para la orden:");
        TextArea comentarioArea = new TextArea();
        comentarioArea.setPrefRowCount(5);
        comentarioArea.setPrefColumnCount(40);
        comentarioArea.setWrapText(true);

        Button btnGuardar = new Button("Guardar Comentario");
        btnGuardar.setPrefWidth(211);
        btnGuardar.setPrefHeight(35);
        btnGuardar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnGuardar.setDisable(true); // Deshabilitado por defecto

        comentarioArea.textProperty().addListener((observable, oldValue, newValue) -> {
            btnGuardar.setDisable(newValue.trim().isEmpty());
        });

        btnGuardar.setOnAction(e -> {
           // String comentario = comentarioArea.getText().trim();

            // Mostrar alerta de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡Éxito!");
            alert.setHeaderText("El comentario se guardó exitosamente para la orden: " + numeroOrden);
            alert.showAndWait();
            welcomeStage.close();
            abrirSeleccionMotivos(numeroOrden, username);
        });

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setPrefWidth(211);
        btnCancelar.setPrefHeight(35);
        btnCancelar.setStyle("-fx-background-color: red; -fx-text-fill: white;");


        btnCancelar.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Estás seguro que deseas cancelar?");
            alert.setContentText("Se perderán los datos no guardados.");

            // Mostrar y esperar respuesta
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                welcomeStage.close(); // Cierra la ventana si el usuario confirma
            }
        });

        HBox botonesBox = new HBox(10);
        botonesBox.getChildren().addAll(btnGuardar, btnCancelar);

        welcomeVBox.getChildren().addAll(welcomeText, comentarioLabel, comentarioArea, botonesBox);

        Scene welcomeScene = new Scene(welcomeVBox, 600, 300);
        welcomeStage.setScene(welcomeScene);
        welcomeStage.show();

    }

    public void abrirSeleccionMotivos(int numeroOrden, String username) {
        Stage motivoStage = new Stage();
        motivoStage.setTitle("Seleccionar Motivos - Orden " + numeroOrden);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        Label titulo = new Label("Seleccione los motivos de cierre:");
        titulo.setFont(Font.font("Arial", 18));

        // Lista de motivos (puedes agregar más fácilmente)
        String[] motivos = {
                "Falla en el sismógrafo",
                "Condiciones climáticas",
                "Falta de acceso al sitio",
                "Problemas eléctricos",
                "Otros"
        };

        // Mapa para relacionar cada checkbox con su campo de texto
        Map<CheckBox, TextField> motivoInputs = new LinkedHashMap<>();

        for (String motivo : motivos) {
            CheckBox cb = new CheckBox(motivo);
            TextField txt = new TextField();
            txt.setPromptText("Comentario para \"" + motivo + "\"");
            txt.setDisable(true);

            // Cuando se marca el checkbox, se habilita el campo de texto
            cb.selectedProperty().addListener((obs, oldVal, newVal) -> txt.setDisable(!newVal));

            motivoInputs.put(cb, txt);
        }

        VBox motivosBox = new VBox(10);
        for (Map.Entry<CheckBox, TextField> entry : motivoInputs.entrySet()) {
            VBox motivoEntry = new VBox(5);
            motivoEntry.getChildren().addAll(entry.getKey(), entry.getValue());
            motivosBox.getChildren().add(motivoEntry);
        }

        // Botones
        Button btnConfirmar = new Button("Confirmar Cierre de Orden");
        btnConfirmar.setPrefWidth(250);
        btnConfirmar.setPrefHeight(10);
        btnConfirmar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setPrefWidth(150);
        btnCancelar.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        btnCancelar.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("¿Estás seguro que deseas cancelar?");
            alert.setContentText("Se perderán los datos no guardados.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                motivoStage.close(); // Cierra la ventana si el usuario confirma
            }
        });

        btnConfirmar.setOnAction(e -> {
            System.out.println("Motivos seleccionados para la orden " + numeroOrden + ":");
            for (Map.Entry<CheckBox, TextField> entry : motivoInputs.entrySet()) {
                if (entry.getKey().isSelected()) {
                    String motivoSeleccionado = entry.getKey().getText();
                    String comentario = entry.getValue().getText();
                    System.out.println("- " + motivoSeleccionado + " | Comentario: " + comentario);
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Orden de Inspeccion Cerrada");
            alert.setHeaderText("La orden de inspección se cerró exitosamente.");
            alert.showAndWait();

            motivoStage.close();
        });

        HBox botonesBox = new HBox(10, btnConfirmar, btnCancelar);
        botonesBox.setAlignment(Pos.CENTER_RIGHT);

        layout.getChildren().addAll(titulo, motivosBox, botonesBox);

        Scene scene = new Scene(layout, 600, 400);
        motivoStage.setScene(scene);
        motivoStage.show();
    }

}
