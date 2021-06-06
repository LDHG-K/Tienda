package com.Quda.Backend.Servicio;

import com.Quda.Backend.Entidades.Category;
import com.Quda.Backend.Entidades.Supplier;
import com.Quda.Backend.Repositorio.JpaProveedor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioProveedor {

    private final JpaProveedor jpaProveedor;


    public Optional<Supplier> buscarProveedor(Integer id) {
    return jpaProveedor.findById(id);
    }

    public List<Supplier> listarProveedores() {
    return jpaProveedor.findAll();
    }

    public Optional<Supplier> crearProveedor(Supplier categoria) {
        return Optional.of(jpaProveedor.save(categoria));
    }

    public void eliminarProveedor(Integer id) {
        Optional<Supplier> proveedor = buscarProveedor(id);
        jpaProveedor.delete(proveedor.get());
    }

    public Optional<Supplier> editarProveedor(Supplier proveedor, String id) {
        Integer idBuscado = jpaProveedor.darIdPorProveedorNombre(id);
        proveedor.setSupplierId(idBuscado);
        return Optional.of(jpaProveedor.save(proveedor));
    }



}
