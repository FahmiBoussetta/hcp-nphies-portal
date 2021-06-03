package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaskOutput.
 */
@Entity
@Table(name = "task_output")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskOutput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "error_output")
    private String errorOutput;

    @JsonIgnoreProperties(value = { "item", "detailItem", "subDetailItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ReferenceIdentifier response;

    @ManyToOne
    @JsonIgnoreProperties(value = { "outputs" }, allowSetters = true)
    private TaskResponse taskResponse;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskOutput id(Long id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public TaskOutput status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorOutput() {
        return this.errorOutput;
    }

    public TaskOutput errorOutput(String errorOutput) {
        this.errorOutput = errorOutput;
        return this;
    }

    public void setErrorOutput(String errorOutput) {
        this.errorOutput = errorOutput;
    }

    public ReferenceIdentifier getResponse() {
        return this.response;
    }

    public TaskOutput response(ReferenceIdentifier referenceIdentifier) {
        this.setResponse(referenceIdentifier);
        return this;
    }

    public void setResponse(ReferenceIdentifier referenceIdentifier) {
        this.response = referenceIdentifier;
    }

    public TaskResponse getTaskResponse() {
        return this.taskResponse;
    }

    public TaskOutput taskResponse(TaskResponse taskResponse) {
        this.setTaskResponse(taskResponse);
        return this;
    }

    public void setTaskResponse(TaskResponse taskResponse) {
        this.taskResponse = taskResponse;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskOutput)) {
            return false;
        }
        return id != null && id.equals(((TaskOutput) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskOutput{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", errorOutput='" + getErrorOutput() + "'" +
            "}";
    }
}
