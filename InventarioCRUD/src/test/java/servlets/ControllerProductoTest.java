package servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelos.Producto;
import org.junit.jupiter.api.*;

public class ControllerProductoTest {

    private static EntityManagerFactory emf;
    private ControllerProducto controller;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeAll
    public static void setUpClass() {
        // Crear EMF para H2 (usa persistence.xml con configuración H2)
        emf = Persistence.createEntityManagerFactory("Tarea1PU");
    }

    @AfterAll
    public static void tearDownClass() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @BeforeEach
    public void setUp() {
        controller = new ControllerProducto();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        // Inyectar el EMF de prueba
        controller.setEntityManagerFactory(emf);
    }

    @Test
    public void testCrearProductoValido() throws Exception {
        // Configurar request mock
        when(request.getParameter("nombre")).thenReturn("Laptop HP");
        when(request.getParameter("descripcion")).thenReturn("Core i7 16GB RAM");
        when(request.getParameter("cantidad")).thenReturn("10");
        when(request.getParameter("precio")).thenReturn("899.99");

        // Ejecutar
        controller.doPost(request, response);

        // Verificar en base de datos
        EntityManager em = emf.createEntityManager();
        Producto producto = em.createQuery("SELECT p FROM Producto p WHERE p.nombre = 'Laptop HP'", Producto.class)
                .getSingleResult();
        em.close();

        assertNotNull(producto);
        assertEquals("Laptop HP", producto.getNombre());
        assertEquals(10, producto.getCantidad());
    }

    @Test
    public void testValidacionCamposVacios() throws Exception {
        when(request.getParameter("nombre")).thenReturn("");
        when(request.getParameter("descripcion")).thenReturn("");

        controller.doPost(request, response);

        verify(request).setAttribute(eq("errorMessage"), any(String.class));
    }

    @Test
    public void testValidacionNumerosInvalidos() throws Exception {
        when(request.getParameter("nombre")).thenReturn("Teclado");
        when(request.getParameter("cantidad")).thenReturn("abc");

        controller.doPost(request, response);

        verify(request).setAttribute(eq("errorMessage"), any(String.class));
    }
}