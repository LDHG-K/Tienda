package com.Quda.Backend.Entidades;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the products_names database table.
 * 
 */
@Entity
@Table(name="products_names")
@NamedQuery(name="ProductsName.findAll", query="SELECT p FROM ProductsName p")
public class ProductsName implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="name_id")
	private Integer nameId;

	private Integer name;

	public ProductsName() {
	}

	public Integer getNameId() {
		return this.nameId;
	}

	public void setNameId(Integer nameId) {
		this.nameId = nameId;
	}

	public Integer getName() {
		return this.name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

}