<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=”es”>

<head>
<meta charset="utf-8" />
<title>Trello</title>
<link rel="stylesheet" href="CSS/default.css" />
<link type="image/x-icon" href="media/favicon.ico" rel="icon" />
</head>

<body id="body-login">

	<header>

		<nav>

			<a href="index.html"> HOME </a> <a href="log-in.html"> LOGIN </a> <a
				href="sing-up.html"> SING UP </a>

		</nav>

		<div id="botones">
			<a class="sing-up" href="sing-up.html"> Sing Up</a> <a id="login"
				href="log-in.html"> Log In </a>
		</div>
		<a href="index.html"> <img src="media\logo-lightbluesmall-lg.png"
			alt="Home" />
		</a>


	</header>


	<div id="main-login">


		<h1>Create an Account</h1>
		<form method="get" action="SingUpServlet">

			<label>
				<h2>Name:</h2> <input type="text" name="name" required>
			</label> <label>
				<h2>Username:</h2> <input type="text" name="username" required>
			</label> <label>
				<h2>Email:</h2> <input type=email name="usermail" required>
			</label> <label>
				<h2>Password:</h2> <input type="password" name="password"
				placeholder="e.g., ***********" required>
			</label> <label>
				<h2>Repeat Password:</h2> <input type="password" name="password2"
				placeholder="e.g., ***********" required>
			</label>

			<p>
				<button class="sing-up" type="submit">
					<h3>Create New Account</h3>
				</button>
			</p>

			<div class="mensaje">
				<h2>${message1}</h2>
			</div>

		</form>


	</div>


	<footer class="footer-index">
		<p>&copy; 2014 Juan Carlos Bonilla Bermejo</p>
	</footer>

</body>

</html>






