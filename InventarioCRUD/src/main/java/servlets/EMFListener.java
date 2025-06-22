package servlets;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.HashMap;
import java.util.Map;

@WebListener
public class EMFListener implements ServletContextListener {

    private static EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Verifica el classpath
            System.out.println("Classpath: " + System.getProperty("java.class.path"));

            // Carga explícita de la clase para verificar
            Class.forName("org.eclipse.persistence.jpa.PersistenceProvider");

            // Configuración manual como fallback
            Map<String, String> props = new HashMap<>();
            props.put("jakarta.persistence.jdbc.url", "jdbc:mysql://localhost:3306/inventario?serverTimezone=UTC");
            props.put("jakarta.persistence.jdbc.user", "root");
            props.put("jakarta.persistence.jdbc.password", "root");
            props.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
            props.put("eclipselink.weaving", "false");
            props.put("eclipselink.logging.level", "FINE");

            emf = Persistence.createEntityManagerFactory("Tarea1PU", props);
            System.out.println("EntityManagerFactory creado exitosamente");
        } catch (Exception e) {
            System.err.println("ERROR FATAL al crear EntityManagerFactory:");
            e.printStackTrace();
            throw new RuntimeException("Error de inicialización JPA", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("EntityManagerFactory cerrado correctamente");
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            throw new IllegalStateException("EntityManagerFactory no está inicializado");
        }
        return emf;
    }
}