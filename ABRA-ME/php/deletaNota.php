 <?php
 
 	include_once 'functions.php';
 
  $id = $_POST["id"];
  
  $link = conectar();
  $sql = "DELETE FROM nota WHERE _id = $id;";
	
	mysqli_query($link, $sql);

  	desconectar($link);	
 ?> 