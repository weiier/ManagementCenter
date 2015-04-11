<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="js/jquery-1.10.2.js"></script>
<script src="js/json2.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
			var origin = $("form").find(".origin").length;
			var des = $("form").find(".destination").length;
			if(origin>des){
				var str= new Array();
				for(var i=0;i<origin;i++){
					str.push({'name':$("#"+i).val(),'des':$("#d2").val()});
				}
				var jsonStr =JSON.stringify(str);
				alert(jsonStr);
				$.post("test/testJson?jsonStr="+jsonStr);
			}
		});
		
	});
</script>
  </head>
  
  <body>
  <form method="post" action="test/orders">
 起点仓库： <input type="text" name="origin"/>
   终点：<input type="text" name="destination"/>
    名称：<input type="text" name="itemName"/>
     数量：<input type="text" name="itemQuantity"/>
 <input type="submit" value="submit" >
 
 
 
 <input type="button" id="btn" value="test">
 <input type="text" class="origin" id="0">
  <input type="text" class="origin" id="1">
  <input type="text" class="origin" id="2">
  <input type="text" class="origin" id="3">
    <input type="text" class="destination" id="d2">
    
    <select id="select2" class="half" multiple="multiple" size="6" name="select2">
											<optgroup label="Set 1">
												<option value="1" selected>Lorem</option>
												<option value="2">Ipsum</option>
											</optgroup>
											<optgroup label="Set 2">
												<option value="3">Dolor</option>
												<option value="4">Sit</option>
												<option value="5">Amet</option>
											</optgroup>
	</select>
  </form>
   
  </body>
</html>
