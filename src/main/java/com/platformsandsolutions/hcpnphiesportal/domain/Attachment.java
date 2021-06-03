package com.platformsandsolutions.hcpnphiesportal.domain;

import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.LanguageEnum;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private LanguageEnum language;

    @Column(name = "is_data")
    private Boolean isData;

    @Lob
    @Column(name = "data_file")
    private byte[] dataFile;

    @Column(name = "data_file_content_type")
    private String dataFileContentType;

    @Column(name = "url")
    private String url;

    @Column(name = "attachment_size")
    private Integer attachmentSize;

    @Lob
    @Column(name = "hash")
    private byte[] hash;

    @Column(name = "hash_content_type")
    private String hashContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Attachment id(Long id) {
        this.id = id;
        return this;
    }

    public String getContentType() {
        return this.contentType;
    }

    public Attachment contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getTitle() {
        return this.title;
    }

    public Attachment title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LanguageEnum getLanguage() {
        return this.language;
    }

    public Attachment language(LanguageEnum language) {
        this.language = language;
        return this;
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language;
    }

    public Boolean getIsData() {
        return this.isData;
    }

    public Attachment isData(Boolean isData) {
        this.isData = isData;
        return this;
    }

    public void setIsData(Boolean isData) {
        this.isData = isData;
    }

    public byte[] getDataFile() {
        return this.dataFile;
    }

    public Attachment dataFile(byte[] dataFile) {
        this.dataFile = dataFile;
        return this;
    }

    public void setDataFile(byte[] dataFile) {
        this.dataFile = dataFile;
    }

    public String getDataFileContentType() {
        return this.dataFileContentType;
    }

    public Attachment dataFileContentType(String dataFileContentType) {
        this.dataFileContentType = dataFileContentType;
        return this;
    }

    public void setDataFileContentType(String dataFileContentType) {
        this.dataFileContentType = dataFileContentType;
    }

    public String getUrl() {
        return this.url;
    }

    public Attachment url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getAttachmentSize() {
        return this.attachmentSize;
    }

    public Attachment attachmentSize(Integer attachmentSize) {
        this.attachmentSize = attachmentSize;
        return this;
    }

    public void setAttachmentSize(Integer attachmentSize) {
        this.attachmentSize = attachmentSize;
    }

    public byte[] getHash() {
        return this.hash;
    }

    public Attachment hash(byte[] hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public String getHashContentType() {
        return this.hashContentType;
    }

    public Attachment hashContentType(String hashContentType) {
        this.hashContentType = hashContentType;
        return this;
    }

    public void setHashContentType(String hashContentType) {
        this.hashContentType = hashContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attachment)) {
            return false;
        }
        return id != null && id.equals(((Attachment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attachment{" +
            "id=" + getId() +
            ", contentType='" + getContentType() + "'" +
            ", title='" + getTitle() + "'" +
            ", language='" + getLanguage() + "'" +
            ", isData='" + getIsData() + "'" +
            ", dataFile='" + getDataFile() + "'" +
            ", dataFileContentType='" + getDataFileContentType() + "'" +
            ", url='" + getUrl() + "'" +
            ", attachmentSize=" + getAttachmentSize() +
            ", hash='" + getHash() + "'" +
            ", hashContentType='" + getHashContentType() + "'" +
            "}";
    }
}
