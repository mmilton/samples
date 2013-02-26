package com.eds.bean;

public class ApiErrorMessage {

    private String DetailedErrorDescription;
    private String ErrorDescription;
    private String ErrorNumber;

    public String getDetailedErrorDescription() {
	return DetailedErrorDescription;
    }

    public void setDetailedErrorDescription(String detailedErrorDescription) {
	DetailedErrorDescription = detailedErrorDescription;
    }

    public String getErrorDescription() {
	return ErrorDescription;
    }

    public void setErrorDescription(String errorDescription) {
	ErrorDescription = errorDescription;
    }

    public String getErrorNumber() {
	return ErrorNumber;
    }

    public void setErrorNumber(String errorNumber) {
	ErrorNumber = errorNumber;
    }

}
