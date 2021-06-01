package com.Quda.Backend.Entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
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

	@Column(name="product_name")
	private String productName;

	@Column(name="product_sell_price")
	private BigDecimal productSellPrice;

	@Column(name="product_stock")
	private Integer productStock;

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