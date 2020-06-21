<?php
require "init.php";
$target_file_name =  basename($_FILES["file"]["name"]);
$response = array();

if (isset($_FILES["file"]))
{
    if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file_name))
    {
        $url = "https://companycar123.000webhostapp.com/".$target_file_name;
        $msg = $_GET["message"];
        $number = $_GET["number"];
        $sql = "INSERT INTO posts (id,message,url,number) VALUES (NULL , '$msg' , '$url' , '$number');";
        mysqli_query($connection,$sql);
        $success = true;
        $message = "Uploaded!!!";
    }
    else
    {
        $success = false;
        $message = "NOT Uploaded!!! _ Error While Uploading";
    }
}
else
{
    $success = false;
    $message = "missing field";
}
$response["success"] = $success;
$response["message"] = $message;
echo json_encode($response);
?>