<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<fmt:parseDate value="${work.workDate}" pattern="yyyy-MM-dd" var="workDay" type="date" />
<label for="${AttributeConst.WORK_DATE.getValue()}">日付</label><br/>
<input type="date" name="${AttributeConst.WORK_DATE.getValue()}" id="${AttributeConst.WORK_DATE.getValue()}" value="<fmt:formatDate value='${workDay}' pattern='yyyy-MM-dd'/>"/>
<br/><br/>

<label>氏名</label><br />
<c:out value="${sessionScope.login_employee.name}" />
<br /><br />

<label for="${AttributeConst.WORK_ATTENDANCE.getValue()}">出勤時刻</label><br />
<input type="time" name="${AttributeConst.WORK_ATTENDANCE.getValue()}" id="${AttributeConst.WORK_ATTENDANCE.getValue()}" value="${work.attendanceAt}" />
<br /><br />

<label for="${AttributeConst.WORK_LEAVING.getValue()}">退勤時刻</label><br />
<input type="time" name="${AttributeConst.WORK_LEAVING.getValue()}" id="${AttributeConst.WORK_LEAVING.getValue()}" value="${work.leavingAt}" />
<br /><br />
<input type="hidden" name="${AttributeConst.WORK_ID.getValue()}" value="${work.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">登録</button>