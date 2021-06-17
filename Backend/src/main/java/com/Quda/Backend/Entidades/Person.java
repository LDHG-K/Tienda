package com.Quda.Backend.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the persons database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="persons")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Positive
	@Column(name="person_id")
	private Integer personId;

	@Positive
	@Column(name="fk_names_identification_id")
	private Integer fkNamesIdentificationId;

	@Temporal(TemporalType.DATE)
	@Column(name="person_birthdate")
	private Date personBirthdate;

	@Column(name="person_cellphone")
	private String personCellphone;

	@Temporal(TemporalType.DATE)
	@Column(name="person_creation_date")
	private Date personCreationDate;

	@Email(message = "El correo debe de tener un formato de correo electronico")
	@Column(name="person_email")
	private String personEmail;

	@Column(name="person_identification")
	private String personIdentification;

	@Column(name="person_lastname")
	private String personLastname;

	@Column(name="person_name")
	@Size(min = 4, max = 30, message = "nombre debe tener entre 4 y 30 caracteres")
	private String personName;

	@Positive
	@Column(name="fk_city_id")
	private Integer cityId;

	@Positive
	@Column(name="fk_roles_id")
	private Integer roleId;

	//bi-directional many-to-one association to Bill
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(mappedBy="person")
	private List<Bill> bills;

	//bi-directional many-to-one association to City
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne
	@JoinColumn(name="fk_city_id", updatable = false,insertable = false)
	private City city;

	//bi-directional many-to-one association to Role
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne
	@JoinColumn(name="fk_roles_id", updatable = false,insertable = false)
	private Role role;

	//bi-directional many-to-one association to User
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(mappedBy="person")
	private List<User> users;

}