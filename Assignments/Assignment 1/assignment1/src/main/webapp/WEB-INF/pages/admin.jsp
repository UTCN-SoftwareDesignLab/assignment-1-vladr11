<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Administrator</title>
	<style type="text/css">
	@CHARSET "UTF-8";

@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);

body {
  background-color: #3e94ec;
  font-family: "Roboto", helvetica, arial, sans-serif;
  font-size: 16px;
  font-weight: 400;
  text-rendering: optimizeLegibility;
}

div.table-title {
   display: block;
  margin: auto;
  max-width: 600px;
  padding:5px;
  width: 100%;
}

.table-title h3 {
   color: #fafafa;
   font-size: 30px;
   font-weight: 400;
   font-style:normal;
   font-family: "Roboto", helvetica, arial, sans-serif;
   text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);
   text-transform:uppercase;
}


/*** Table Styles **/

.table-fill {
  background: white;
  border-radius:3px;
  border-collapse: collapse;
  height: 320px;
  margin: auto;
  max-width: 600px;
  padding:5px;
  width: 100%;
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
  animation: float 5s infinite;
}
 
th {
  color:#D5DDE5;;
  background:#1b1e24;
  border-bottom:4px solid #9ea7af;
  border-right: 1px solid #343a45;
  font-size:23px;
  font-weight: 100;
  padding:24px;
  text-align:left;
  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
  vertical-align:middle;
}

th:first-child {
  border-top-left-radius:3px;
}
 
th:last-child {
  border-top-right-radius:3px;
  border-right:none;
}
  
tr {
  border-top: 1px solid #C1C3D1;
  border-bottom-: 1px solid #C1C3D1;
  color:#666B85;
  font-size:16px;
  font-weight:normal;
  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);
}
 
tr:hover td {
  background:#4E5066;
  color:#FFFFFF;
  border-top: 1px solid #22262e;
  border-bottom: 1px solid #22262e;
}
 
tr:first-child {
  border-top:none;
}

tr:last-child {
  border-bottom:none;
}
 
tr:nth-child(odd) td {
  background:#EBEBEB;
}
 
tr:nth-child(odd):hover td {
  background:#4E5066;
}

tr:last-child td:first-child {
  border-bottom-left-radius:3px;
}
 
tr:last-child td:last-child {
  border-bottom-right-radius:3px;
}
 
td {
  background:#FFFFFF;
  padding:20px;
  text-align:left;
  vertical-align:middle;
  font-weight:300;
  font-size:18px;
  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);
  border-right: 1px solid #C1C3D1;
}

td:last-child {
  border-right: 0px;
}

th.text-left {
  text-align: left;
}

th.text-center {
  text-align: center;
}

th.text-right {
  text-align: right;
}

td.text-left {
  text-align: left;
}

td.text-center {
  text-align: center;
}

td.text-right {
  text-align: right;
}

.popup { 
	position: absolute;
	background: #606060;
	top: 50%;
	left: 50%;
	margin: -150px 0 0 -150px;
	width:300px;
	height:auto;
	visibility:hidden;
}
.login h1 { color: #fff; text-shadow: 0 0 10px rgba(0,0,0,0.3); letter-spacing:1px; text-align:center; }

input { 
	width: 90%; 
	margin-bottom: 10px; 
	background: rgba(0,0,0,0.3);
	border: none;
	outline: none;
	padding: 10px;
	font-size: 13px;
	color: #fff;
	text-shadow: 1px 1px 1px rgba(0,0,0,0.3);
	border: 1px solid rgba(0,0,0,0.3);
	border-radius: 4px;
	box-shadow: inset 0 -5px 45px rgba(100,100,100,0.2), 0 1px 1px rgba(255,255,255,0.2);
	-webkit-transition: box-shadow .5s ease;
	-moz-transition: box-shadow .5s ease;
	-o-transition: box-shadow .5s ease;
	-ms-transition: box-shadow .5s ease;
	transition: box-shadow .5s ease;
}
input:focus { box-shadow: inset 0 -5px 45px rgba(100,100,100,0.4), 0 1px 1px rgba(255,255,255,0.2); }

@import url(http://fonts.googleapis.com/css?family=Open+Sans);
.btn { display: inline-block; *display: inline; *zoom: 1; padding: 4px 10px 4px; margin-bottom: 0; font-size: 13px; line-height: 18px; color: #333333; text-align: center;text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75); vertical-align: middle; background-color: #f5f5f5; background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6); background-image: -ms-linear-gradient(top, #ffffff, #e6e6e6); background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), to(#e6e6e6)); background-image: -webkit-linear-gradient(top, #ffffff, #e6e6e6); background-image: -o-linear-gradient(top, #ffffff, #e6e6e6); background-image: linear-gradient(top, #ffffff, #e6e6e6); background-repeat: repeat-x; filter: progid:dximagetransform.microsoft.gradient(startColorstr=#ffffff, endColorstr=#e6e6e6, GradientType=0); border-color: #e6e6e6 #e6e6e6 #e6e6e6; border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25); border: 1px solid #e6e6e6; -webkit-border-radius: 4px; -moz-border-radius: 4px; border-radius: 4px; -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05); -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05); box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05); cursor: pointer; *margin-left: .3em; }
.btn:hover, .btn:active, .btn.active, .btn.disabled, .btn[disabled] { background-color: #e6e6e6; }
.btn-large { padding: 9px 14px; font-size: 15px; line-height: normal; -webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px; }
.btn:hover { color: #333333; text-decoration: none; background-color: #e6e6e6; background-position: 0 -15px; -webkit-transition: background-position 0.1s linear; -moz-transition: background-position 0.1s linear; -ms-transition: background-position 0.1s linear; -o-transition: background-position 0.1s linear; transition: background-position 0.1s linear; }
.btn-primary, .btn-primary:hover { text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25); color: #ffffff; }
.btn-primary.active { color: rgba(255, 255, 255, 0.75); }
.btn-primary { background-color: #4a77d4; background-image: -moz-linear-gradient(top, #6eb6de, #4a77d4); background-image: -ms-linear-gradient(top, #6eb6de, #4a77d4); background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#6eb6de), to(#4a77d4)); background-image: -webkit-linear-gradient(top, #6eb6de, #4a77d4); background-image: -o-linear-gradient(top, #6eb6de, #4a77d4); background-image: linear-gradient(top, #6eb6de, #4a77d4); background-repeat: repeat-x; filter: progid:dximagetransform.microsoft.gradient(startColorstr=#6eb6de, endColorstr=#4a77d4, GradientType=0);  border: 1px solid #3762bc; text-shadow: 1px 1px 1px rgba(0,0,0,0.4); box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.5); }
.btn-primary:hover, .btn-primary:active, .btn-primary.active, .btn-primary.disabled, .btn-primary[disabled] { filter: none; background-color: #4a77d4; }
.btn-block { width: 100%; display:block; }
	</style>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>

<body>
<div class="table-title">
<h3>Administrator</h3>
</div>
<table class="table-fill">
<thead>
<tr>
<th class="text-left">ID</th>
<th class="text-left">Full name</th>
<th class="text-left">Username</th>
<th class="text-left">Password</th>
<th class="text-left">Role</th>
<th class="text-left"><button onclick="showCreatePanel()">Add</button></th>
</tr>
</thead>
<tbody class="table-hover">
</tbody>
</table>

<div class="popup">
	<h1>Account</h1>
    <form method="post">
    	<input type="text" name="username" placeholder="Username" required="required" />
    	<input type="text" name="fullname" placeholder="Full name" required="required" />
        <input type="text" name="password" placeholder="Password" required="required" />
        <input type="text" name="role" placeholder="Role (number)" required="required" />
    </form>
    <button class="btn btn-primary btn-block btn-large" onclick="commit()">Done</button>
    <button class="btn btn-primary btn-block btn-large" onclick="hidePanel()">Close</button>
</div>

  <script type="text/javascript">
  $(document).ready(function() {
	  $.ajax({
	        type: "GET",
	        url: "http://localhost:8080/assignment1/admin/employees/",
	        success: function (response) {
	        	populateTable(response["results"]);
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		//alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
  });
  
  function populateTable(list) {
	  for (index in list) {
  		var account = list[index];
  		$(".table-hover").append("<tr>" +
  				"<td class=\"text-left\">" + account["id"]+ "</td>" +
  				"<td class=\"text-left\">" + account["fullname"] + "</td>" +
  				"<td class=\"text-left\">" + account["username"] + "</td>" +
  				"<td class=\"text-left\">" + account["password"] + "</td>" +
  				"<td class=\"text-left\">" + account["role"] + "</td>" +
  				"<td class=\"text-left\">" +
  				"<button onclick=\"activityLog(" + account["id"] + ")\">Activity log</button></br>" + 
  				"<button onclick=\"showEditPanel(" + account["id"] + ")\">Edit</button></br>" + 
  				"<button onclick=\"remove(" + account["id"] + ")\">Delete</button></td></tr>");
  	}
  }
  
  function clearTable() {
	  $(".table-hover").empty();
  }
  
  function reloadTable() {
	  $.ajax({
	        type: "GET",
	        url: "http://localhost:8080/assignment1/admin/employees/",
	        success: function (response) {
	        	clearTable();
	        	populateTable(response["results"]);
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		//alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
  }
  
  var mode = 0;
  function commit() {
	  var inputs = $("form").serializeArray();
	  var data = {};
	  for (index in inputs) {
		  input = inputs[index];
		  data[input["name"]] = input["value"];
	  }
	  if (mode == 1) {
		  create(data);
	  } else if (mode == 2) {
		  update(data);
	  }
	  hidePanel();
	  mode = 0;
  }
  
  function showCreatePanel() {
	  mode = 1;
	  $(".popup").css("visibility", "visible");
  }
  
  function showEditPanel(employee_id) {
	  mode = 2;
	  globalId = employee_id;
	  $(".popup").css("visibility", "visible");
  }
  
  function hidePanel() {
	  $(".popup").css("visibility", "hidden");
  }
  
  function create(data) {
	  $.ajax({
	        type: "POST",
	        url: "http://localhost:8080/assignment1/admin/employees/",
	        data: data,
	        success: function (response) {
	        	reloadTable();
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
  }
  
  var globalId = -1;
  function update(data) {
	  $.ajax({
	        type: "POST",
	        url: "http://localhost:8080/assignment1/admin/employees/" + globalId + "/",
	        data: data,
	        success: function (response) {
	        	reloadTable();
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
	  globalId = -1;
  }
  
  function remove(employeeId) {
	  $.ajax({
	        type: "DELETE",
	        url: "http://localhost:8080/assignment1/admin/employees/" + employeeId + "/",
	        success: function (response) {
	        	reloadTable();
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
  }
  
  function activityLog(employee_id) {
	  window.location.href = "http://localhost:8080/assignment1/admin/activity-log/" + employee_id + "/";
  }
  </script>
  </body>
</html>