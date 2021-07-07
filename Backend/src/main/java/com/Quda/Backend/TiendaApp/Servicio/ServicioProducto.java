package com.Quda.Backend.TiendaApp.Servicio;

import com.Quda.Backend.TiendaApp.Entidad.Product;
import com.Quda.Backend.TiendaApp.Repositorio.JpaProducto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioProducto {

    private final JpaProducto jpaProducto;


    // CRUD =================================================================================
    public Optional<Product> buscarProducto(Integer id){
        return jpaProducto.findById(id);
    }

    public Optional<Product> crearProducto(Product producto){
        if (!jpaProducto.findById(producto.getProductSerial()).isEmpty()){
            throw new RuntimeException("Producto "+producto.getProductName()
                    +" Ya existe con el Serial #"+producto.getProductSerial());
        }
        return Optional.ofNullable(jpaProducto.save(producto));
    }

    public Optional<Product> editarProducto(Product producto){
        if (jpaProducto.findById(producto.getProductSerial()).isEmpty()){
            throw new RuntimeException("El producto que se quiere editar no existe");
        }
        return Optional.ofNullable(jpaProducto.save(producto)); }

    public void eliminarProducto(Integer id){
        if (jpaProducto.findById(id).isEmpty()){
            throw new RuntimeException("El producto que se quiere eliminar no existe");
        }
        jpaProducto.delete(buscarProducto(id).get());
    }

    //Listas productos ======================================================================

    public List<Product> listarProductos(){
        return jpaProducto.findAll();
    }

    public List<Product> listarProductosDisponibles() {
    return jpaProducto.listarDisponibles();}

    public List<Product> listarProductosCategoria(Integer categoria){
        return jpaProducto.listarPorCategoria(categoria);
    }

    public List<Product> listarProductosProveedor(Integer proveedor){
        return jpaProducto.listarPorProveedor(proveedor);
    }

    public List<Product> listarProductosObjetivo(Integer objetivo) {
        return jpaProducto.listarPorObjetivo(objetivo);
    }
    //Agregar Stock =========================================================================

    public void agregarStockAlProducto(Integer producto, Integer cantidad){
        Optional<Product> product = jpaProducto.findById(producto);
        if (product.isEmpty()){
            throw new RuntimeException("El producto no al que se quiere agregar stock no existe. (Revisa el serial del producto)");
        }
        product.get().setProductStock(product.get().getProductStock()+cantidad);
        jpaProducto.save(product.get());
    }


    //Validar Productos ======================================================================

    @Transactional
    public void restarExistencias (HashMap<Integer,Integer> listaProductos){
        listaProductos.forEach((k,v) ->
                {
                    try {
                        Optional<Product> productoValidado = validarExistencias(k,v);
                        productoValidado.get().setProductStock(productoValidado.get().getProductStock()-v);
                        jpaProducto.save(productoValidado.get());
                    }
                    catch (RuntimeException e){
                        throw e;
                    } });
    }

    public Boolean existeStock(HashMap<Integer,Integer> listaProductos){
        listaProductos.forEach((k,v) ->
        { try {
                Optional<Product> productoValidado = validarExistencias(k,v);}
            catch (RuntimeException e){
                throw e;
            } });
        return true;
    }

    public Optional<Product> validarExistencias(Integer id, Integer value){
        Optional<Product> productoValidar= jpaProducto.findById(id);
        if (productoValidar.get().getProductStock()-value < 0){
            throw new RuntimeException("No hay existencias del producto "+productoValidar.get().getProductName());
        }
        return productoValidar;
    }
}
