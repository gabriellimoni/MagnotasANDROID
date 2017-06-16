 <?php
 
 	include_once 'functions.php';
 
  $assunto = $_POST["assunto"];
  $texto = $_POST["texto"];
  $categoria = $_POST["categoria"];
  $usuario = $_POST["usuario"];
  $id = $_POST["id"];
  $data_expirada = calculaDataExpiracao($categoria);
  $sucesso_msg = "Nota atualizada com sucesso!";
  
  $link = conectar();
  if($id == "-1"){
	$sql = "INSERT INTO nota(n_assunto, n_texto, n_categoria, n_usu, n_data_expira)"
  			. " VALUES('$assunto', '$texto', $categoria, $usuario, '$data_expirada');";
	$sucesso_msg = "Nota inserida com sucesso!";
  }
  else
	$sql = "UPDATE nota SET n_assunto = '$assunto', n_texto = '$texto', n_categoria = $categoria, n_data_expira = '$data_expirada'"
			. " WHERE _id = $id";
	
	
	mysqli_query($link, $sql);
	if(mysqli_affected_rows($link) == 1){
				
		$result["sucesso"] = $sucesso_msg;
				
		$result["result"] = 1;
				
		echo json_encode($result);
				
	}
	else{
		$result["result"] = 0;
		
		$result["erro"] = getErrorMsg();
		
		echo json_encode($result);
	}
	
	//echo $data_expirada;

  	desconectar($link);	
	
	
	function calculaDataExpiracao($categoria){
		$data_atual = date_create();
		$dias = 0;
		$horas = 0;
		$minutos = 0;
		
		if($categoria == 0){
			$minutos = 1;
		}
		else if($categoria == 1){
			$dias = 7;
		}
		else if($categoria == 2){
			$dias = 15;
		}
		else if($categoria == 3){
			$horas = 12;
		}
		
		return strftime('%Y-%m-%d %H:%M:%S',(mktime(	
						date_format($data_atual,"H")+$horas,
						date_format($data_atual,"i")+$minutos,
						date_format($data_atual,"s"),
						date_format($data_atual,"m"),
						date_format($data_atual,"d")+$dias,
						date_format($data_atual,"Y"))));
		
	}
	
	function getErrorMsg(){
		return "Email ja cadastrado!";
	}
	
	// --------------------------------------------------------------------------------
  
 ?> 