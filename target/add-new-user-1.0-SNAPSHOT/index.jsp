<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<%@ page language="Java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTDHTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String ctxPath = request.getContextPath();
%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>jQuery表格数据增删改插件 Tabullet.js</title>
    <link rel="stylesheet" type="text/css" href="http://www.jq22.com/jquery/bootstrap-3.3.4.css">
    <script type="text/javascript" src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="<%=ctxPath %>/js/Tabullet.js"></script>
    <style>
    body {
        background-color: #fafafa;
    }

    .container {
        margin: 150px auto;
    }
    </style>
    <script>
    $(function() {
        var source = [{
            id: 1,
            name: "Aditya Purwa",
            level: "Admin"
        }, {
            id: 2,
            name: "Aditya Avaga",
            level: "Manager"
        }, ];

        function resetTabullet() {
            $("#table").tabullet({
                data: source,
                action: function(mode, data) {
                    console.dir(mode);
                    if (mode === 'save') {
                        source.push(data);
                    }
                    if (mode === 'edit') {
                        for (var i = 0; i < source.length; i++) {
                            if (source[i].id == data.id) {
                                source[i] = data;
                            }
                        }
                    }
                    if (mode == 'delete') {
                        for (var i = 0; i < source.length; i++) {
                            if (source[i].id == data) {
                                source.splice(i, 1);
                                break;
                            }
                        }
                    }
                    resetTabullet();
                }
            });
        }

        resetTabullet();
    });
    </script>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <h1>日期调整新增数量比例</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <table class="table table-hover" id="table">
                <thead>
                    <tr data-tabullet-map="id">
                        <th width="50" data-tabullet-map="_index" data-tabullet-readonly="true">num</th>
                        <th data-tabullet-map="dayTime">日期</th>
                        <th data-tabullet-map="one">19点-5点(所占比)</th>
                        <th data-tabullet-map="two">6-9点/13-15点(所占比)</th>
                        <th data-tabullet-map="three">10-12点/16-18点(所占比)</th>
                        <th data-tabullet-map="count">今日总数</th>
                        <th width="50" data-tabullet-type="edit"></th>
                        <th width="50" data-tabullet-type="delete"></th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
</body>
</html>
