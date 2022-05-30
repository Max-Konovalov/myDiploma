package ru.mkonovalov.jurdoc.domain.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DocumentPackage.class)
public abstract class DocumentPackage_ {

	public static volatile SingularAttribute<DocumentPackage, LocalDateTime> createdAt;
	public static volatile SetAttribute<DocumentPackage, Document> documents;
	public static volatile SingularAttribute<DocumentPackage, String> name;
	public static volatile SingularAttribute<DocumentPackage, User> client;
	public static volatile SingularAttribute<DocumentPackage, Long> id;

	public static final String CREATED_AT = "createdAt";
	public static final String DOCUMENTS = "documents";
	public static final String NAME = "name";
	public static final String CLIENT = "client";
	public static final String ID = "id";

}

