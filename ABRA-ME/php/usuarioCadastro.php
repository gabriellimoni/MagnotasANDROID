 <?php
 
 	include_once 'functions.php';
 
  $nome = $_POST["nome"];
  $apelido = $_POST["nick"];
  $email = $_POST["email"];
  $senha = codificasenha($_POST["senha"]);
  
  $email_valido = 0;
  $apelido_valido = 0;
  
  $link = conectar();
  $sql = "INSERT INTO usuario(u_nome, u_apelido, u_senha, u_email)"
  			. " VALUES('$nome', '$apelido', '$senha', '$email');";
			
	// valida os dados
	if(!validaForm($email)){
		$erro["erroMsg"] = getErrorMsg();
		
		$erro["result"] = 0;
			
		echo json_encode($erro);
	}
	else{
	
		mysqli_query($link, $sql);
		if(mysqli_affected_rows($link) == 1){
				
			$result["apelido"] = $apelido;
		
			$result["email"] = $email;
				
			$result["result"] = 1;
				
			echo json_encode($result);
				
		}
	}

  	desconectar($link);	
	
	// VALIDAÇÕES ------------------------------------------- setar a variavel $erro com a string correspondente
	function validaForm($str){
		if(existeEmailUsuario($str)){
			return false;
		}
		
		return true;
	}
	
	function existeEmailUsuario($str)
	{ 
		$link2 = conectar();
	
		$cmd = "SELECT * FROM usuario WHERE u_email = '$str'";
	
		mysqli_query($link2,$cmd);
		if(mysqli_affected_rows($link2)==1){
			desconectar($link2);
			return true;
		}else{
			desconectar($link2);
			return false;
		}

	}
	
	function getErrorMsg(){
		return "Email ja cadastrado!";
	}
	
	// --------------------------------------------------------------------------------
  
 ?> 