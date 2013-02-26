package com.eds.bean;

import java.util.ArrayList;

public class Record {

    private String pubType;
    private String dbId;
    private String dbLabel;
    private String an;
    private String pLink;
    private String pdfLink;
    private String htmlFullText;
    private String PDF;
    private String HTML;
    private String PubTypeId;
    private String ResultId;
    private ApiErrorMessage apierrormessage;

    public ApiErrorMessage getApierrormessage() {
	return apierrormessage;
    }

    public void setApierrormessage(ApiErrorMessage apierrormessage) {
	this.apierrormessage = apierrormessage;
    }

    public String getResultId() {
	return ResultId;
    }

    public void setResultId(String resultId) {
	ResultId = resultId;
    }

    private ArrayList<BookJacket> bookJaketList = new ArrayList<BookJacket>();
    private ArrayList<CustomLink> CustomListList = new ArrayList<CustomLink>();
    private ArrayList<Item> itemList = new ArrayList<Item>();

    public String getPubTypeId() {
	return PubTypeId;
    }

    public void setPubTypeId(String pubTypeId) {
	PubTypeId = pubTypeId;
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

    public String getPdfLink() {
	return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
	this.pdfLink = pdfLink;
    }

    public String getHtmlFullText() {
	return htmlFullText;
    }

    public void setHtmlFullText(String htmlFullText) {
	this.htmlFullText = htmlFullText;
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
