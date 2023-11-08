package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;
import model.Javabeans;

@WebServlet(urlPatterns = {
		"/controller", "/main", "/insert", "/select", "/update", "/delete", "/report"
})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DAO dao = new DAO();
	Javabeans contato = new Javabeans();
       
    public Controller() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// TESTE DE CONEXÃO
		// dao.testeConexao();
		
		String action = request.getServletPath();
		System.out.println(action);
		
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			removerContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			response.sendRedirect("index.html");
		}
	}
	
	// Método Listar Contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.sendRedirect("agenda.jsp");
		ArrayList<Javabeans> lista = dao.listarContatos();
		
		/*
		// Teste de recebimento do objeto lista
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i).getIdcon());
			System.out.println(lista.get(i).getNome());
			System.out.println(lista.get(i).getFone());
			System.out.println(lista.get(i).getEmail());
		}
		*/
		
		// Encaminhar a lista de documentos JSP
		request.setAttribute("contatos", lista);
		jakarta.servlet.RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
		}

	// Método Listar novoContato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		// Teste de recebimento dos dados do formulário
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));
		*/
		
		// Setar as variáveis Javabeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		// Chamar o método inserirContato() passando o objeto contato
		dao.inserirContato(contato);
		
		// Redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
	}
	
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		String idcon = request.getParameter("idcon");
		// Teste de recebimento dos dados do id do contato que será editado
		// System.out.println(idcon);
		
		// Setar a variável JavaBeans
		contato.setIdcon(idcon);
		// O método selecionarContato()
		dao.selecionarContato(contato);
		
		/* teste de recebimento
		System.out.println(contato.getIdcon());
		System.out.println(contato.getNome());
		System.out.println(contato.getFone());
		System.out.println(contato.getEmail());
		*/
		
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		
		jakarta.servlet.RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}
	
	protected void editarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		dao.alterarContato(contato);

		response.sendRedirect("main");
	}

	/*protected void removerContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idcon = request.getParameter("idcon");
		System.out.println(idcon);
		
		contato.setIdcon(idcon);
		
		dao.deletarContato(contato);
	}*/
	
    protected void removerContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String idcon = request.getParameter("idcon");
    	
    	dao.deletarContato(idcon);
    	
    	response.sendRedirect("main");
    }
    
    protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	Document documento = new Document();
    	
    	try {
    		// Tipo de Conteúdo
    		response.setContentType("application/pdf");
    		// Nome do Documento
    		response.reset();
    		response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");
    		// Criar o Documento
    		PdfWriter.getInstance(documento, response.getOutputStream());
    		// Abrir o Documento -> Conteúdo
    		documento.open();
    		documento.add(new Paragraph("Lista de Contatos"));
    		documento.add(new Paragraph(" "));
    		
    		PdfPTable tabela = new PdfPTable(3);
    		PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
    		PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
    		PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
    		
    		tabela.addCell(col1);
    		tabela.addCell(col2);
    		tabela.addCell(col3);
    		
    		ArrayList<Javabeans> lista = dao.listarContatos();
    		for (int i = 0; i < lista.size(); i++) {
    			tabela.addCell(lista.get(i).getNome());
    			tabela.addCell(lista.get(i).getFone());
    			tabela.addCell(lista.get(i).getEmail());
    		}
    		documento.add(tabela);
    		documento.close();
    	} catch (Exception e) {
    		System.out.println(e);
    		documento.close();
    	}
    }
}