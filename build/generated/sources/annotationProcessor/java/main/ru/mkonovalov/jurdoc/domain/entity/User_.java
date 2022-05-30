package ru.mkonovalov.jurdoc.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Role> role;
	public static volatile SetAttribute<User, DocumentPackage> accessedDocumentPackages;
	public static volatile SingularAttribute<User, String> middleName;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PASSWORD = "password";
	public static final String ROLE = "role";
	public static final String ACCESSED_DOCUMENT_PACKAGES = "accessedDocumentPackages";
	public static final String MIDDLE_NAME = "middleName";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";

}

