package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaskResponse.
 */
@Entity
@Table(name = "task_response")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "jhi_system")
    private String system;

    @Column(name = "parsed")
    private String parsed;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "taskResponse")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "response", "taskResponse" }, allowSetters = true)
    private Set<TaskOutput> outputs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskResponse id(Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public TaskResponse value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSystem() {
        return this.system;
    }

    public TaskResponse system(String system) {
        this.system = system;
        return this;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getParsed() {
        return this.parsed;
    }

    public TaskResponse parsed(String parsed) {
        this.parsed = parsed;
        return this;
    }

    public void setParsed(String parsed) {
        this.parsed = parsed;
    }

    public String getStatus() {
        return this.status;
    }

    public TaskResponse status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<TaskOutput> getOutputs() {
        return this.outputs;
    }

    public TaskResponse outputs(Set<TaskOutput> taskOutputs) {
        this.setOutputs(taskOutputs);
        return this;
    }

    public TaskResponse addOutputs(TaskOutput taskOutput) {
        this.outputs.add(taskOutput);
        taskOutput.setTaskResponse(this);
        return this;
    }

    public TaskResponse removeOutputs(TaskOutput taskOutput) {
        this.outputs.remove(taskOutput);
        taskOutput.setTaskResponse(null);
        return this;
    }

    public void setOutputs(Set<TaskOutput> taskOutputs) {
        if (this.outputs != null) {
            this.outputs.forEach(i -> i.setTaskResponse(null));
        }
        if (taskOutputs != null) {
            taskOutputs.forEach(i -> i.setTaskResponse(this));
        }
        this.outputs = taskOutputs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskResponse)) {
            return false;
        }
        return id != null && id.equals(((TaskResponse) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskResponse{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", system='" + getSystem() + "'" +
            ", parsed='" + getParsed() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
