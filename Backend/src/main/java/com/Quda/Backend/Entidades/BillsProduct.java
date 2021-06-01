package com.Quda.Backend.Entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="bills_products")
public class BillsProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BillsProductPK id;

	@Column(name = "discount")
	private BigDecimal discount;

	@Column(name = "tax")
	private BigDecimal tax;

	@Column(name = "total")
	private BigDecimal total;

	@Column(name = "units")
	private Integer units;

	//bi-directional many-to-one association to Bill
	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@JoinColumn(name="fk_bills_id", updatable = false,insertable = false)
	private Bill bill;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@JoinColumn(name="fk_product_serial", updatable = false,insertable = false)
	private Product product;



}