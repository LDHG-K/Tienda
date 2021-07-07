package com.Quda.Backend.TiendaApp.Servicio;

import com.Quda.Backend.TiendaApp.Entidad.Category;
import com.Quda.Backend.TiendaApp.Repositorio.JpaCategoria;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioCategoria {

    private final JpaCategoria jpaCategoria;

    public Optional<Category> buscarCategoria (Integer id) {
        Optional<Category> buscada = jpaCategoria.findById(id);
        try {
            if (buscada.isEmpty()) {
                throw new RuntimeException("No existe esa categoria");
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return buscada;
    }

    public Optional<Category> crearCategoria(Category category){
        Optional<Category> buscada = Optional.of(jpaCategoria.save(category));
        try {
            if (buscada.isEmpty()) {
                throw new RuntimeException("Hubo un error creando la categoria");
            }

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return buscada;
    }

    public void eliminarCategoria (Integer id){
        Optional<Category> buscada = buscarCategoria(id);
        jpaCategoria.delete(buscada.get());
    }

    public Optional<Category> editarCategoria(Category categoria, String categoriaAntigua){
        Integer id = jpaCategoria.buscarIdPorCategoria(categoriaAntigua);
        categoria.setCategoryId(id);
        System.out.println(id);
        return Optional.of(jpaCategoria.save(categoria));
    }

    public List<Category> listarCategorias(){
        return jpaCategoria.findAll();
    }



}
