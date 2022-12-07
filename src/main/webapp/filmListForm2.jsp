<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ page import="dao.*" %>
<%@ page import="java.util.*" %>
<%
	FilmDao filmDao = new FilmDao();

	int minYear = filmDao.selectMinReleaseYear();//2006
	System.out.println(minYear);
	Calendar today = Calendar.getInstance();//캘린더 클래스는 추상클래스, 추상클래스는 생성자 호출 못함
	int todayYear = today.get(Calendar.YEAR);//오늘날짜의 연도
	System.out.println(todayYear);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>영화 검색</h1>
	<form action="<%=request.getContextPath()%>/filmListAction2.jsp" method="post">
		<table>
			<tr>
				<td>출시연도</td>
				<td>
					<select name="releaseYear">
						<option value="">출시연도를 선택하세요</option>
						<%
							for(int i=minYear; i<=todayYear; i++)
							{
						%>
								<option value="<%=i%>"><%=i%>년</option>
						<%
							}
						%>
					</select>
				</td>
			</tr>
			<tr>
				<td>상영시간(분)</td>
				<td>
					<input type="number" name="fromMinute">
					~
					<input type="number" name="toMinute">
				</td>
			</tr>
			<tr>
				<td>등급</td>
				<td>
					<input type="checkbox" name="rating" value="G">G
					<input type="checkbox" name="rating" value="PG">PG
					<input type="checkbox" name="rating" value="PG-13">PG-13
					<input type="checkbox" name="rating" value="R">R
					<input type="checkbox" name="rating" value="NC-17">NC-17
				</td>
			</tr>
			
			<tr>
				<td>영화 제목 검색</td>
				<td>
					<input type="text" name="searchTitle">
				</td>
			</tr>
		</table>
	
		<button type="submit">검색</button>
	</form>
	
</body>
</html>