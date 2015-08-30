<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang=âesâ>

<head>
<meta charset="utf-8" />
<title>Trello</title>
<link rel="stylesheet" href="CSS/default.css" />
<link type="image/x-icon" href="media/favicon.ico" rel="icon" />
</head>

<body id="body-list">

	<header>

		<nav class="oculto">

			<a href="index.html"> HOME </a> <a href="log-in.html"> LOGIN </a> <a
				href="sing-up.html"> SING UP </a>

		</nav>

		<a id="logo" href="BoardServlet"> <img src="media\logo-lightbluesmall-lg.png" />
		</a>

		<div id="user">

			<form method="get" action="LogOutServlet">

				<button class="user-boton" type="submit">
					<h3>${userName}</h3>
				</button>

			</form>

		</div>

		<div id="mas">
			<form method="get" action="AddBoardServlet">

				<button class="newBoard" type="submit">
					<h3>+</h3>
				</button>
			</form>

		</div>

	</header>



	<div id="main-lists">

		<div class="myLists">

			<div id="setBoard">
				<form method="post" action="SetListServlet">

					<input type="text" name="newListName" placeholder="${listName}" maxlength=”39” required>
					<input class="save" type="submit" value="Save"> 
					<input type="hidden" name="listId" value="${listId}"> 
					<input type="hidden" name="boardId" value="${boardId}"> 
					<input type="hidden" name="boardName" value="${boardName}"> 
					
					<a id="cerrar2" href="ListServlet?boardName=${boardName}&boardId=${boardId}">X</a>

				</form>
			</div>
			
		</div>
		
			<c:forEach var="card" items="${cardsList}">
				<div class="card">
					<a href="CardServlet?cardId=${card.name}&listId=${list.id}&listName=${list.name}"> 	${card.name} </a>
				</div>
							
			</c:forEach>
		</div>

	<div class="delete-boton ">
		<form method="get" action="DeleteListServlet">

			<a href="DeleteListServlet?listId=${listId}&listName=${listName}&boardName=${boardName}&boardId=${boardId}">Delete</a>
		</form>
	</div>



	<footer>
		<p>&copy; 2014 Juan Carlos Bonilla Bermejo</p>
	</footer>

</body>

</html>



