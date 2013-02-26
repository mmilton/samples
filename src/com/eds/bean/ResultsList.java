package com.eds.bean;

import java.util.ArrayList;

public class ResultsList {

    private String lookfor;
    private String type;
    private String queryString;
    private String hits;
    private String searchTime;
    private ApiErrorMessage apierrormessage;
    private ArrayList<Result> resultsList = new ArrayList<Result>();
    private ArrayList<Facet> facetsList = new ArrayList<Facet>();

    public ApiErrorMessage getApierrormessage() {
	return apierrormessage;
    }

    public void setApierrormessage(ApiErrorMessage apierrormessage) {
	this.apierrormessage = apierrormessage;
    }

    public String getLookfor() {
	return lookfor;
    }

    public void setLookfor(String lookfor) {
	this.lookfor = lookfor;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getQueryString() {
	return queryString;
    }

    public void setQueryString(String queryString) {
	this.queryString = queryString;
    }

    public String getHits() {
	return hits;
    }

    public String getSearchTime() {
	return searchTime;
    }

    public void setSearchTime(String searchTime) {
	this.searchTime = searchTime;
    }

    public void setHits(String hits) {
	this.hits = hits;
    }

    public ArrayList<Result> getResultsList() {
	return resultsList;
    }

    public void setResultsList(ArrayList<Result> resultsList) {
	this.resultsList = resultsList;
    }

    public ArrayList<Facet> getFacetsList() {
	return facetsList;
    }

    public void setFacetsList(ArrayList<Facet> facetsList) {
	this.facetsList = facetsList;
    }

}
