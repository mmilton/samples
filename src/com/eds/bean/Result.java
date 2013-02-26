package com.eds.bean;

import java.util.ArrayList;

public class Result {

    private String resultId;
    private String pubType;
    private String dbId;
    private String dbLabel;
    private String an;
    private String pLink;
    private String PDF;
    private String HTML;
    private String pubTypeID;
    private ArrayList<BookJacket> bookJaketList = new ArrayList<BookJacket>();
    private ArrayList<CustomLink> CustomListList = new ArrayList<CustomLink>();
    private ArrayList<Item> itemList = new ArrayList<Item>();

    public String getPubTypeID() {
	return pubTypeID;
    }

    public void setPubTypeID(String pubTypeID) {
	this.pubTypeID = pubTypeID;
    }

    public String getResultId() {
	return resultId;
    }

    public void setResultId(String resultId) {
	this.resultId = resultId;
    }

    public String getPubType() {
	return pubType;
    }

    public void setPubType(String pubType) {
	this.pubType = pubType;
    }

    public String getDbId() {
	return dbId;
    }

    public void setDbId(String dbId) {
	this.dbId = dbId;
    }

    public String getDbLabel() {
	return dbLabel;
    }

    public void setDbLabel(String dbLabel) {
	this.dbLabel = dbLabel;
    }

    public String getAn() {
	return an;
    }

    public void setAn(String an) {
	this.an = an;
    }

    public String getpLink() {
	return pLink;
    }

    public void setpLink(String pLink) {
	this.pLink = pLink;
    }

    public String getPDF() {
	return PDF;
    }

    public void setPDF(String pDF) {
	PDF = pDF;
    }

    public String getHTML() {
	return HTML;
    }

    public void setHTML(String hTML) {
	HTML = hTML;
    }

    public ArrayList<BookJacket> getBookJaketList() {
	return bookJaketList;
    }

    public void setBookJaketList(ArrayList<BookJacket> bookJaketList) {
	this.bookJaketList = bookJaketList;
    }

    public ArrayList<CustomLink> getCustomListList() {
	return CustomListList;
    }

    public void setCustomListList(ArrayList<CustomLink> customListList) {
	CustomListList = customListList;
    }

    public ArrayList<Item> getItemList() {
	return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
	this.itemList = itemList;
    }

}
