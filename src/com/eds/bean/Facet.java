package com.eds.bean;

import java.util.ArrayList;

public class Facet {

    private String id;
    private String label;
    private ArrayList<FacetValue> facetsValueList = new ArrayList<FacetValue>();

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getLabel() {
	return label;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public ArrayList<FacetValue> getFacetsValueList() {
	return facetsValueList;
    }

    public void setFacetsValueList(ArrayList<FacetValue> facetsValueList) {
	this.facetsValueList = facetsValueList;
    }

}
