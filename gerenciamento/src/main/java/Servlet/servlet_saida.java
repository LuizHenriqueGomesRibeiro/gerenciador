package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ModelProdutos;
import model.ModelUsuarios;
import model.ModelVendas;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.daoVendas;

/**
 * Servlet implementation class servletLogin
 */
@WebServlet(urlPatterns = { "/servlet_saida"}) 
public class servlet_saida extends servlet_recuperacao_login{
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoProdutos daoproduto = new daoProdutos();
	daoFornecimento daoFornecimento = new daoFornecimento();
	daoPedidos daopedidos = new daoPedidos();
	daoVendas vendas = new daoVendas();
	
	public servlet_saida() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("caixaListar")) {
			try {
				List<ModelProdutos> produtos = daoproduto.listarProdutos(super.getUsuarioLogado(request).getId());
				request.setAttribute("produtos", produtos);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			RequestDispatcher despache = request.getRequestDispatcher("principal/saida.jsp");
			despache.forward(request, response);
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("vender")) {
			String id = request.getParameter("idProduto");
			String quantidade = request.getParameter("quantidade");
			int quantidadeInt = Integer.parseInt(quantidade);
			int quantidadeIntInversa = -quantidadeInt;
			
			LocalDate dataAtual = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        String dataFormatada = dataAtual.format(formatter);			
			
			try {
				daoproduto.adicionaProdutoCaixa(Integer.parseInt(id), quantidadeIntInversa);
				ModelVendas venda = new ModelVendas();
				ModelProdutos produto = daoproduto.consultaProduto(Long.parseLong(id), super.getUsuarioLogado(request).getId());
				venda.setProduto_pai(produto);
				venda.setQuantidade(quantidadeInt);
				venda.setDataentrega(dataFormatada);
				venda.setQuantidade(quantidadeInt);
				vendas.gravarVenda(venda);
				List<ModelProdutos> produtos = daoproduto.listarProdutos(super.getUsuarioLogado(request).getId());
				request.setAttribute("produtos", produtos);
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}

}
