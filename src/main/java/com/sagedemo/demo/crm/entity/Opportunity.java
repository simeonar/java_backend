package com.sagedemo.demo.crm.entity;

import com.sagedemo.demo.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "crm_opportunity")
public class Opportunity extends BaseEntity {
    private String name;
    private String stage;
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
