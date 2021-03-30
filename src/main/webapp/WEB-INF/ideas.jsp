<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" 
		integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
		crossorigin="anonymous">
	<link rel="stylesheet" href="/css/main.css"/>
    <title>Ideas page</title>
</head>
<body>
	<div class="titles">
		<h1>Welcome, ${ user.firstName }</h1>
		<button><a href="/logout">Logout</a></button>
		<button><a href="/ideas/new">New idea</a></button>
	</div>
	<div class="user-form">
		<h2>Ideas</h2>
		<table class="table table-hover">
		    <thead>
		        <tr>
		            <th>Idea</th>
		            <th>Created by</th>
		            <th>Likes</th>
		            <th>Action</th>
		        </tr>
		    </thead>
			<tbody>
				<c:forEach items="${ ideas}" var="e">
					<tr>
						<td><a href="/ideas/${e.id}">${ e.content }</a></td>
						<td>${ e.ideamaker.getFirstName() }</td>
						<td>${ e.likers.size() }</td>
						<td>
							<c:choose>
								<c:when test="${ e.ideamaker.id == user.id }">
									<button><a href="/ideas/edit/${ e.id }">Edit</a></button>
									<button><a href="/ideas/delete/${ e.id }">Delete</a></button>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${ e.likers.contains(user) }">
											<form action="/${ e.id }/a/unlike" method="post">
												<input type="hidden" value="unlike" />
												<button>Unlike</button>
											</form>	
										</c:when>
										<c:otherwise>
											<form action="/${ e.id }/a/like" method="post">
												<input type="hidden" value="like" />
												<button>Like</button>
											</form>							
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>