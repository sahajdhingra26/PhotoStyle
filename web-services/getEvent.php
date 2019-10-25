<?php
error_reporting(0);
include 'config-db.php';
$sql_select_user="Select * from ps_users where email='".$_POST['email']."' and `isVerified` = '1'";

$res_select_user=mysql_query($sql_select_user);
if(!$res_select_user){
    die("Error: ".mysql_error());
}
else{
    if(mysql_num_rows($res_select_user)>0){
        $row_user=mysql_fetch_array($res_select_user,MYSQL_ASSOC);
        $sql_select_images="Select * from ps_events where userId='".$row_user['id']."'";
        $res_select_images=mysql_query($sql_select_images);
        if(!$res_select_images){
            die("Error: ".mysql_error());
        }
       $array_image = array();
        
       
        
        while( $row_image=mysql_fetch_array($res_select_images,MYSQL_ASSOC)){
            
            $array_image[]=$row_image['event']."\n".$row_image['date'];
        }
        $arr = array ('success'=>'1','src'=>$array_image);
        echo json_encode($arr);
    }
}
    

?>

