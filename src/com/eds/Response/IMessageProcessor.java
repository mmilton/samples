package com.eds.Response;

import java.io.BufferedReader;

import com.eds.bean.Record;
import com.eds.bean.Response;
import com.eds.bean.ResultsList;
import com.eds.bean.SessionToken;

public interface IMessageProcessor {

    public SessionToken buildSessionToken(BufferedReader reader);

    public ResultsList buildResultsList(Response response);

    public String buildUIDAuthRequest(String username, String password);

    public Record buildRecord(Response response);

    public String GetContentType();

}
