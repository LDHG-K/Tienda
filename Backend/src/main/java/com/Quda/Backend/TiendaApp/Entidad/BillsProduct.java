package com.Quda.Backend.TiendaApp.Entidad;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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

	@PositiveOrZero
	@Column(name = "discount")
	private BigDecimal discount;

	@PositiveOrZero
	@Column(name = "tax")
	private BigDecimal tax;

	@PositiveOrZero
	@Column(name = "total")
	private BigDecimal total;

	@Positive
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