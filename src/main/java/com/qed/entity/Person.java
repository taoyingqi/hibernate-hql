package com.qed.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * 持久化类设计
 * 注意：
 *   持久化类通常由一个持久化标识符(ID)
 *   持久化标识符通常使用封装类(基本类有默认值)
 *   持久化类通常给定一个无参构造器(反射)
 *   属性通常提供getter/setter方法
 *   持久化类不能是final
 *   持久化类中使用集合数据类型是只能使用接口类型进行声明(List/Set/Map)
 *   List list = new ArrayList();
 * @author Administrator
 *
 */
@Entity
public class Person {
	private String id;
	private String name;
	private String password;
	private Timestamp birthday;
	//存储多个地址的引用
	private Set<Address> addressSet;
	
	public Person() {
		super();
	}

	public Person(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	public Person(String name, String password, Timestamp birthday) {
		super();
		this.name = name;
		this.password = password;
		this.birthday = birthday;
	}

	public Person(String id, String name, String password, Timestamp birthday) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Person [id=" + id + ", name=" + name + ", password=" + password + ", birthday=" + birthday + "]";
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 45)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name", nullable = false, length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "password", nullable = false, length = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "birthday", updatable = true)
	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "person")
	public Set<Address> getAddressSet() {
		return addressSet;
	}

	public void setAddressSet(Set<Address> addressSet) {
		this.addressSet = addressSet;
	}

}
