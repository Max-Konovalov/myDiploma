package ru.mkonovalov.jurdoc.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attachments")
public class Attachment {
    @Id
    protected String id;

    private String extension;

    @OneToOne(mappedBy = "attachment")
    private Document document;

    public Attachment(String extension) {
        this.id = UUID.randomUUID().toString();
        this.extension = extension;
    }

    public String getFilename() {
        return this.id + "." + this.extension;
    }
}
