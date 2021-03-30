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
		<button><a href="/ideas">Home</a></button>
	</div>
	<div class="user-form">
		<h2>${idea.content}</h2>
		<table class="table table-hover">
		    <thead>
		        <tr>
		            <th>Likes(${idea.likers.size()})</th>
		            <th>Action</th>
		        </tr>
		    </thead>
			<tbody>
					<tr>
						<td>
							<p><c:forEach items="${idea.likers}" var="e">
							${ e.firstName }
							</c:forEach><p>
						</td>
						<td>
							<c:choose>
								<c:when test="${ idea.ideamaker.id == user.id }">
									<a href="/ideas/edit/${ idea.id }">Edit</a> |
									<a href="/ideas/delete/${ idea.id }">Delete</a>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${ idea.likers.contains(user) }">
											<form action="/${ idea.id }/a/unlike" method="post">
												<input type="hidden" value="unlike" />
												<button>Unlike</button>
											</form>	
										</c:when>
										<c:otherwise>
											<form action="/${ idea.id }/a/like" method="post">
												<input type="hidden" value="like" />
												<button>Like</button>
											</form>							
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
			</tbody>
		</table>
	</div>
</body>
</html>