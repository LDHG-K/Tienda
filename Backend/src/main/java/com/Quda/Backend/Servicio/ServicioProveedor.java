package com.Quda.Backend.Servicio;

import com.Quda.Backend.Entidades.Category;
import com.Quda.Backend.Entidades.Supplier;
import com.Quda.Backend.Repositorio.JpaProveedor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioProveedor {

    private final JpaProveedor jpaProveedor;


    public Optional<Supplier> buscarProveedor(Integer id) {

        Optional<Supplier> proveedor = jpaProveedor.findById(id);
        if (proveedor.isEmpty()){
            throw new RuntimeException("No existe el proveedor "+id );
        }

    return proveedor ;
    }

    public List<Supplier> listarProveedores() {
    return jpaProveedor.findAll();
    }

    public Optional<Supplier> crearProveedor(Supplier proveedor) {
        Optional<Supplier> proveedorGuardado = null;
        try {
             proveedorGuardado = Optional.ofNullable(jpaProveedor.save(proveedor));
        }
        catch (RuntimeException e){
            throw e;
        }
        return proveedorGuardado;
    }

    @Transactional
    public void eliminarProveedor(Integer id) {

        Optional<Supplier> proveedor = buscarProveedor(id);
        if (proveedor.isEmpty()){
            throw new RuntimeException("El proveedor a eliminar no existe");
        }

        jpaProveedor.delete(proveedor.get());
    }

    @Transactional
    public Optional<Supplier> editarProveedor(Supplier proveedor, String id) {

        Integer idBuscado = 0;

        try {
            idBuscado = jpaProveedor.darIdPorProveedorNombre(id);
        }
        catch (RuntimeException e){
            throw e;
        }

        proveedor.setSupplierId(idBuscado);

        return Optional.of(jpaProveedor.save(proveedor));
    }



}
