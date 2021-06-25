package com.Quda.Backend.Entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the products database table.
 * 
 */
@Entity
@Table(name="products")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="product_serial")
	private Integer productSerial;

	@Column(name="product_description")
	private String productDescription;

	@Column(name="product_image")
	private Integer productImage;

	@Size(min = 3, max = 30, message
			= "El nombre del producto no debe de ser mayor a 30 o menor a 3 caracteres")
	@Column(name="product_name")
	private String productName;

	@PositiveOrZero(message = "El valor de venta siempre debe de ser positivo o 0 en su defecto")
	@Column(name="product_sell_price")
	private BigDecimal productSellPrice;

	@Min(value = 0, message = "EL STOCK NUNCA DEBE DE SER MENOR A 0")
	@Column(name="product_stock")
	private Integer productStock;

	@Positive
	@Column(name = "fk_category_id")
	private Integer categoryId;

	@Positive
	@Column(name = "fk_typestype_id")
	private Integer objetiveId;

	@Positive
	@Column(name= "fk_supplier_id")
	private Integer supplierId;

	//bi-directional many-to-one association to BillsProduct
	@OneToMany(mappedBy="product")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<BillsProduct> billsProducts;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="fk_category_id", updatable = false,insertable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Category category;

	//bi-directional many-to-one association to Objetive
	@ManyToOne
	@JoinColumn(name="fk_typestype_id", updatable = false,insertable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Objetive objetive;

	//bi-directional many-to-one association to Supplier
	@ManyToOne
	@JoinColumn(name="fk_supplier_id", updatable = false,insertable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Supplier supplier;

	//bi-directional many-to-one association to Purchas
	@OneToMany(mappedBy="product")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<Purchas> purchases;


}