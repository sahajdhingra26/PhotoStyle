
<?php
    error_reporting(0);
    include 'config-db.php';
    if(isset($_POST['email']) && isset($_POST['event'])){
        $sql_select_user="Select * from ps_users where email='".$_POST['email']."' and `isVerified` = '1'";
        
        $res_select_user=mysql_query($sql_select_user);
        if(!$res_select_user){
            die("Error: ".mysql_error());
        }
        else{
        if(mysql_num_rows($res_select_user)>0){
        $row_user=mysql_fetch_array($res_select_user,MYSQL_ASSOC);
            
            $sql_insert_image="INSERT INTO ps_events(`userId`,`event`,`date`) VALUES('".$row_user['id']."','".$_POST['event']."','".$_POST['date']."')";
            $res_insert_image=mysql_query($sql_insert_image);
            if(!$res_insert_image){
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

