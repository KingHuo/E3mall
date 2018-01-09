

<html>
<head>
<title>${hello}</title>
</head>
<body>
<table border="1">
   <tr> 
     <th>序号</th>
     <th>姓名</th>
     <th>年龄</th>
     <th>住址</th>
   </tr>
   <#list  stuList as stu>
   <#if stu_index%2==0>
	<tr bgcolor="red">
	<#else>
	<tr bgcolor="blue">
	</#if>
		<td>${stu_index}</td>
		<td>${stu.name}</td>
		<td>${stu.age}</td>
		<td>${stu.address}</td>
	</tr>
	</#list>
</table>
	当前日期: ${date?date}<br>
	当前时间: ${date?time}<br>
	当前日期和时间：  ${date?datetime}<br>
	自定义： ${date?string("yyyy-MM-dd HH:mm:ss")}<br>
	null值处理: ${student.val!}<br>
	null值处理: ${student.val!"woshiNUll"}<br>
	空值做判断:
    <#if val??><br>
     	我是if
    <#else>
	        我是else
   </#if>
</body>
</html>


