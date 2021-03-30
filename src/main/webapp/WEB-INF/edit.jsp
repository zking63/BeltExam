<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" 
		integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
		crossorigin="anonymous">
	<link rel="stylesheet" href="/css/main.css"/>
    <title>Event page</title>
</head>
<body>
	<div class="titles">
		<h1>Hello, ${ user.firstName }</h1>
		<button><a href="/logout">Logout</a></button>
		<button><a href="/ideas">Home</a></button>
	</div>
	<div class="user-form">
		<h1>Create an idea</h1>
		<h1>${idea.content}</h1>
	    <form:form method="POST" action="/ideas/edit/${id}" modelAttribute="idea">
	    	<form:hidden value="${ user.id }" path="ideamaker"/>
	    	<p>
	            <form:label path="content">Idea:</form:label>
	            <form:errors path="content"></form:errors>
	            <form:input type="content" path="content"/>
	        </p>
	        <input type="submit" value="Create!"/>
	        <button><a href="/ideas/delete/${ idea.id }">Delete</a></button>
	    </form:form>
	</div>  
</body>
</html>