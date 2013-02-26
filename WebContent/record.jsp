<!DOCTYPE html >
<html>
<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%> 
<%@page import="com.eds.bean.*" language="java" %> 
<%@page import="java.net.URLEncoder" %> <%@page import="java.util.ArrayList" %><%@page import="com.eds.helper.*"%>

<% Record record = (Record)request.getSession().getAttribute("record"); %> 

<%String picurl="";%>

<link rel="stylesheet" href="style/style.css" type="text/css" media="screen" />

</head>
<body>
<% 

if(record.getApierrormessage()!=null)
{
	ApiErrorMessage aem=record.getApierrormessage();
    String errorString=aem.getErrorDescription();
    		out.println(errorString);
}
else{
%>

<!-- Here we address record -->
<div class="center">
<div class="record" align="left" >
<div class="topbar">
<a href="javascript:history.back();" class="back"><%out.println("<< Results List");%></a>
</div>

<%ArrayList<Item> itemList=record.getItemList();%>
<% ArrayList<CustomLink> CustomListList=record.getCustomListList();%>
<%
    ArrayList<BookJacket> bookJaketList=record.getBookJaketList();
%>


<table>
<tr>
<td>
<table>
<% 
String data="";
for(int i=0;i<itemList.size();i++){
	
	Item item=itemList.get(i);	
	data =item.getData();
 
%>

<% 
if(item.getGroup().equals("Ti"))
{
%>
<h1><%out.println(data);%></h1>
	
<%}else{

if(item.getGroup().equals("Au")||item.getGroup().equals("Su")||item.getGroup().equals("Ca"))
{
	 BuildSearchLink bsl=new BuildSearchLink();
	 String value=bsl.buildSearchLink(item.getData());
%>

	<tr>
	<td width="20%" align="left"><div><strong><%out.println(item.getLabel()+":");%></strong></div><td>
	<td><%=value%><br/></td>
	</tr>
		
<%}

else{%>
	
	
	<tr>
	<td width="20%" align="left"><div ><strong><%out.println(item.getLabel()+":");%></strong></div><td>
	<td width="80%" align="left"><div ><%out.println(data);%></div><td>
	</tr>


<%}


}}%>
</table>
</td>

<td>



<%
String bookJacket ="";

			
if(record.getBookJaketList().size()>0){
	                 
	               %>
	             
                    <% for(int b=0;b<record.getBookJaketList().size();b++){
                    	
                    	
                            if(record.getBookJaketList().get(b).getSize().equals("medium")){
                            	
                            	
                            	bookJacket = record.getBookJaketList().get(b).getTarget();
                                //break;
                            } 
                       }
   %>                                          
  
  <div class="center">
  <a href=""> <img src="<%=bookJacket%>"  width="150px" height="200px" style="border:2px solid white"/></a> 
  </div>
               <% }
               
else{%><%}}%> 

</td>
</tr>

</table>
</div>
</div>



</body>

</html>