<?php
	error_reporting(0);
	include 'config-db.php';
	if(isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])){
		$sql_select_user="Select * from ps_users where email='".$_POST['email']."'";
		$res_select_user=mysql_query($sql_select_user);
		if(!$res_select_user){
			die("Error: ".mysql_error());
		}
		else{
			if(mysql_num_rows($res_select_user)>0){
				$arr = array ('success'=>'0','error'=>"User already exists!");
				echo json_encode($arr);
			}
			else{
				$sql_insert_user="Insert into ps_users (`name`,`email`,`password`) Values ('".$_POST['name']."','".$_POST['email']."','".$_POST['password']."')";
				$res_insert_user=mysql_query($sql_insert_user);
				if(!$res_insert_user){
					die("Error: ".mysql_error());
				}
                if(sendMail($_POST['email'])){
				$arr = array ('success'=>'1','email'=>$_POST['email']);
				echo json_encode($arr);
                }
                else{
                    $arr = array ('success'=>'0','message'=>'email not sent');
                    echo json_encode($arr);
                }
			}
		}
	}
	else{
		$arr = array ('success'=>'0','error'=>"Please enter all the required fields.");
		echo json_encode($arr);
	}
    
    
    
    
    function sendMail($email){
        require "phpmailer/class.phpmailer.php"; //include phpmailer class
        // Instantiate Class
        
    
    $message="Please click on the link to verify your email address <br><a href='http://sgisandbox.com/sahaj/photo-style/services/verifyEmail.php?email=".$email."'>Click here to verify your email</a>";
        
        $mail = new PHPMailer();
        $mail->IsSMTP();                // Sets up a SMTP connection
        $mail->SMTPAuth = true;         // Connection with the SMTP does require authorization
        $mail->SMTPSecure = "tls";      // Connect using a TLS connection
        $mail->Host = "smtp.gmail.com";  //Gmail SMTP server address
        $mail->Port = 587;  //Gmail SMTP port
       
        $mail->Username   = "iamfooody@gmail.com"; // Your full Gmail address
        $mail->Password   = "dinnertime@26"; // Your Gmail password
        $mail->SetFrom("iamfooody@gmail.com", "PhotoStyle");
        $mail->AddReplyTo("iamfooody@gmail.com", "PhotoStyle");
        $mail->Subject = "PhotoStyle - Verify your email";      // Subject (which isn't required)
        $mail->AddAddress($email); // Where to send it - Recipient
        $mail->Body = $message;
        $mail->IsHTML(true);
        $result = $mail->Send();        // Send!
        $message = $result ? 'Successfully Sent!' : 'Sending Failed!';
        if($message=='Successfully Sent!'){
            return true;
        }
        else{
            return false;
        }

    }
    
    
    
    
    
    
    
    
?>				
