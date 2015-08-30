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

		<div class="myCard">
		<form method="get" action="SetCardServlet">
			<input type="text" name="newCardName" placeholder="${card.name}" maxlength=”39” required>
			<input type="hidden" name="cardId" value="${card.id}"> 
		</form>
			<h3>in list </h3>
			
			<h1> ${listName} </h1>
			
		</div>
		
				
		<div id="setDescription">

				<form method="post" action="SetCardServlet">
					
					<h2>Move Card</h2>

					<fieldset class="squareR">
							<legend> Lists </legend>
							
							<c:forEach var="list" items="${listsList}">
								<p><label> <input type=radio name=newListId required value="${list.id}"> ${list.name} in 								
								<c:forEach var="board" items="${boardsList}">
									<c:if test="${board.id eq list.board}">
									${board.name}
								 <input type="hidden" name="listBoard" value="${list.board}"> 
								 <input type="hidden" name="cardId" value="${card.id}"> 
								 
									
									</c:if>
								</c:forEach>
								</label></p>
							</c:forEach>
					</fieldset>
					<input class="create" type="submit" value="Move">
					
					<div class="cerrar">
						<a href="BoardServlet">X</a>
					</div>

				</form>
			
		</div>
		
		<div id="addlist2">
		
					<p>${card.description} </p>
		
				<form method="get" action="SetDescriptionCardServlet">

					<input type="text" name="cardDescription" placeholder="Edit the card description." maxlength=”139” required>
					<input type="hidden" name="cardId" value="${card.id}"> 
				</form>
		</div>
	

	</div>
	
	
	<div class="delete-boton ">

			<a href="DeleteCardServlet?cardId=${card.id}&cardName=${card.name}&listName=${list.name}&listId=${list.id}&boardServlet=${boardServlet}&boardId=${boardId}">Delete</a>
	</div>


	<footer>
		<p>&copy; 2014 Juan Carlos Bonilla Bermejo</p>
	</footer>

</body>

</html>



