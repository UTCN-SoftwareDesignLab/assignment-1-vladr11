<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Employee</title>
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
  max-width: 700px;
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

.switch_btn {
	display: block;
  margin: auto;
  max-width: 600px;
  padding:5px;
  width: 100%;
  margin-bottom: 15px;
}

.text_area {
	display: block;
  margin: auto;
  max-width: 600px;
  padding:5px;
  margin-bottom: 15px;
  visibility: hidden;
}
.filter_btn {
	display: block;
  margin: auto;
  max-width: 600px;
  padding:5px;
  margin-bottom: 15px;
  visibility: hidden;
}
	</style>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>

<body>
<div class="table-title">
<h3>Employee</h3>
</div>
<button class="btn btn-primary btn-block btn-large switch_btn" onclick="displayClients()">Clients</button>
<button class="btn btn-primary btn-block btn-large switch_btn" onclick="displayAccounts()">Accounts</button>
<button class="btn btn-primary btn-block btn-large switch_btn" onclick="displayUtilityBills()">Utility bills</button>
<textarea class="text_area" rows="1" cols="10" placeholder="Client ID"></textarea>
<button class="btn btn-primary btn-block btn-large filter_btn" onclick="filter()">Filter</button>
<table class="table-fill">
<thead>
<tr>
<th class="text-left">ID</th>
<th class="text-left">First name</th>
<th class="text-left">Last name</th>
<th class="text-left">ID number</th>
<th class="text-left">CNP</th>
<th class="text-left">Email</th>
<th class="text-left">Address</th>
<th class="text-left"><button onclick="showCreatePanel()">Add</button></th>
</tr>
</thead>
<tbody class="table-hover">
</tbody>
</table>

<div class="popup">
	<h1>Client Account</h1>
    <form method="post">
    	<input type="text" name="firstname" placeholder="First name" required="required" />
    	<input type="text" name="lastname" placeholder="Last name" required="required" />
        <input type="text" name="identity_card_number" placeholder="ID number" required="required" />
        <input type="text" name="cnp" placeholder="CNP (number)" required="required" />
        <input type="text" name="email" placeholder="Email" required="required" />
        <input type="text" name="address" placeholder="Address" required="required" />
    </form>
    <button class="btn btn-primary btn-block btn-large" onclick="commitClient()">Done</button>
    <button class="btn btn-primary btn-block btn-large" onclick="hidePanel()">Close</button>
</div>

  <script type="text/javascript">
  $(document).ready(function() {
	  fetchClients();
  });
  
  var mode = 0;
  
  function fetchClients() {
	  $.ajax({
	        type: "GET",
	        url: "http://localhost:8080/assignment1/employee/clients/",
	        success: function (response) {
	        	populateClientsTable(response["results"]);
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr);
	        }
	      });
  }
  
  function fetchAccounts() {
	  $.ajax({
	        type: "GET",
	        url: "http://localhost:8080/assignment1/employee/accounts/",
	        success: function (response) {
	        	populateAccountsTable(response["results"]);
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr);
	        }
	      });
  }
  
  function fetchAccountsForClient(client_id) {
	  $.ajax({
	        type: "GET",
	        url: "http://localhost:8080/assignment1/employee/clients/" + client_id + "/accounts/",
	        success: function (response) {
	        	populateAccountsTable(response["results"]);
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr);
	        }
	      });
  }
  
  function fetchUtilityBills() {
	  $.ajax({
	        type: "GET",
	        url: "http://localhost:8080/assignment1/employee/utility_bills/",
	        success: function (response) {
	        	populateBillsTable(response["results"]);
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr);
	        }
	      });
  }

  function populateClientsTable(list) {
	  for (index in list) {
  		var account = list[index];
  		$(".table-hover").append("<tr>" +
  				"<td class=\"text-left\">" + account["id"] + "</td>" +
  				"<td class=\"text-left\">" + account["firstname"] + "</td>" +
  				"<td class=\"text-left\">" + account["lastname"] + "</td>" +
  				"<td class=\"text-left\">" + account["identity_card_number"] + "</td>" +
  				"<td class=\"text-left\">" + account["cnp"] + "</td>" +
  				"<td class=\"text-left\">" + account["email"] + "</td>" +
  				"<td class=\"text-left\">" + account["address"] + "</td>" +
  				"<td class=\"text-left\">" + 
  				"<button onclick=\"showEditPanel(" + account["id"] + ")\">Edit</button></br>" + 
  				"</td></tr>");
  	}
  }
  
  function populateAccountsTable(list) {
	  for (index in list) {
	  		var account = list[index];
	  		$(".table-hover").append("<tr>" +
	  				"<td class=\"text-left\">" + account["id"] + "</td>" +
	  				"<td class=\"text-left\">" + account["balance"] + "</td>" +
	  				"<td class=\"text-left\">" + account["creation_date"] + "</td>" +
	  				"<td class=\"text-left\">" + account["type"] + "</td>" +
	  				"<td class=\"text-left\">" + account["owner"] + "</td>" +
	  				"<td class=\"text-left\">" +
	  				"<button onclick=\"showUtilityPanel(" + account["id"] + ")\">Pay utilities</button></br>" + 
	  				"<button onclick=\"showTransferPanel(" + account["owner"] + "," + account["id"] + ")\">Transfer</button></br>" + 
	  				"<button onclick=\"showEditPanel(" + account["id"] + ")\">Edit</button></br>" + 
	  				"<button onclick=\"removeAccount(" + account["owner"] + "," + account["id"] + ")\">Delete</button></td></tr>");
	  	}
  }
  
  function populateBillsTable(list) {
	  for (index in list) {
	  		var bill = list[index];
	  		$(".table-hover").append("<tr>" +
	  				"<td class=\"text-left\">" + bill["id"] + "</td>" +
	  				"<td class=\"text-left\">" + bill["amount_to_pay"] + "</td>" +
	  				"<td class=\"text-left\">" + bill["firstname"] + "</td>" +
	  				"<td class=\"text-left\">" + bill["lastname"] + "</td>" +
	  				"<td class=\"text-left\">" + bill["cnp"] + "</td>" +
	  				"<td class=\"text-left\">" + bill["address"] + "</td>" +
	  				"<td class=\"text-left\">" + bill["paid"] + "</td>" +
	  				"<td class=\"text-left\">" +
	  				"<button onclick=\"showBillsPayPanel(" + bill["id"] + ")\">Pay utilities</button></br>" +
	  				"<button onclick=\"removeBill(" + bill["id"] + ")\">Delete</button></td></tr>");
	  	}
  }
  
  function createClientsTable() {
	  $(".table-fill").html("<thead> <tr> \
			  <th class=\"text-left\">ID</th> \
			  <th class=\"text-left\">First name</th> \
			  <th class=\"text-left\">Last name</th> \
			  <th class=\"text-left\">ID number</th> \
			  <th class=\"text-left\">CNP</th> \
			  <th class=\"text-left\">Email</th> \
			  <th class=\"text-left\">Address</th> \
			  <th class=\"text-left\"><button onclick=\"showClientsCreatePanel()\">Add</button></th> \
			  </tr> \
			  </thead> \
			  <tbody class=\"table-hover\"> \
			  </tbody>");
  }
  
  function createAccountsTable() {
	  $(".table-fill").html("<thead> <tr> \
			  <th class=\"text-left\">ID</th> \
			  <th class=\"text-left\">Balance</th> \
			  <th class=\"text-left\">Creation date</th> \
			  <th class=\"text-left\">Type</th> \
			  <th class=\"text-left\">Owner</th> \
			  <th class=\"text-left\"><button onclick=\"showAccountsCreatePanel()\">Add</button></th> \
			  </tr> \
			  </thead> \
			  <tbody class=\"table-hover\"> \
			  </tbody>");
  }
  
  function createBillsTable() {
	  $(".table-fill").html("<thead> <tr> \
			  <th class=\"text-left\">ID</th> \
			  <th class=\"text-left\">Amount to pay</th> \
			  <th class=\"text-left\">First name</th> \
			  <th class=\"text-left\">Last name</th> \
			  <th class=\"text-left\">CNP</th> \
			  <th class=\"text-left\">Address</th> \
			  <th class=\"text-left\">Paid</th> \
			  <th class=\"text-left\"><button onclick=\"showBillsCreatePanel()\">Add</button></th> \
			  </tr> \
			  </thead> \
			  <tbody class=\"table-hover\"> \
			  </tbody>");
  }
  
  function clearTableContent() {
	  $(".table-hover").empty();
  }
  
  function clearTable() {
	  $(".table-fill").empty();
  }
  
  function createClientsPopup() {
	  $(".popup").html("<h1>Client Account</h1> \
			    <form method=\"post\"> \
		    	<input type=\"text\" name=\"firstname\" placeholder=\"First name\" required=\"required\" /> \
		    	<input type=\"text\" name=\"lastname\" placeholder=\"Last name\" required=\"required\" /> \
		        <input type=\"text\" name=\"identity_card_number\" placeholder=\"ID number\" \
		        required=\"required\" /> \
		        <input type=\"text\" name=\"cnp\" placeholder=\"CNP (number)\" required=\"required\" /> \
		        <input type=\"text\" name=\"email\" placeholder=\"Email\" required=\"required\" /> \
		        <input type=\"text\" name=\"address\" placeholder=\"Address\" required=\"required\" /> \
		    </form> \
		    <button class=\"btn btn-primary btn-block btn-large\" onclick=\"commitClient()\">Done</button>\
		    <button class=\"btn btn-primary btn-block btn-large\" onclick=\"hidePanel()\">Close</button>");
  }
  
  function createAccountsPopup() {
	  $(".popup").html("<h1>Bank Account</h1> \
			    <form method=\"post\"> \
		    	<input type=\"text\" name=\"balance\" placeholder=\"Balance\" required=\"required\" /> \
		    	<input type=\"text\" name=\"creation_date\" placeholder=\"Creation date\" required=\"required\" /> \
		        <input type=\"text\" name=\"type\" placeholder=\"Type (number)\" /> \
		        <input type=\"text\" name=\"client_id\" placeholder=\"Client ID\" /> \
		    </form> \
		    <button class=\"btn btn-primary btn-block btn-large\" onclick=\"commitAccount()\">Done</button>\
		    <button class=\"btn btn-primary btn-block btn-large\" onclick=\"hidePanel()\">Close</button>");
  }
  
  function createBillsPopup() {
	  $(".popup").html("<h1>Utility bill</h1> \
			    <form method=\"post\"> \
		    	<input type=\"text\" name=\"amount_to_pay\" placeholder=\"Amount to pay\" required=\"required\" /> \
		    	<input type=\"text\" name=\"firstname\" placeholder=\"First name\" required=\"required\" /> \
		        <input type=\"text\" name=\"lastname\" placeholder=\"Last name\" /> \
		        <input type=\"text\" name=\"cnp\" placeholder=\"CNP\" /> \
		        <input type=\"text\" name=\"address\" placeholder=\"Address\" /> \
		    </form> \
		    <button class=\"btn btn-primary btn-block btn-large\" onclick=\"commitBill()\">Done</button>\
		    <button class=\"btn btn-primary btn-block btn-large\" onclick=\"hidePanel()\">Close</button>");
  }
  
  function createBillsPayPopup() {
	  $(".popup").html("<h1>Utility bill</h1> \
			    <form method=\"post\"> \
		    	<input type=\"text\" name=\"account_id\" placeholder=\"Account ID\" required=\"required\" /> \
		    </form> \
		    <button class=\"btn btn-primary btn-block btn-large\" onclick=\"processBill(" + globalId + ")\">Done</button>\
		    <button class=\"btn btn-primary btn-block btn-large\" onclick=\"hidePanel()\">Close</button>");
  }
  
  function createTransferPopup() {
	  $(".popup").html("<h1>Utility bill</h1> \
			    <form method=\"post\"> \
		    	<input type=\"text\" name=\"sum\" placeholder=\"Sum\" required=\"required\" /> \
		    	<input type=\"text\" name=\"target_account\" placeholder=\"Target account (id)\" required=\"required\" /> \
		    </form> \
		    <button class=\"btn btn-primary btn-block btn-large\" onclick=\"commitTransfer()\">Done</button>\
		    <button class=\"btn btn-primary btn-block btn-large\" onclick=\"hidePanel()\">Close</button>");
  }
  
  function displayClients() {
	  clearTable();
	  createClientsTable();
	  fetchClients();
	  mode = 0;
	  $("textarea").css("visibility", "hidden");
	  $(".filter_btn").css("visibility", "hidden");
  }
  
  function displayAccounts() {
	  clearTable();
	  createAccountsTable();
	  fetchAccounts();
	  mode = 1;
	  $("textarea").css("visibility", "visible");
	  $(".filter_btn").css("visibility", "visible");
  }
  
  function displayUtilityBills() {
	  clearTable();
	  createBillsTable();
	  fetchUtilityBills();
	  mode = 3;
	  $("textarea").css("visibility", "hidden");
	  $(".filter_btn").css("visibility", "hidden");
  }
  
  function filter() {
	  var filterId = $("textarea").val();
	  console.log(filterId);
	  if (filterId > 0) {
	  	clearTableContent();
	  	fetchAccountsForClient(filterId);
	  } else {
		  clearTableContent();
		  fetchAccounts();
	  }
  }
  
  var actionType = 0; // 1 for create; 2 for update
  var globalId = -1;
  function showClientCreatePanel() {
	  actionType = 1;
	  createClientsPopup();
	  $(".popup").css("visibility", "visible");
  }
  
  function showClientEditPanel(object_id) {
	  actionType = 2;
	  globalId = object_id;
	  createClientsPopup();
	  $(".popup").css("visibility", "visible");
  }
  
  function showAccountCreatePanel() {
	  actionType = 1;
	  createAccountsPopup();
	  $(".popup").css("visibility", "visible");
  }
  
  function showAccountEditPanel(object_id) {
	  actionType = 2;
	  globalId = object_id;
	  createAccountsPopup();
	  $(".popup").css("visibility", "visible");
  }
  
  function showBillsCreatePanel() {
	  actionType = 1;
	  createBillsPopup();
	  $(".popup").css("visibility", "visible");
  }
  
  function showBillsPayPanel(bill_id) {
	  actionType = 1;
	  globalId = bill_id;
	  createBillsPayPopup();
	  $(".popup").css("visibility", "visible");
  }
  
  var globalId2 = -1;
  function showTransferPanel(client_id, account_id) {
	  actionType = 3;
	  createTransferPopup();
	  globalId = account_id;
	  globalId2 = client_id;
	  $(".popup").css("visibility", "visible");
  }
  
  function hidePanel() {
	  $(".popup").css("visibility", "hidden");
  }
  
  //
  
  function commitClient() {
	  var inputs = $("form").serializeArray();
	  var data = {};
	  for (index in inputs) {
		  input = inputs[index];
		  data[input["name"]] = input["value"];
	  }
	  if (actionType == 1) {
		  createClient(data);
	  } else if (actionType == 2) {
		  updateClient(data);
	  }
	  hidePanel();
	  mode = 0;
  }
  
  function createClient(data) {
	  $.ajax({
	        type: "POST",
	        url: "http://localhost:8080/assignment1/employee/clients/",
	        data: data,
	        success: function (response) {
	        	clearTableContent();
	        	fetchClients();
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
  }
  
  function updateClient(data) {
	  $.ajax({
	        type: "POST",
	        url: "http://localhost:8080/assignment1/employee/clients/" + globalId + "/",
	        data: data,
	        success: function (response) {
	        	clearTableContent();
	        	fetchClients();
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
	  globalId = -1;
  }
  
  //
  
  function commitAccount() {
	  var inputs = $("form").serializeArray();
	  var data = {};
	  for (index in inputs) {
		  input = inputs[index];
		  data[input["name"]] = input["value"];
	  }
	  var clientId = data["client_id"];
	  data["client_id"] = null;
	  if (actionType == 1) {
		  createAccount(data, clientId);
	  } else if (actionType == 2) {
		  updateAccount(data, clientId);
	  }
	  hidePanel();
	  mode = 0;
  }
  
  function createAccount(data, client_id) {
	  $.ajax({
	        type: "POST",
	        url: "http://localhost:8080/assignment1/employee/clients/" + client_id + "/accounts/",
	        data: data,
	        success: function (response) {
	        	clearTableContent();
	        	fetchAccounts();
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
  }
  
  function updateAccount(data, client_id) {
	  $.ajax({
	        type: "POST",
	        url: "http://localhost:8080/assignment1/employee/clients/" + client_id + "/accounts/" + globalId + "/",
	        data: data,
	        success: function (response) {
	        	clearTableContent();
	        	fetchAccounts();
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
	  globalId = -1;
  }
  
  function removeAccount(clientId, accountId) {
	  $.ajax({
	        type: "DELETE",
	        url: "http://localhost:8080/assignment1/employee/clients/" + clientId + "/accounts/" + accountId + "/",
	        success: function (response) {
	        	clearTableContent();
	        	fetchAccounts();
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
  }
  
  //
  
  function commitTransfer() {
	  var inputs = $("form").serializeArray();
	  var data = {};
	  for (index in inputs) {
		  input = inputs[index];
		  data[input["name"]] = input["value"];
	  }
	  $.ajax({
	        type: "POST",
	        url: "http://localhost:8080/assignment1/employee/clients/" + globalId2 + "/accounts/" + globalId + "/transfer/",
	        data: data,
	        success: function (response) {
	        	alert("Successful transaction");
	        	hidePanel();
	        	filter();
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
  }
  
  //
  
  function commitBill() {
	  var inputs = $("form").serializeArray();
	  var data = {};
	  for (index in inputs) {
		  input = inputs[index];
		  data[input["name"]] = input["value"];
	  }
	  $.ajax({
	        type: "POST",
	        url: "http://localhost:8080/assignment1/employee/utility_bills/",
	        data: data,
	        success: function (response) {
	        	clearTableContent();
	        	fetchUtilityBills();
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
	  hidePanel();
  }
  
  function processBill(bill_id) {
	  var inputs = $("form").serializeArray();
	  var data = {};
	  for (index in inputs) {
		  input = inputs[index];
		  data[input["name"]] = input["value"];
	  }
	  $.ajax({
	        type: "POST",
	        url: "http://localhost:8080/assignment1/employee/utility_bills/" + bill_id + "/",
	        data: data,
	        success: function (response) {
	        	alert("Utility bill processed successfully");
	        	hidePanel();
	        	clearTableContent();
	        	fetchUtilityBills();
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
  }
  
  function removeBill(bill_id) {
	  $.ajax({
	        type: "DELETE",
	        url: "http://localhost:8080/assignment1/employee/utility_bills/" + bill_id + "/",
	        success: function (response) {
	        	clearTableContent();
	        	fetchUtilityBills();
	        },
	        error: function (xhr, ajaxOptions, thrownError) {
	            var json = xhr.responseJSON;
	       		alert(json["error"]);
	       		console.log(xhr)
	        }
	      });
  }
  </script>
  </body>
</html>