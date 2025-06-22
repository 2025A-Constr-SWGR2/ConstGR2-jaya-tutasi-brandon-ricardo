package servlets;

import java.io.IOException;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelos.Producto;

@WebServlet(name = "ControllerProducto", urlPatterns = {"/ControllerProducto"})
public class ControllerProducto extends HttpServlet {

    private EntityManagerFactory emf;

    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void init() throws ServletException {
        if (emf == null) { // Solo crear si no se inyectó (para pruebas)
            emf = Persistence.createEntityManagerFactory("Tarea1PU");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String cantidadStr = request.getParameter("cantidad");
        String precioStr = request.getParameter("precio");

        // Validación básica
        if (nombre == null || nombre.trim().isEmpty() ||
                descripcion == null || descripcion.trim().isEmpty() ||
                cantidadStr == null || cantidadStr.trim().isEmpty() ||
                precioStr == null || precioStr.trim().isEmpty()) {

            request.setAttribute("errorMessage", "Todos los campos son requeridos");
            doGet(request, response);
            return;
        }

        try {
            // Convertir valores numéricos
            int cantidad = Integer.parseInt(cantidadStr);
            double precio = Double.parseDouble(precioStr);

            // Crear y guardar producto
            Producto nuevoProducto = new Producto(nombre, descripcion, cantidad, precio);

            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(nuevoProducto);
                em.getTransaction().commit();
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                request.setAttribute("errorMessage", "Error al guardar: " + e.getMessage());
            } finally {
                em.close();
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Cantidad y Precio deben ser números válidos");
        }

        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        EntityManager em = emf.createEntityManager();

        try {
            if(action != null) {
                switch(action) {
                    case "delete":
                        handleDelete(request, em);
                        break;
                    case "edit":
                        String idStr = request.getParameter("id");
                        if(idStr != null && !idStr.isEmpty()) {
                            int id = Integer.parseInt(idStr);
                            Producto producto = em.find(Producto.class, id);
                            request.setAttribute("productoEditar", producto);
                        }
                        break;
                }
            }

            List<Producto> productos = em.createQuery("SELECT p FROM Producto p", Producto.class)
                    .getResultList();
            request.setAttribute("productos", productos);

            request.getRequestDispatcher("index.jsp").forward(request, response);
        } finally {
            em.close();
        }
    }

    private void handleDelete(HttpServletRequest request, EntityManager em) {
        try {
            String idStr = request.getParameter("id");
            if(idStr != null && !idStr.isEmpty()) {
                int id = Integer.parseInt(idStr);
                em.getTransaction().begin();
                Producto producto = em.find(Producto.class, id);
                if(producto != null) {
                    em.remove(producto);
                }
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    private void handleEdit(HttpServletRequest request, EntityManager em) {
        try {
            String idStr = request.getParameter("id");
            if(idStr != null && !idStr.isEmpty()) {
                int id = Integer.parseInt(idStr);
                Producto producto = em.find(Producto.class, id);

                if(producto != null) {
                    // Actualizar campos si se enviaron en el request
                    String nombre = request.getParameter("nombre");
                    String descripcion = request.getParameter("descripcion");
                    String cantidadStr = request.getParameter("cantidad");
                    String precioStr = request.getParameter("precio");

                    if(nombre != null) producto.setNombre(nombre);
                    if(descripcion != null) producto.setDescripcion(descripcion);
                    if(cantidadStr != null) producto.setCantidad(Integer.parseInt(cantidadStr));
                    if(precioStr != null) producto.setPrecio(Double.parseDouble(precioStr));

                    em.getTransaction().begin();
                    em.merge(producto);
                    em.getTransaction().commit();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al editar producto: " + e.getMessage());
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    @Override
    public void destroy() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}