package com.Quda.Backend.TiendaApp.Entidad;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cities database table.
 * 
 */
@Entity
@Table(name="cities")
@NamedQuery(name="City.findAll", query="SELECT c FROM City c")
@Data
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="city_id")
	private Integer cityId;

	@Column(name="city_name")
	private String cityName;

	@Column(name="fk_departments_id")
	private Integer departmentId;

	//bi-directional many-to-one association to Bill
	@OneToMany(mappedBy="city")
	private List<Bill> bills;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn(name="fk_departments_id",insertable = false, updatable = false)
	private Department department;

	//bi-directional many-to-one association to State
	@ManyToOne
	@JoinColumn(name="fk_state_id")
	private State state;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="city")
	private List<Person> persons;

	public City() {
	}

	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<Bill> getBills() {
		return this.bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	public Bill addBill(Bill bill) {
		getBills().add(bill);
		bill.setCity(this);

		return bill;
	}

	public Bill removeBill(Bill bill) {
		getBills().remove(bill);
		bill.setCity(null);

		return bill;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Person addPerson(Person person) {
		getPersons().add(person);
		person.setCity(this);

		return person;
	}

	public Person removePerson(Person person) {
		getPersons().remove(person);
		person.setCity(null);

		return person;
	}

}