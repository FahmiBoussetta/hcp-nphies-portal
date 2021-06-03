package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.TaskCodeEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.TaskReasonCodeEnum;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "is_queued")
    private Boolean isQueued;

    @Column(name = "parsed")
    private String parsed;

    @Column(name = "identifier")
    private String identifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private TaskCodeEnum code;

    @Column(name = "description")
    private String description;

    @Column(name = "focus")
    private String focus;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason_code")
    private TaskReasonCodeEnum reasonCode;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization requester;

    @JsonIgnoreProperties(value = { "address", "contacts" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organization owner;

    @OneToMany(mappedBy = "task")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inputOrigResponse", "task" }, allowSetters = true)
    private Set<TaskInput> inputs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task id(Long id) {
        this.id = id;
        return this;
    }

    public String getGuid() {
        return this.guid;
    }

    public Task guid(String guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Boolean getIsQueued() {
        return this.isQueued;
    }

    public Task isQueued(Boolean isQueued) {
        this.isQueued = isQueued;
        return this;
    }

    public void setIsQueued(Boolean isQueued) {
        this.isQueued = isQueued;
    }

    public String getParsed() {
        return this.parsed;
    }

    public Task parsed(String parsed) {
        this.parsed = parsed;
        return this;
    }

    public void setParsed(String parsed) {
        this.parsed = parsed;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public Task identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public TaskCodeEnum getCode() {
        return this.code;
    }

    public Task code(TaskCodeEnum code) {
        this.code = code;
        return this;
    }

    public void setCode(TaskCodeEnum code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFocus() {
        return this.focus;
    }

    public Task focus(String focus) {
        this.focus = focus;
        return this;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public TaskReasonCodeEnum getReasonCode() {
        return this.reasonCode;
    }

    public Task reasonCode(TaskReasonCodeEnum reasonCode) {
        this.reasonCode = reasonCode;
        return this;
    }

    public void setReasonCode(TaskReasonCodeEnum reasonCode) {
        this.reasonCode = reasonCode;
    }

    public Organization getRequester() {
        return this.requester;
    }

    public Task requester(Organization organization) {
        this.setRequester(organization);
        return this;
    }

    public void setRequester(Organization organization) {
        this.requester = organization;
    }

    public Organization getOwner() {
        return this.owner;
    }

    public Task owner(Organization organization) {
        this.setOwner(organization);
        return this;
    }

    public void setOwner(Organization organization) {
        this.owner = organization;
    }

    public Set<TaskInput> getInputs() {
        return this.inputs;
    }

    public Task inputs(Set<TaskInput> taskInputs) {
        this.setInputs(taskInputs);
        return this;
    }

    public Task addInputs(TaskInput taskInput) {
        this.inputs.add(taskInput);
        taskInput.setTask(this);
        return this;
    }

    public Task removeInputs(TaskInput taskInput) {
        this.inputs.remove(taskInput);
        taskInput.setTask(null);
        return this;
    }

    public void setInputs(Set<TaskInput> taskInputs) {
        if (this.inputs != null) {
            this.inputs.forEach(i -> i.setTask(null));
        }
        if (taskInputs != null) {
            taskInputs.forEach(i -> i.setTask(this));
        }
        this.inputs = taskInputs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", guid='" + getGuid() + "'" +
            ", isQueued='" + getIsQueued() + "'" +
            ", parsed='" + getParsed() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", focus='" + getFocus() + "'" +
            ", reasonCode='" + getReasonCode() + "'" +
            "}";
    }
}
