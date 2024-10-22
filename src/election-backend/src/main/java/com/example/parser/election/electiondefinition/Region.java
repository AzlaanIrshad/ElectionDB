package com.example.parser.election.electiondefinition;

public class Region {
    private String id;
    private String name;
    private String regionCategory;
    private String superiorRegionCategory;

    // Constructor
    public Region(String id, String name, String regionCategory, String superiorRegionCategory) {
        this.id = id;
        this.name = name;
        this.regionCategory = regionCategory;
        this.superiorRegionCategory = superiorRegionCategory;
    }

    // Getters en Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionCategory() {
        return regionCategory;
    }

    public void setRegionCategory(String regionCategory) {
        this.regionCategory = regionCategory;
    }

    public String getSuperiorRegionCategory() {
        return superiorRegionCategory;
    }

    public void setSuperiorRegionCategory(String superiorRegionCategory) {
        this.superiorRegionCategory = superiorRegionCategory;
    }
}
