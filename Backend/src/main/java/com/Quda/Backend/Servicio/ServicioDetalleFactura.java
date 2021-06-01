package com.Quda.Backend.Servicio;

import com.Quda.Backend.Entidades.BillsProduct;
import com.Quda.Backend.Entidades.BillsProductPK;
import com.Quda.Backend.Entidades.Product;
import com.Quda.Backend.Repositorio.JpaDetalleFactura;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioDetalleFactura {

    private final JpaDetalleFactura jpaDetalleFactura;
    private final ServicioProducto servicioProducto;

    // Crear llaves para cada uno de los productos
    public void crearDetalles(Integer idFactura, HashMap<Integer,Integer> productos){

        productos.forEach((k,v) -> {
            // Llaves
            BillsProductPK llaves = BillsProductPK.builder().
                    fkProductSerial(k).
                    fkBillsId(idFactura).build();

            BillsProduct registro = BillsProduct.builder()
                    .id(llaves)
                    .discount(null)
                    .tax(null)
                    .units(v)
                    .total(calcularTotal(k,v))
                    .build();
            jpaDetalleFactura.save(registro);
        });

    }

    public BigDecimal calcularTotal (Integer k, Integer v){
        Optional<Product> producto = servicioProducto.buscarProducto(k);
        BigDecimal itemCost=new BigDecimal(BigInteger.ZERO,  2);
        BigDecimal totalCost=new BigDecimal(BigInteger.ZERO,  2);

        itemCost = producto.get().getProductSellPrice().multiply(new BigDecimal(v));
        return totalCost.add(itemCost);
    }




}
