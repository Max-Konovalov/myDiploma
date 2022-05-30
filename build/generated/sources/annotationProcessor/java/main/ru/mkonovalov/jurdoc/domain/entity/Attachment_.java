package ru.mkonovalov.jurdoc.domain.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Attachment.class)
public abstract class Attachment_ {

	public static volatile SingularAttribute<Attachment, String> extension;
	public static volatile SingularAttribute<Attachment, Document> document;
	public static volatile SingularAttribute<Attachment, String> id;

	public static final String EXTENSION = "extension";
	public static final String DOCUMENT = "document";
	public static final String ID = "id";

}

