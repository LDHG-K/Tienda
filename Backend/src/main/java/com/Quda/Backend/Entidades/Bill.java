package com.Quda.Backend.Entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the bills database table.
 * 
 */
@Entity
@Table(name="bills")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="bill_id")
	private Integer billId;

	@Temporal(TemporalType.DATE)
	@Column(name="bill_date")
	private Date billDate;

	@Column(name="bill_send_addres")
	private String billSendAddres;

	@Column(name="bill_send_cost")
	private BigDecimal billSendCost;

	@Column(name="bill_total")
	private BigDecimal billTotal;

	@Column(name="fk_pay_form_id")
	private Integer payFormId;

	@Column(name="fk_persons_id")
	private Integer personId;

	@Column(name="fk_shipping_guide")
	private Integer shippingId;

	@Column(name="fk_states_id")
	private Integer stateId;

	@Column(name="fk_city_id")
	private Integer cityId;


	//bi-directional many-to-one association to City
	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@JoinColumn(name="fk_city_id", updatable = false,insertable = false)
	private City city;

	//bi-directional many-to-one association to PayForm
	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@JoinColumn(name="fk_pay_form_id", updatable = false,insertable = false)
	private PayForm payForm;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@JoinColumn(name="fk_persons_id", updatable = false,insertable = false)
	private Person person;

	//bi-directional many-to-one association to Shipping
	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@JoinColumn(name="fk_shipping_guide", updatable = false,insertable = false)
	private Shipping shipping;

	//bi-directional many-to-one association to State
	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@JoinColumn(name="fk_states_id", updatable = false,insertable = false)
	private State state;

	//bi-directional many-to-one association to BillsProduct
	@OneToMany(mappedBy="bill")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<BillsProduct> billsProducts;


}