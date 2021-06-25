package com.Quda.Backend.Entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Supplier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="supplier_id")
	private Integer supplierId;

	@NotNull(message = "Nombre del proveedor no puede ser un espacio vacio")
	@Size(min = 2, max = 30, message = "El nombre no puede exceder de los 30 caracteres")
	@NotBlank(message =  "No son admitidos los espacios en blanco")
	@Column(name="supplier_name")
	private String supplierName;

	//bi-directional many-to-one association to Product
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(mappedBy="supplier")
	private List<Product> products;

}