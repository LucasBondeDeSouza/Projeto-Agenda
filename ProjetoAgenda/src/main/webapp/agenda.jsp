<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="model.Javabeans"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Javabeans" %>
<%@ page import="java.util.ArrayList"%>

<%
ArrayList<Javabeans> lista = (ArrayList<Javabeans>)request.getAttribute("contatos");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Agenda de Contatos</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="icon" href="imagens/favicon.png">
<link rel="stylesheet" href="style.css">
</head>
<body>
     <h1>Agenda de Contatos</h1>
     <a href="novo.html" class="botao1">Novo Contato</a>
     <a href="report" class="botao2" target="_blank">Relatório</a>
     
     <p>
     <table id="tabela">
           <thead>
                  <tr>
                      <th>Id</th>
                      <th>Nome</th>
                      <th>Fone</th>
                      <th>E-mail</th>
                      <th>Opções</th>
                  </tr>
           </thead>
           <tbody>
                      <% for (int i=0; i < lista.size(); i++){ %>
                            <tr>
                                <td><%=lista.get(i).getIdcon()%></td>
                                <td><%=lista.get(i).getNome()%></td>
                                <td><%=lista.get(i).getFone()%></td>
                                <td><%=lista.get(i).getEmail()%></td>
                                <td><a href="select?idcon=<%=lista.get(i).getIdcon()%>" class="botao1">Editar</a></td>
                                <td><a href="delete?idcon=<%= lista.get(i).getIdcon() %>" class="botao2">Excluir</a></td>
                            </tr>
                      <%}%>
           </tbody>
      </table>
      
      <script src="scripts/confirmador.js"></script>
      
</body>
</html>