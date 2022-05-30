package ru.mkonovalov.jurdoc.domain.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Document.class)
public abstract class Document_ {

	public static volatile SingularAttribute<Document, LocalDateTime> createdAt;
	public static volatile SingularAttribute<Document, String> extension;
	public static volatile SingularAttribute<Document, Attachment> attachment;
	public static volatile SingularAttribute<Document, User> createdBy;
	public static volatile SetAttribute<Document, Document> referrals;
	public static volatile SingularAttribute<Document, String> name;
	public static volatile SingularAttribute<Document, DocumentPackage> documentPackage;
	public static volatile SingularAttribute<Document, Long> id;
	public static volatile SingularAttribute<Document, Document> parentDocument;

	public static final String CREATED_AT = "createdAt";
	public static final String EXTENSION = "extension";
	public static final String ATTACHMENT = "attachment";
	public static final String CREATED_BY = "createdBy";
	public static final String REFERRALS = "referrals";
	public static final String NAME = "name";
	public static final String DOCUMENT_PACKAGE = "documentPackage";
	public static final String ID = "id";
	public static final String PARENT_DOCUMENT = "parentDocument";

}

