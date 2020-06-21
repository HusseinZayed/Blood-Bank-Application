<?php

 require 'init.php';
 
 $password = $_POST["password"];
 $number = $_POST["number"];




 $sql = "SELECT * FROM   user_table WHERE number = '$number' AND password = '$password'" ;

 $result = mysqli_query($connection , $sql);
 
 if(mysqli_num_rows($result)>0){
     $rows = mysqli_fetch_assoc($result);
     echo $rows["city"];
 }
 else{
     echo "incorrect data";
 }

 mysqli_close($connection);
 

?>
