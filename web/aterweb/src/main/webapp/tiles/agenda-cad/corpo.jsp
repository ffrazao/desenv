<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script src="<spring:url value="resources/js/agenda-cad.js"/>" charset="UTF-8"></script>

<div id="corpo" class="container" style="background-color: #FFF;">
<div ui-calendar="uiConfig.calendar" data-ng-model="eventSources" calendar="myCalendar">
</div>