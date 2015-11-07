package com.qed.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * 持久化组件类
 * @author Administrator
 *
 */
@Entity
@Table(name = "address")
public class Address {
	private String id;
	private String descs;
	private Integer code;
	
	private Person person;
	
	public Address() {}
	
	public Address(String descs, Integer code) {
		super();
		this.descs = descs;
		this.code = code;
	}

	public Address(String id, String descs, Integer code) {
		this.id = id;
		this.descs = descs;
		this.code = code;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", descs=" + descs + ", code=" + code
				+ "]";
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 45)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "descs", nullable = false, length = 200)
	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	@Column(name = "code", nullable = false)
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@ManyToOne
	@JoinColumn(name = "persId", referencedColumnName = "id", nullable = false)
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
