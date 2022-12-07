<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="dao.*" %>
<%@ page import="java.util.*" %>

<%
	//1) controller   요청처리+모델(model) 호출(dao)
	String col = "title";
	String sort = "asc";
	
	if(request.getParameter("col") != null)
	{
		col = request.getParameter("col");
	}
	
	if(request.getParameter("sort")!= null)
	{
		sort = request.getParameter("sort");
	}
	
	String paramSort = "asc"; //제목 클릭시 넘겨질 sort값(sort변수의 반대값)
	if(sort.equals("asc"))
	{	
		paramSort = "desc";
	}

	String searchCol = request.getParameter("searchCol");
	
	
	String searchWord =" ";
	if(request.getParameter("searchWord") != null)
	{
		searchWord = request.getParameter("searchWord");
	}
	FilmDao filmDao = new FilmDao();
	ArrayList<Film> list = filmDao.selectFilmListBySearch(searchCol,searchWord,col, sort);
	
	//2) View
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>필름 목록(검색 : 동적 쿼리)</h1>
	
	<form action="<%=request.getContextPath()%>/selectFilmListAction.jsp" method="get">
		<select name="searchCol" >
			<option value="title">title</option>
			<option value="description">description</option>
			<option value="titleAndDescription">title + description</option>
		</select>
		<input type="text" name="searchWord" value="<%=searchWord%>">
		<button type="submit">검색</button>
	</form>
	
	<table border="1" style="border-collapse:collapse">
		<tr>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=film_id&sort=<%=paramSort%>">filmID</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=title&sort=<%=paramSort%>">title</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=description&sort=<%=paramSort%>">description</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=release_year&sort=<%=paramSort%>">release_year</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=language_id&sort=<%=paramSort%>">language_id</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=original_language_id&sort=<%=paramSort%>">original_language_id</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=rental_duration&sort=<%=paramSort%>">rental_duration</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=rental_rate&sort=<%=paramSort%>">rental_rate</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=length&sort=<%=paramSort%>">length</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=replacement_cost&sort=<%=paramSort%>">replacement_cost</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=rating&sort=<%=paramSort%>">rating</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=special_features&sort=<%=paramSort%>">special_features</a>
			</th>
			<th>
				<a href="<%=request.getContextPath()%>/selectFilmListAction.jsp?col=last_update&sort=<%=paramSort%>">last_update</a>
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