<?php
	error_reporting(0);
	include 'config-db.php';
	if(isset($_POST['email']) && isset($_POST['password'])){

		$sql_select_user="Select * from ps_users where email='".$_POST['email']."' and `isVerified` = '1'";
        
		$res_select_user=mysql_query($sql_select_user);
		if(!$res_select_user){
			die("Error: ".mysql_error());
		}
		else{
			if(mysql_num_rows($res_select_user)>0){
				$row_select_user=mysql_fetch_array($res_select_user,MYSQL_ASSOC);
				if(trim($_POST['password'])==$row_select_user['password']){
					
					session_start();
					$_SESSION['userid']=$row_select_user['id'];
					$arr = array ('success'=>'1','email'=>$row_select_user['email']);
					echo json_encode($arr);
				}
				else{
					$arr = array ('success'=>'0','error'=>'Invalid email or password.');
					echo json_encode($arr);
				}	
					
					
			}
			else{
					$arr = array ('success'=>'0','error'=>'Invalid email or password.');
					echo json_encode($arr);
				}	
		}
	}
	else{
		$arr = array ('success'=>'0','error'=>"Please enter all the required fields.");
		echo json_encode($arr);
	}
?>				
