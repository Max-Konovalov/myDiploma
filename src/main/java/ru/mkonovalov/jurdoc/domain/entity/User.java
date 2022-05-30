package ru.mkonovalov.jurdoc.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
       uniqueConstraints = {
            @UniqueConstraint(columnNames = "username"),
            @UniqueConstraint(columnNames = "email")
       })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(length = 24, nullable = false)
    private String username;

    @Column(length = 128, nullable = false)
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String middleName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<DocumentPackage> accessedDocumentPackages;

    public boolean hasAccessToDocument(Document document) {
        boolean res = false;

        for (DocumentPackage dp : accessedDocumentPackages) {
            if (dp.getDocuments().contains(document)) {
                res = true;
                break;
            }
        }

        return res;
    }

    public boolean hasAccessToDocumentPackage(DocumentPackage documentPackage) {
        return this.accessedDocumentPackages.contains(documentPackage);
    }
}
