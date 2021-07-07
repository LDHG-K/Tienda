package com.Quda.Backend.TiendaApp.Entidad;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the objetives database table.
 * 
 */
@Entity
@Table(name="objetives")
@NamedQuery(name="Objetive.findAll", query="SELECT o FROM Objetive o")
public class Objetive implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="type_id")
	private Integer typeId;

	@Column(name="type_name")
	private String typeName;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="objetive")
	private List<Product> products;

	public Objetive() {
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setObjetive(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setObjetive(null);

		return product;
	}

}