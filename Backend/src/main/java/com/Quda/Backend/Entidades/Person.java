package com.Quda.Backend.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the persons database table.
 * 
 */
@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name="persons")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="person_id")
	private Integer personId;

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

	@Column(name="person_email")
	private String personEmail;

	@Column(name="person_identification")
	private String personIdentification;

	@Column(name="person_lastname")
	private String personLastname;

	@Column(name="person_name")
	@Size(min = 4, max = 30, message = "nombre debe tener entre 4 y 30 caracteres")
	private String personName;

	@Column(name="fk_city_id")
	private Integer cityId;

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

	public Person() {
	}

	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getFkNamesIdentificationId() {
		return this.fkNamesIdentificationId;
	}

	public void setFkNamesIdentificationId(Integer fkNamesIdentificationId) {
		this.fkNamesIdentificationId = fkNamesIdentificationId;
	}

	public Date getPersonBirthdate() {
		return this.personBirthdate;
	}

	public void setPersonBirthdate(Date personBirthdate) {
		this.personBirthdate = personBirthdate;
	}

	public String getPersonCellphone() {
		return this.personCellphone;
	}

	public void setPersonCellphone(String personCellphone) {
		this.personCellphone = personCellphone;
	}

	public Date getPersonCreationDate() {
		return this.personCreationDate;
	}

	public void setPersonCreationDate(Date personCreationDate) {
		this.personCreationDate = personCreationDate;
	}

	public String getPersonEmail() {
		return this.personEmail;
	}

	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}

	public String getPersonIdentification() {
		return this.personIdentification;
	}

	public void setPersonIdentification(String personIdentification) {
		this.personIdentification = personIdentification;
	}

	public String getPersonLastname() {
		return this.personLastname;
	}

	public void setPersonLastname(String personLastname) {
		this.personLastname = personLastname;
	}

	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public List<Bill> getBills() {
		return this.bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	public Bill addBill(Bill bill) {
		getBills().add(bill);
		bill.setPerson(this);

		return bill;
	}

	public Bill removeBill(Bill bill) {
		getBills().remove(bill);
		bill.setPerson(null);

		return bill;
	}

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setPerson(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setPerson(null);

		return user;
	}

}