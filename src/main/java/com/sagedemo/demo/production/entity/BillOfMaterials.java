package com.sagedemo.demo.production.entity;

import com.sagedemo.demo.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "production_bom")
public class BillOfMaterials extends BaseEntity {
    private String name;
    @ElementCollection
    private List<String> components; // MVP: just names, later link to Product
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getComponents() { return components; }
    public void setComponents(List<String> components) { this.components = components; }
}
