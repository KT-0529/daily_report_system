<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actWork" value="${ForwardConst.ACT_WORK.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>${sessionScope.login_employee.name}さんの出退勤記録</h2>
        <table id="work_list">
            <tbody>
                <tr>
                    <th class="work_date">日付</th>
                    <th class="work_attendance">出勤時刻</th>
                    <th class="work_leaving">退勤時刻</th>
                    <th class="work_action">&nbsp;</th>
                </tr>
                <c:forEach var="work" items="${works}" varStatus="status">
                    <fmt:parseDate value="${work.workDate}" pattern="yyyy-MM-dd" var="workDay" type="date"/>

                    <tr class="row${status.count % 2}">
                        <td class="work_attendance"><fmt:formatDate value='${workDay}' pattern='yyyy-MM-dd'/></td>
                        <td class="work_attendance">${work.attendanceAt}</td>
                        <td class="work_leaving">${work.leavingAt}</td>
                        <td class="work_action">
                                    <a href="<c:url value='?action=${actWork}&command=${commEdt}&id=${work.id}'/>">更新</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${works_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((works_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actWork}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actWork}&command=${commNew}' />">出退勤報告</a></p>

    </c:param>
</c:import>