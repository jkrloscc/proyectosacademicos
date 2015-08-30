<!DOCTYPE html>
<html lang=”es”>

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


	<div id="main-boards">



		<div id="deleteBoard">


			<form method="post" action="DeleteBoardServlet">

				<h3> "${boardName}" will be removed </h3>
				<h2>Are you sure?</h2>
				<input type="hidden" name="boardId" value="${boardId}">
			<a  href="DeleteBoardServlet"><button class="boton-si" >YES</button></a>
			<a class="boton-no" href="BoardServlet">NO</a>
				
			</form>

		</div>

	</div>


	<footer>
		<p>&copy; 2014 Juan Carlos Bonilla Bermejo</p>
	</footer>

</body>

</html>



