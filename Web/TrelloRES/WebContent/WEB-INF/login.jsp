<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=”es”>

	<head>
		<meta charset="utf-8" />
		<title>Trello</title>
		<link rel="stylesheet" href="CSS/default.css"/>
		<link type="image/x-icon" href="media/favicon.ico" rel="icon" />
	</head>
		
	<body id="body-login">
				
				<header>
				
					<nav>
						
						 <a href="index.html"> HOME </a> 
						 <a href="log-in.html"> LOGIN </a>
						 <a href="sing-up.html"> SING UP </a>
						
					</nav>
					
					<div id="botones">
						<a class="sing-up" href="sing-up.html"> Sing Up</a>
						<a id="login" href="log-in.html"> Log In </a>
					</div>
					<a href="index.html"> <img src="media\logo-lightbluesmall-lg.png" alt="Home" /> </a>
					
					
				</header>				
				
				
				<div id="main-login">
								
					
						<h1>Login</h1>
						<form method="POST" action="LoginServlet">
						
							<label> <h2>User: </h2> <input type="text" name="username"  maxlength=”19”required></label> 
							
							<label> <h2>Password:</h2> <input type="password" name="password" 
							placeholder="e.g., ***********"  maxlength=”39” required></label> 
							 <input type="hidden" name="boardid" value="${board.id }">
												
							<p> <button class="sing-up"  type="submit"> <h3>Login </h3></button> </p>
							
							
							<p><a href="forgot.html">Forgot your password? </a></p>				
							
							<p>New here? <a href="sing-up.html">Create an Account </a> </p>							
										 
						</form>		
					
							<div class="mensaje"> <h2>${messages} </h2> </div>
				</div>
				
				
				
				
				<footer id="footer-login">
					<p>&copy; 2014  Juan Carlos Bonilla Bermejo</p>
				</footer>
			
	</body>

</html>



