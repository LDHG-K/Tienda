package com.Quda.Backend.Servicio;

import com.Quda.Backend.Entidades.Person;
import com.Quda.Backend.Entidades.Product;
import com.Quda.Backend.Entidades.User;
import com.Quda.Backend.Repositorio.JpaProducto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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
        return Optional.ofNullable(jpaProducto.save(producto)); }

    public void eliminarProducto(Integer id){
        jpaProducto.delete(buscarProducto(id).get());
    }

    //Listas productos ======================================================================

    public List<Product> listarProductos(){
        return jpaProducto.findAll();
    }

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
        Product product = jpaProducto.findById(producto).get();
        product.setProductStock(product.getProductStock()+cantidad);
        jpaProducto.save(product);
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
                    }
                }
        );

    }

    public Boolean existeStock(HashMap<Integer,Integer> listaProductos){

        listaProductos.forEach((k,v) ->
        {
            try {
                Optional<Product> productoValidado = validarExistencias(k,v);}
            catch (RuntimeException e){
                throw e;
            }
        }
        );
        return true;

    }

    public Optional<Product> validarExistencias(Integer id, Integer value){

        Optional<Product> productoValidar= jpaProducto.findById(id);
        if (productoValidar.get().getProductStock()-value < 0){
            throw new RuntimeException("No hay existencias del producto "+productoValidar.get().getProductName());
        }
        return productoValidar;
    }


    // Metodos Recibiendo listas <OPCIONAL>
  /*
    public void restarExistencias(List<Integer> listaProductos){

        List<Product> listaNueva = null;

        for (Integer idProducto: listaProductos) {
            try{
                Product productoValidado =validarExistencias(idProducto).get();
                productoValidado.setProductStock(productoValidado.getProductStock()-1);
                listaNueva.add(productoValidado);
                jpaProducto.saveAll(listaNueva);
            }

            catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Integer> existeStock(List<Integer> listaProductos){


        for (Integer idProducto: listaProductos) {
            try{
                Product productoValidado =validarExistencias(idProducto,).get();
            }
            catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
        return listaProductos;
    }

    public Optional<Product> validarExistencias(Integer id, Integer cantidad){

        Optional<Product> productoValidar= jpaProducto.findById(id);
        if (productoValidar.get().getProductStock()==0){
            throw new RuntimeException("No hay existencias del producto "+productoValidar.get().getProductName());
        }
        return productoValidar;
    }

    public HashMap<Integer, Integer> transform (List<Integer> list){

		HashMap<Integer, Integer> response = new HashMap<Integer, Integer>(list.size());
		for (Integer integer : list) {
			if (!response.containsKey(integer)) {
				response.put(integer, 1);
			}
			else {
				int value = response.get(integer);
				response.replace(integer, value+1);
			}
		}
		return response;
	}

   */


}
