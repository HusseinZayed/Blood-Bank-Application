<?php
   
   $host = "localhost";
   $username = "id11797206_admin";
   $password = "19191919@asdA";
   $DBname = "id11797206_blood_bank";

   $connection = mysqli_connect($host,$username,$password,$DBname);
   
   if ( $connection ) {
     // echo "successful to connect to MySQL: " ;
   }
   else{
       //echo "Failed to connect to MySQL: " . mysqli_connect_error();
   }
?>