<?php 

define(HOST,'');
define(USER,'');
define(PASS,'');
define(DB,'');


/*define(HOST,'localhost');
define(USER,'root');
define(PASS,'');
define(DB,'sgisandb_searchurphone');
*/


$con=mysql_connect(HOST,USER,PASS) or die("can not connect database");
mysql_select_db(DB,$con) or die("can not select database");
?>
