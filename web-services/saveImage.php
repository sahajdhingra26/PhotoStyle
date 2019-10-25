
<?php
    error_reporting(0);
    include 'config-db.php';
    if(isset($_POST['email']) && isset($_POST['src'])){
        $sql_select_user="Select * from ps_users where email='".$_POST['email']."' and `isVerified` = '1'";
        
        $res_select_user=mysql_query($sql_select_user);
        if(!$res_select_user){
            die("Error: ".mysql_error());
        }
        else{
        if(mysql_num_rows($res_select_user)>0){
        $row_user=mysql_fetch_array($res_select_user,MYSQL_ASSOC);
            
            $sql_insert_image="INSERT INTO ps_images(`userId`,`src`) VALUES('".$row_user['id']."','".$_POST['src']."')";
            $res_insert_image=mysql_query($sql_insert_image);
            if(!$res_insert_image){
                die("Error: ".mysql_error());
            }
            $imgId=mysql_insert_id();
            
            $sql_insert_label="INSERT INTO ps_labels(`userId`,`imgId`,`label`) VALUES('".$row_user['id']."','".$imgId."','".$_POST['image_label']."')";
            $res_insert_label=mysql_query($sql_insert_label);
            if(!$res_insert_label){
                die("Error: ".mysql_error());
            }
            
            $sql_insert_label="INSERT INTO ps_seasons(`userId`,`imgId`,`season`) VALUES('".$row_user['id']."','".$imgId."','".$_POST['season']."')";
            $res_insert_label=mysql_query($sql_insert_label);
            if(!$res_insert_label){
                die("Error: ".mysql_error());
            }
            
            $sql_insert_label="INSERT INTO ps_types(`userId`,`imgId`,`type`) VALUES('".$row_user['id']."','".$imgId."','".$_POST['type']."')";
            $res_insert_label=mysql_query($sql_insert_label);
            if(!$res_insert_label){
                die("Error: ".mysql_error());
            }
            
            $sql_insert_label="INSERT INTO ps_category(`userId`,`imgId`,`category`) VALUES('".$row_user['id']."','".$imgId."','".$_POST['category']."')";
            $res_insert_label=mysql_query($sql_insert_label);
            if(!$res_insert_label){
                die("Error: ".mysql_error());
            }
            $arr = array ('success'=>'1');
            echo json_encode($arr);

        }
        }
    
    }
    else{
        $arr = array ('success'=>'0','error'=>"Please enter all the required fields.");
        echo json_encode($arr);
    }
?>
