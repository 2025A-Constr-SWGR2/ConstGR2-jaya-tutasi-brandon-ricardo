<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sistema de Inventario</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container">
    <div class="header">
        <div class="logo">
            <i class="fas fa-boxes"></i>
            <span>Inventario</span>
        </div>
    </div>

    <div class="card">
        <h1><i class="fas fa-edit"></i> ${empty productoEditar ? 'Registrar' : 'Editar'} Producto</h1>

        <c:if test="${not empty param.error}">
            <div class="alert alert-error">
                <i class="fas fa-exclamation-circle"></i> ${param.error}
            </div>
        </c:if>

        <form action="ControllerProducto" method="POST" id="productForm">
            <c:if test="${not empty productoEditar}">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="id" value="${productoEditar.id}">
            </c:if>

            <div class="form-group">
                <label for="nombre"><i class="fas fa-tag"></i> Nombre del producto</label>
                <input type="text" id="nombre" name="nombre" class="form-control"
                       value="${productoEditar.nombre}" placeholder="Ej: Laptop HP" required>
            </div>

            <div class="form-group">
                <label for="descripcion"><i class="fas fa-align-left"></i> Descripción</label>
                <input type="text" id="descripcion" name="descripcion" class="form-control"
                       value="${productoEditar.descripcion}" placeholder="Ej: Laptop 15 pulgadas, 8GB RAM" required>
            </div>

            <div class="form-group">
                <label for="cantidad"><i class="fas fa-box"></i> Cantidad en Stock</label>
                <input type="number" id="cantidad" name="cantidad" class="form-control"
                       value="${productoEditar.cantidad}" placeholder="Ej: 10" required min="0">
            </div>

            <div class="form-group">
                <label for="precio"><i class="fas fa-dollar-sign"></i> Precio Unitario</label>
                <input type="number" step="0.01" id="precio" name="precio" class="form-control"
                       value="${productoEditar.precio}" placeholder="Ej: 999.99" required min="0">
            </div>

            <button type="submit" class="btn btn-primary">
                <i class="fas fa-save"></i> ${empty productoEditar ? 'Registrar' : 'Actualizar'} Producto
            </button>

            <c:if test="${not empty productoEditar}">
                <a href="ControllerProducto" class="btn btn-cancel">
                    <i class="fas fa-times"></i> Cancelar
                </a>
            </c:if>
        </form>
    </div>

    <div class="card">
        <h2><i class="fas fa-boxes"></i> Inventario de Productos</h2>
        <c:choose>
            <c:when test="${empty productos}">
                <div class="empty-state">
                    <i class="fas fa-box-open"></i>
                    <h3>No hay productos registrados</h3>
                    <p>Comienza agregando tu primer producto usando el formulario superior</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-container">
                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Descripción</th>
                            <th>Stock</th>
                            <th>Precio</th>
                            <th>Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${productos}" var="p">
                            <tr>
                                <td>${p.id}</td>
                                <td><strong>${p.nombre}</strong></td>
                                <td>${p.descripcion}</td>
                                <td class="${p.cantidad < 10 ? 'low-stock' : 'in-stock'}">
                                        ${p.cantidad} ${p.cantidad < 10 ? '(Bajo Stock)' : ''}
                                </td>
                                <td>$${String.format("%.2f", p.precio)}</td>
                                <td class="actions">
                                    <a href="ControllerProducto?action=edit&id=${p.id}" class="btn-action btn-edit">
                                        <i class="fas fa-edit"></i> Editar
                                    </a>
                                    <a href="ControllerProducto?action=delete&id=${p.id}" class="btn-action btn-delete">
                                        <i class="fas fa-trash-alt"></i> Eliminar
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script>
    document.getElementById('productForm').addEventListener('submit', function(e) {
        const inputs = this.querySelectorAll('input[required]');
        let isValid = true;

        inputs.forEach(input => {
            if (!input.value.trim()) {
                isValid = false;
                input.classList.add('is-invalid');
            } else {
                input.classList.remove('is-invalid');
            }
        });

        // Validar cantidad y precio son números válidos
        const cantidad = document.getElementById('cantidad').value;
        const precio = document.getElementById('precio').value;

        if(isNaN(cantidad) || isNaN(precio)) {
            isValid = false;
            alert('Cantidad y Precio deben ser números válidos');
        }

        if (!isValid) {
            e.preventDefault();
            alert('Por favor complete todos los campos requeridos correctamente');
        }
    });
</script>
</body>
</html>