package com.Quda.Backend.Entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Builder;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_nick_name")
	@Size(min = 4, max = 30, message = "NickName debe tener entre 4 y 30 caracteres")
	private String userNickName;

	@Column(name="user_password")
	@Size(min = 8, message = "Contraseña debe contener mas de 8 Caracteres")
	//@JsonIgnore
	@JsonProperty(access = Access.WRITE_ONLY)
	private String userPassword;

	@Column(name="fk_person_id")
	private Integer personId;

	@Column(name="fk_state_id")
	private Integer stateId;
	//bi-directional many-to-one association to Person

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="fk_person_id", updatable = false,insertable = false)
	//@JsonIgnore
	private Person person;

	//bi-directional many-to-one association to State
	//@JsonIgnore
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne
	@JoinColumn(name="fk_state_id", updatable = false,insertable = false)
	private State state;

}