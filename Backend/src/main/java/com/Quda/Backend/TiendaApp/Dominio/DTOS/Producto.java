package com.Quda.Backend.TiendaApp.Dominio.DTOS;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Builder
public class Producto {
    /**
     * SERIAL DEL PRODUCTO
     */
    private Integer productSerial;
    /**
     * DESCRIPCION DEL PRODUCTO
     */
    private String productDescription;
    /**
     * NUMERO DE IMAGEN DEL PRODUCTO
     */
    private Integer productImage;
    /**
     * DESCUENTO ACTUAL DEL PRODUCTO
     */
    private BigDecimal actualDiscount;
    /**
     * IMPUESTO ACTUAL DEL PRODUCTO
     */
    private BigDecimal actualTax;
    /**
     * NOMBRE DEL PRODUCTO
     */
    private String productName;
    /**
     * PRECIO DE VENTA ACTUAL DEL PRODUCTO
     */
    private BigDecimal productSellPrice;
    /**
     * STOCK ACTUAL DEL PRODUCTO
     */
    private Integer productStock;
    /**
     * ID CATEGORIA DEL PROCUTO
     */
    private Integer categoryId;
    /**
     * ID PUBLICO OBJETIVO
     */
    private Integer objetiveId;
    /**
     * ID PROVEEDOR O FABRICANTE
     */
    private Integer supplierId;
}
