 <?php
 
 	include_once 'functions.php';
	
  $email = $_POST["email"];
  $senha = codificasenha($_POST["senha"]);
  
  
  $link = conectar();
  $sql = "SELECT * FROM usuario WHERE u_email = '$email' AND u_senha = '$senha'";
			
	$query = mysqli_query($link, $sql);
	$row = mysqli_fetch_array($query);
	$id = $row["_id"];
	
	
	if(mysqli_affected_rows($link) == 1){
				
		$result["result"] = 1;
		
		$result["id"] = $id;
				
		echo json_encode($result);
				
	}
	else{
		$result["result"] = 0;
		
		$result["erroMsg"] = getErrorMsg();
		
		echo json_encode($result);
	}

  	desconectar($link);	
	
	
	
	function getErrorMsg(){
		return "Email ou senha incorretos!";
	}
	
	// --------------------------------------------------------------------------------
  
 ?> 