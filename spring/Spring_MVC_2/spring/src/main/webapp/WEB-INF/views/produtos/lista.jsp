<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Livros de Java, Android, iPhone, Ruby, PHP e muito mais - Casa do Código</title>
	</head>
	<body>
		<h1>Lista de Produtos</h1>
		
		<p> ${sucesso} </p>
				
		<table>
        <tr>
            <td>Título</td>
            <td>Descrição</td>
            <td>Páginas</td>
            <td>Link</td>
        </tr>
        <c:forEach items="${produtos}" var="item">
        	<tr>
	        	<td>
	        		${item.titulo}
	        	</td>
	        	<td>
	        		${item.descricao}
	        	</td>
	        	<td>
	        		${item.paginas}
	        	</td>
	        	<td>
	        		<a href="${s:mvcUrl('PC#detalhe').arg(0, item.id).build()}"> ${item.titulo}</a>
	        	</td>
	        </tr>
        </c:forEach>
        
           <c:forEach items="${tipos}" var="tipoPreco" varStatus="status">
	        <div>
	            <label>${tipoPreco}</label>
	            <input type="text" name="precos[${status.index}].valor" />
	            <input type="hidden" name="precos[${status.index}].tipo" value="${tipoPreco}" />
	        </div>
	    </c:forEach>
    </table>
	</body>
</html>