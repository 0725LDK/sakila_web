<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.*" %>
<%@ page import="vo.*" %>
<%@ page import="java.util.*" %>

<%
	request.setCharacterEncoding("utf-8");

	CustomerDao customerDao = new CustomerDao();
	int count = customerDao.customerCount(); //총 행 수
	//System.out.println(count);
	
	//페이징 변수
	int firstPage = 1;
	int currentPage = 1;
	if(request.getParameter("currentPage") != null)
	{
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int rowPerPage = 10;
	int beginRow = (currentPage - 1)*rowPerPage;
	int lastPage = count/rowPerPage;
	if(count%rowPerPage != 0)
	{
		lastPage = lastPage + 1;
	}
	
	ArrayList<HashMap<String,Object>> customerMapList = customerDao.selectCustomerMapLis(beginRow, rowPerPage);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>customerMapList</title>
	<style>
	  table {
	    width: 80%;
	    border: 1px solid #444444;
	    border-collapse: collapse;
	    text-align:center;
	  }
	  th, td {
	    border: 1px solid #444444;
	    text-align:center;
	  }
	</style>
</head>
<body>
	<!-- Map 타입 사용 -->
	<h1>HashMap type List</h1>
	
	<table>
		<tr>
			<th>성</th>
			<th>이름</th>
			<th>상세주소</th>
			<th>지역주소</th>
			<th>도시</th>
			<th>나라</th>
		</tr>
			<%
				for(HashMap<String,Object> list : customerMapList)
				{
			%>
					<tr>
						<td><%=list.get("lastName")%></td>
						<td><%=list.get("firstName")%></td>
						<td><%=list.get("address")%></td>
						<td><%=list.get("district")%></td>
						<td><%=list.get("city")%></td>
						<td><%=list.get("country")%></td>
					</tr>
			<%
				}
			
			%>
		<tr>
			<td colspan="6">
				<%
					if(currentPage==1)
					{
				%>
						<span>처음으로</span>
						<span>이전</span>
				<%
					}
					else if( currentPage > 1)
					{
				%>
						<a href="<%=request.getContextPath()%>/customerMapList.jsp?currentPage=<%=firstPage%>">처음으로</a>
						<a href="<%=request.getContextPath()%>/customerMapList.jsp?currentPage=<%=currentPage - 1%>">이전</a>
				<%
					}
				%>
				
				<span>[ <%=currentPage %> ]</span>
				
				<%
					if(currentPage == lastPage)
					{
				%>
						<span>다음</span>
						<span>마지막으로</span>
				<%		
					}
					else if(currentPage < lastPage)
					{
				%>		
						<a href="<%=request.getContextPath()%>/customerMapList.jsp?currentPage=<%=currentPage + 1%>">다음</a>
						<a href="<%=request.getContextPath()%>/customerMapList.jsp?currentPage=<%=lastPage%>">마지막으로</a>
				<%		
					}
				%>
			</td>
		</tr>
	</table>
</body>
</html>