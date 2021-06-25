package com.Quda.Backend.Entidades;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the bills_products database table.
 * 
 */
@AllArgsConstructor
@Data
@Builder
@Embeddable
public class BillsProductPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="fk_product_serial", insertable=false, updatable=false)
	private Integer fkProductSerial;

	@Column(name="fk_bills_id", insertable=false, updatable=false)
	private Integer fkBillsId;

	public BillsProductPK() {
	}
}