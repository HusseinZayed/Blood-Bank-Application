<?php

 require 'init.php';
 
 $name = $_POST["name"];
 $password = $_POST["password"];
 $city = $_POST["city"];
 $blood_group = $_POST["blood_group"];
 $number = $_POST["number"];

/*test
 $name = "test";
 $password = "test";
 $city = "test";
 $blood_group = "test";
 $number = "test";*/


 $sql = "INSERT INTO user_table (name,city,blood_groub,password,number)
 VALUES('$name','$city','$blood_group','$password','$number')";
 
 $result = mysqli_query($connection , $sql);
 
 if($result){
     echo "success";
 }
 else{
     echo "error ".mysqli_error($connection);
 }

 mysqli_close($connection);
 

?>
