<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@ page import="dao.*" %>

<%
	int startMin = 0; 
	if(request.getParameter("startMin") != null)
	{
		startMin = Integer.parseInt(request.getParameter("startMin"));
	}


	int endMin = 0;
	if(request.getParameter("endMin") != null)
	{
		endMin = Integer.parseInt(request.getParameter("endMin"));
	}
	
	
	String col = "LENGTH";
	String sort = "ASC";
	
	FilmDao filmDao = new FilmDao();
	ArrayList<Film> list = filmDao.runningTime(startMin, endMin, col, sort);

%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/runningTime.jsp" method="get">
		<span>상영시간</span>
		<input type="text" name="startMin"> ~ <input type="text" name="endMin">
		<button type="submit">확인</button>
	</form>
	<table border="1" style="border-collapse:collapse">
		<tr>
			<th>
				filmID
			</th>
			<th>
				title
			</th>
			<th>
				description
			</th>
			<th>
				release_year
			</th>
			<th>
				language_id
			</th>
			<th>
				original_language_id
			</th>
			<th>
				rental_duration
			</th>
			<th>
				rental_rate
			</th>
			<th>
				length
			</th>
			<th>
				replacement_cost
			</th>
			<th>
				rating
			</th>
			<th>
				special_features
			</th>
			<th>
				last_update
			</th>
		</tr>
		<%
			for(Film f : list)
			{
		%>		
				<tr>
					<td><%=f.getFilmId() %></td>
					<td><%=f.getTitle() %></td>
					<td><%=f.getDescription() %></td>
					<td><%=f.getReleaseYear() %></td>
					<td><%=f.getLanguageId() %></td>
					<td><%=f.getOriginalLanguageId() %></td>
					<td><%=f.getRentalDuration() %></td>
					<td><%=f.getRentalRate() %></td>
					<td><%=f.getLength() %></td>
					<td><%=f.getReplacementCost() %></td>
					<td><%=f.getRating() %></td>
					<td><%=f.getSpecialFeatures() %></td>
					<td><%=f.getLastUpdate() %></td>
				</tr>	
		<%		
			}
		%>
	</table>
	
</body>
</html>