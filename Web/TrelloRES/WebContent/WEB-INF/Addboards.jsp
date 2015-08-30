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

<body>

	<header>

		<nav class="oculto">

			<a href="index.html"> HOME </a> <a href="log-in.html"> LOGIN </a> <a
				href="sing-up.html"> SING UP </a>

		</nav>

		<a id="logo" href="BoardServlet">
		 <img src="media\logo-lightbluesmall-lg.png"/>
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


	<div id="main-boards">

		<div id="myBoards">
			<img src="media\user.png" />
			<h1>My boards</h1>


			<div id="addBoard">

				<form method="post" action="AddBoardServlet">
					
					<h2>Title</h2>

					<input type="text" name="boardName"> <input class="create"
						type="submit" value="Create" maxlength=”39”>
						
					<div class="cerrar">
						<a href="BoardServlet">X</a>
					</div>
					<input type="hidden" name="boardOwner" value="${boardOwner}"> 


				</form>

			</div>

		</div>

		<div id="content">

			<c:forEach var="board" items="${boardList}">
				<div class="board">
					<a href="ListServlet?boardid=${board.id}"> ${board.name}</a>
				</div>
			</c:forEach>

		</div>

	</div>


	<footer>
		<p>&copy; 2014 Juan Carlos Bonilla Bermejo</p>
	</footer>

</body>

</html>



