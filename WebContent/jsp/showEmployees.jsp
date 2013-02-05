<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/mytags.tld" prefix="libr"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>JPA Task</title>
<link href="/Task8/css/style.css" rel="stylesheet">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
	<div class="header">JPA Task</div>

	<libr:employee-nav />

	<table border=1 class="employee-table">
		<tr>
			<th>#</th>
			<th>Employee Name</th>
			<th>Employee Address</th>
			<th>Work</th>
		</tr>
		<c:forEach items="${employeeList }" var="employee"
			varStatus="statusEmployee">
			<tr>
				 <td>${statusEmployee.count }</td>
				<td>${employee.firstName } ${employee.lastName }</td>
				<td>${employee.address.city.country.name },
					${employee.address.city.name }, ${employee.address.street} street,
					${employee.address.building } <c:if
						test="${employee.address.apartment != 0}">
								,${employee.address.apartment} ap.
								</c:if>
				</td>
				<td><c:forEach items="${employee.works }" var="work"
						varStatus="statusWork">
						<p>
							Company: ${work.office.company.name }<br> Company Address:
							${work.office.address.city.country.name },
							${work.office.address.city.name }, ${work.office.address.street}
							street, ${work.office.address.building }
							<c:if test="${work.office.address.apartment != 0}">
										, ${work.office.address.apartment} ap.
										</c:if>
							<br> Number of employees: ${work.office.countOfEmployees }<br>
							Position: ${work.position.name }
						</p>

					</c:forEach></td>
			</tr> 
		</c:forEach>
	</table>
	
	<libr:employee-nav />
	
	<div class="footer">Copyright &copy; EPAM 2013. All rights
		reserved.</div>
</body>
</html>