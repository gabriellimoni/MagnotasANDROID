<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
</head>



 <?php
 
 	include_once 'functions.php';
 
  $nome = $_POST["nome"];
  $apelido = $_POST["nick"];
  $email = $_POST["email"];
  $senha = codificasenha($_POST["senha"]);
  // $cargo = $_POST["optradio"];
  
  $email_valido = 0;
  $apelido_valido = 0;
  
  /*
  if($cargo == "rbAluno")
  	$cargo = 1;
  else
  	$cargo = 0;
  */
  $link = conectar();
  $sql = "INSERT INTO usuario(u_nome, u_apelido, u_senha, u_email)"
  			. " VALUES('$nome', '$apelido', '$senha', '$email');";
			
	if(validaForm()){
		mysqli_query($link, $sql);
		if(mysqli_affected_rows($link) == 1){
			$cad = base64_encode(1);
			redireciona("../login.php?cadastrado=".$cad);
		}
	}
	else{
		// PASSAR VARIÁVEIS POR URL PARA RECARREGAR NO FORM DE CADASTRO
		$url = "?nome=";
		($email_valido == 1)? 
			redireciona("../cadastro.php?email_valido=".$email_valido):
				redireciona("../cadastro.php?apelido_valido=".$apelido_valido);
	}
  	desconectar($link);	
	
	// VALIDAÇÕES -------------------------------------------
	function validaForm($apelido, $email){
		if(existeEmailUsuario($email)){
			
			$emai_valido = "1";
			return false;
		}
		if(existeApelidoUsuario($apelido)){
			
			$apelido_valido = "1";
			return false;
		}
		
		return true;
	}
	
	
	
	function existeEmailUsuario($email)
	{
		$link = conectar();
	
		$sql = "SELECT * FROM usuario WHERE u_email = '$email'";
	
		mysqli_query($link,$sql);
		if(mysqli_affected_rows($link)==1){
			desconectar($link);
			return true;
		}else{
			desconectar($link);
			return false;
		}

	}
	
	function existeApelidoUsuario($apelido)
	{
		$link = conectar();
	
		$sql = "SELECT * FROM usuario WHERE u_apelido = '$apelido'";
	
		mysqli_query($link,$sql);
		if(mysqli_affected_rows($link)==1){
			desconectar($link);
			return true;
		}else{
			desconectar($link);
			return false;
		}

	}
	
	// --------------------------------------------------------------------------------
  
 ?>   
<body>
	
</body>
</html>
