package com.sagedemo.backend.crm.entity;

import com.sagedemo.backend.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "crm_opportunity")
public class Opportunity extends BaseEntity {
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 30)
    private String stage;

    @NotNull
    private Double value;
    @ManyToOne
    private Lead lead;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
    public Lead getLead() { return lead; }
    public void setLead(Lead lead) { this.lead = lead; }
}
