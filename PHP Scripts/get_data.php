<?php

require "init.php";

$city = $_POST['city'];
$sql = "SELECT * FROM posts WHERE number in(SELECT number FROM user_table WHERE city = '$city')";
$result = mysqli_query($connection , $sql);
$response = array();

while($row = mysqli_fetch_assoc($result)){
    array_push($response,$row);
}

echo json_encode($response);


?>