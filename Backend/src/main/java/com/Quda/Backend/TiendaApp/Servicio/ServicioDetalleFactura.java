package com.Quda.Backend.TiendaApp.Servicio;

import com.Quda.Backend.TiendaApp.Entidad.Bill;
import com.Quda.Backend.TiendaApp.Entidad.BillsProduct;
import com.Quda.Backend.TiendaApp.Entidad.BillsProductPK;
import com.Quda.Backend.TiendaApp.Entidad.Product;
import com.Quda.Backend.TiendaApp.Repositorio.JpaDetalleFactura;
import com.Quda.Backend.TiendaApp.Repositorio.JpaFactura;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ServicioDetalleFactura {

    private final JpaDetalleFactura jpaDetalleFactura;
    private final ServicioProducto servicioProducto;
    private final JpaFactura jpaFactura;

    // Crear llaves para cada uno de los productos
    @Transactional
    public void crearDetalles(Integer idFactura, HashMap<Integer,Integer> productos){

        ArrayList<BigDecimal> totales = new ArrayList<BigDecimal>();
        productos.forEach((k,v) -> {
            // Llaves
            BillsProductPK llaves = crearLLaves(idFactura,k);
            // Total venta del producto (valorProducto * total unidades)
            BigDecimal valorRegistro = calcularTotalProducto(k,v);
            // Descontar unidades del producto

            //Crear Registro del detalle
            BillsProduct registro = BillsProduct.builder()
                    .id(llaves)
                    .discount(null)
                    .tax(null)
                    .units(v)
                    .total(valorRegistro)
                    .build();
            jpaDetalleFactura.save(registro);
            totales.add(valorRegistro);
        });

        // Actualiza el total de la factura
        BigDecimal total = new BigDecimal(0);
        for (BigDecimal n:  totales) {
            total = total.add(n);
        }

        actualizarTotalFactura(total,idFactura);
    }

    private BigDecimal calcularTotalProducto(Integer k, Integer v){
        Optional<Product> producto = servicioProducto.buscarProducto(k);
        BigDecimal itemCost=new BigDecimal(BigInteger.ZERO,  2);
        BigDecimal totalCost=new BigDecimal(BigInteger.ZERO,  2);

        itemCost = producto.get().getProductSellPrice().multiply(new BigDecimal(v));
        return totalCost.add(itemCost);
    }

    private void actualizarTotalFactura(BigDecimal total, Integer idFactura){
        Optional<Bill> factura = jpaFactura.findById(idFactura);
        factura.get().setBillTotal(factura.get().getBillSendCost().add(total));
        jpaFactura.save(factura.get());
    }

    private BillsProductPK crearLLaves(Integer idFactura, Integer serial){
        return BillsProductPK.builder().
                fkProductSerial(serial).
                fkBillsId(idFactura).build();
    }

    //=============================================================================

    public List<BillsProduct> buscarDetallesDeUnaFactura(Integer idFactura){
        return  jpaDetalleFactura.buscarPorIdfactura(idFactura);
    }
}