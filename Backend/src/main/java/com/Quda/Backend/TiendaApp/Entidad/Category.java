package com.Quda.Backend.Entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.List;


/**
 * The persistent class for the categories database table.
 * 
 */
@Data
@Entity
@NoArgsConstructor
@Table(name="categories")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Positive
	@Column(name="category_id")
	private Integer categoryId;

	@Column(name="category_name")
	private String categoryName;

	//bi-directional many-to-one association to Product
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(mappedBy="category")
	private List<Product> products;

}