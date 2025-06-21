module com.example.cierreordendeinspeccion {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires com.jfoenix;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    exports com.example.cierreordendeinspeccion;

    opens com.example.cierreordendeinspeccion to org.hibernate.orm.core;
    exports com.example.cierreordendeinspeccion.Entity;
    opens com.example.cierreordendeinspeccion.Entity to javafx.base, org.hibernate.orm.core;
}
