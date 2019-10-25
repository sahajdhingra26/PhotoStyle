<?php
	error_reporting(0);
	include 'config-db.php';
	if(isset($_GET['email'])) {
        $sql_select_user="Select * from ps_users where email='".$_GET['email']."'";
		$res_select_user=mysql_query($sql_select_user);
		if(!$res_select_user){
			die("Error: ".mysql_error());
		}
		else{
			if(mysql_num_rows($res_select_user)>0){
                $sql_update_user="Update ps_users SET `isVerified` = 1 where `email` = '".$_GET['email']."'";
				$res_update_user=mysql_query($sql_update_user);
				if(!$res_update_user){
					die("Error: ".mysql_error());
				}
                echo "User Verified Successfully";
            }
            else{
                echo "Email Id not registered";
            }
        }
    }
    else{
        echo "Email Id not registered";
    }
?>				
