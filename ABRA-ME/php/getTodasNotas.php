 <?php
 
 	include_once 'functions.php';
	
	verificaDataExpiracao();
	
	$link = conectar();
	$sql = "SELECT n._id as n_id, n_assunto, n_texto, n_categoria, n_data_expira, u_apelido, n_usu FROM nota n, usuario u".
				" WHERE u._id = n_usu ORDER BY n_categoria;";
	
	
	$res = mysqli_query($link, $sql);
	
	
	if(mysqli_num_rows($res) > 0){
		
		$result["notas"] = array();
		
		while($row = mysqli_fetch_array($res)){
			$notas = array();
			$notas["_id"] = $row["n_id"];
			$notas["usu_id"] = $row["n_usu"];
			$notas["assunto"] = $row["n_assunto"];
			$notas["texto"] = $row["n_texto"];
			$notas["categoria"] = $row["n_categoria"];
			$notas["data"] = $row["n_data_expira"];
			$notas["apelido"] = $row["u_apelido"];
			
			array_push($result["notas"], $notas);
			
		}
		
		$result["result"] = 1;
		
	} else
	{
		$result["result"] = 0;
		
		$result["erroMsg"] = "Erro ao recuperar notas!";
	}
	
	desconectar($link);
	
	echo json_encode($result);
	
?>