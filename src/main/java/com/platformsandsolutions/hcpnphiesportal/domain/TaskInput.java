package com.platformsandsolutions.hcpnphiesportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.EventCodingEnum;
import com.platformsandsolutions.hcpnphiesportal.domain.enumeration.ResourceTypeEnum;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaskInput.
 */
@Entity
@Table(name = "task_input")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaskInput implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "input_include")
    private ResourceTypeEnum inputInclude;

    @Enumerated(EnumType.STRING)
    @Column(name = "input_exclude")
    private ResourceTypeEnum inputExclude;

    @Enumerated(EnumType.STRING)
    @Column(name = "input_include_message")
    private EventCodingEnum inputIncludeMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "input_exclude_message")
    private EventCodingEnum inputExcludeMessage;

    @Column(name = "input_count")
    private Integer inputCount;

    @Column(name = "input_start")
    private Instant inputStart;

    @Column(name = "input_end")
    private Instant inputEnd;

    @Column(name = "input_line_item")
    private Integer inputLineItem;

    @JsonIgnoreProperties(value = { "item", "detailItem", "subDetailItem" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ReferenceIdentifier inputOrigResponse;

    @ManyToOne
    @JsonIgnoreProperties(value = { "requester", "owner", "inputs" }, allowSetters = true)
    private Task task;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskInput id(Long id) {
        this.id = id;
        return this;
    }

    public ResourceTypeEnum getInputInclude() {
        return this.inputInclude;
    }

    public TaskInput inputInclude(ResourceTypeEnum inputInclude) {
        this.inputInclude = inputInclude;
        return this;
    }

    public void setInputInclude(ResourceTypeEnum inputInclude) {
        this.inputInclude = inputInclude;
    }

    public ResourceTypeEnum getInputExclude() {
        return this.inputExclude;
    }

    public TaskInput inputExclude(ResourceTypeEnum inputExclude) {
        this.inputExclude = inputExclude;
        return this;
    }

    public void setInputExclude(ResourceTypeEnum inputExclude) {
        this.inputExclude = inputExclude;
    }

    public EventCodingEnum getInputIncludeMessage() {
        return this.inputIncludeMessage;
    }

    public TaskInput inputIncludeMessage(EventCodingEnum inputIncludeMessage) {
        this.inputIncludeMessage = inputIncludeMessage;
        return this;
    }

    public void setInputIncludeMessage(EventCodingEnum inputIncludeMessage) {
        this.inputIncludeMessage = inputIncludeMessage;
    }

    public EventCodingEnum getInputExcludeMessage() {
        return this.inputExcludeMessage;
    }

    public TaskInput inputExcludeMessage(EventCodingEnum inputExcludeMessage) {
        this.inputExcludeMessage = inputExcludeMessage;
        return this;
    }

    public void setInputExcludeMessage(EventCodingEnum inputExcludeMessage) {
        this.inputExcludeMessage = inputExcludeMessage;
    }

    public Integer getInputCount() {
        return this.inputCount;
    }

    public TaskInput inputCount(Integer inputCount) {
        this.inputCount = inputCount;
        return this;
    }

    public void setInputCount(Integer inputCount) {
        this.inputCount = inputCount;
    }

    public Instant getInputStart() {
        return this.inputStart;
    }

    public TaskInput inputStart(Instant inputStart) {
        this.inputStart = inputStart;
        return this;
    }

    public void setInputStart(Instant inputStart) {
        this.inputStart = inputStart;
    }

    public Instant getInputEnd() {
        return this.inputEnd;
    }

    public TaskInput inputEnd(Instant inputEnd) {
        this.inputEnd = inputEnd;
        return this;
    }

    public void setInputEnd(Instant inputEnd) {
        this.inputEnd = inputEnd;
    }

    public Integer getInputLineItem() {
        return this.inputLineItem;
    }

    public TaskInput inputLineItem(Integer inputLineItem) {
        this.inputLineItem = inputLineItem;
        return this;
    }

    public void setInputLineItem(Integer inputLineItem) {
        this.inputLineItem = inputLineItem;
    }

    public ReferenceIdentifier getInputOrigResponse() {
        return this.inputOrigResponse;
    }

    public TaskInput inputOrigResponse(ReferenceIdentifier referenceIdentifier) {
        this.setInputOrigResponse(referenceIdentifier);
        return this;
    }

    public void setInputOrigResponse(ReferenceIdentifier referenceIdentifier) {
        this.inputOrigResponse = referenceIdentifier;
    }

    public Task getTask() {
        return this.task;
    }

    public TaskInput task(Task task) {
        this.setTask(task);
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskInput)) {
            return false;
        }
        return id != null && id.equals(((TaskInput) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskInput{" +
            "id=" + getId() +
            ", inputInclude='" + getInputInclude() + "'" +
            ", inputExclude='" + getInputExclude() + "'" +
            ", inputIncludeMessage='" + getInputIncludeMessage() + "'" +
            ", inputExcludeMessage='" + getInputExcludeMessage() + "'" +
            ", inputCount=" + getInputCount() +
            ", inputStart='" + getInputStart() + "'" +
            ", inputEnd='" + getInputEnd() + "'" +
            ", inputLineItem=" + getInputLineItem() +
            "}";
    }
}
