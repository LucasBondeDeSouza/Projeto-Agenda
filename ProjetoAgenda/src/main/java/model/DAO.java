package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	
	/** Módulo de Conexão **/
	// Parâmetros de Conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	
	private String user = "root";
	private String password = "bondelucas";
	
	// Método de Conexão
	private Connection conectar() {
		Connection con = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/*
	// Teste de Conexão
	public void testeConexao() {
		
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	*/
	
	public void inserirContato(Javabeans contato) {
		
		String create = "insert into contatos (nome, fone, email) values (?,?,?)";
		
		try {
			// Abrir a conexão
			Connection con = conectar();
			
			// Preparar a Query para execução no Banco de Dados
			PreparedStatement pst = con.prepareStatement(create);
			
			// Substituir os parametros (?) pelo conteúdo das variáveis Javabeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			
			// Executar a Query
			pst.executeUpdate();
			
			// Encerrar a conexão com o Banco
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public ArrayList<Javabeans> listarContatos() {
		
		// Criando um objeto para acessar a Classe JavaBeans
		ArrayList<Javabeans> contatos = new ArrayList<>();
		
		String read = "select * from contatos order by nome";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery(read);
			
			// O laço abaixo será executado enquanto houver contatos a serem listados
			while (rs.next()) {
				// Variáveis de apoio que recebem dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				
				// Populando o ArrayList
				contatos.add(new Javabeans(idcon, nome, fone, email));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		// return contatos;
		return contatos;
	}
	
	public void selecionarContato(Javabeans contato) {
		String read2 = "select * from contatos where idcon = ?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void alterarContato(Javabeans contato) {
		String create = "Update contatos set nome=?, fone=?, email=? where idcon=?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void deletarContato(String idcon) {
		String delete = "delete from contatos where idcon=?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, idcon);
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
