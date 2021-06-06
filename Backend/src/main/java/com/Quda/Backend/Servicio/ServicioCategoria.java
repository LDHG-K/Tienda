package com.Quda.Backend.Servicio;

import com.Quda.Backend.Entidades.Category;
import com.Quda.Backend.Repositorio.JpaCategoria;
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

    public Optional<Category> editarCategoria(Category categoria){

    }

    public List<Category> listarCategorias(){
        return jpaCategoria.findAll();
    }



}
