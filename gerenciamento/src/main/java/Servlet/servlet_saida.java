package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;
import model.ModelUsuarios;
import model.ModelVendas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.daoVendas;
import Util.BeanChart;

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
	daoVendas daovendas = new daoVendas();
	
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
			String valor = request.getParameter("valor");
			valor = valor.replaceAll("\\.", "").replaceAll("\\,00", "");
			
	        String valor_R$ = valor.replace("R$", "");
	        valor_R$ = valor_R$.replaceAll("[^0-9]", "");

			int valor_R$Long = Integer.parseInt(valor_R$);
			String quantidade = request.getParameter("quantidade");
			quantidade = quantidade.replaceAll("\\.", "");
			int quantidadeInt = Integer.parseInt(quantidade);
			int quantidadeIntInversa = -quantidadeInt;
			
			LocalDate dataAtual = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        String dataFormatada = dataAtual.format(formatter);			
			
			try {
				daoproduto.adicionaProdutoCaixa(Integer.parseInt(id), quantidadeIntInversa);
				ModelVendas venda = new ModelVendas();
				ModelProdutos produto = daoproduto.consultaProduto(Long.parseLong(id), super.getUsuarioLogado(request).getId());
				//ModelFornecimento fornecimento = daoFornecimento.consultaFornecedor(null, Long.parseLong(id), super.getUsuarioLogado(request).getId());
				venda.setProduto_pai(produto);
				venda.setQuantidade(quantidadeInt);
				venda.setDataentrega(dataFormatada);
				venda.setValortotal(valor_R$Long);
				daovendas.gravarVenda(venda, super.getUsuarioLogado(request).getId());
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
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("loadProduto")) {
			String id = request.getParameter("id");
			try {
				ModelProdutos produto = daoproduto.consultaProduto(Long.parseLong(id), super.getUsuarioLogado(request).getId());
				Double medias = daoFornecimento.mediaValoresFornecimento(Long.parseLong(id));
				
				Gson gson = new Gson();
				String json1 = gson.toJson(produto);
				String json2 = gson.toJson(medias);
				PrintWriter out = response.getWriter();
			    out.print(json1 + "|" + json2);
			    out.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("loadFinanceiro")) {
			String id = request.getParameter("id");
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("financeiro")) {
			
			RequestDispatcher despache = request.getRequestDispatcher("principal/financeiro.jsp");
			despache.forward(request, response);
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("carregarListaVendas")) {
			
			String dataInicial = request.getParameter("dataInicial");
			String dataFinal = request.getParameter("dataFinal");
	
			try {
				
				BeanChart bean = daovendas.listarVendasGrafico(super.getUsuarioLogado(request).getId());
				System.out.println(bean.getDatas());
				System.out.println(bean.getValores());
			
				if(dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()){
					
					List<ModelVendas> vendas = daovendas.listarVendas(super.getUsuarioLogado(request).getId());
					
					Gson gson = new Gson();
					String json = gson.toJson(vendas);
					PrintWriter printWriter = response.getWriter();
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					printWriter.write(json);
					printWriter.close();
					
				}else{
					
					List<ModelVendas> vendas = daovendas.listarVendasPorTempo(super.getUsuarioLogado(request).getId(), dataInicial, dataFinal);
					
					Gson gson = new Gson();
					String json = gson.toJson(vendas);
					PrintWriter printWriter = response.getWriter();
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					printWriter.write(json);
					printWriter.close();
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("carregarListaEntradas")) {
			
			String dataInicial = request.getParameter("dataInicial");
			String dataFinal = request.getParameter("dataFinal");
	
			try {
			
				if(dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()){
					
					int status = 2;
					List<ModelPedidos> entradas = daopedidos.listarRelatorio(super.getUsuarioLogado(request).getId(), status);
					
					Gson gson = new Gson();
					String json = gson.toJson(entradas);
					PrintWriter printWriter = response.getWriter();
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					printWriter.write(json);
					printWriter.close();
					
				}else{
					
					List<ModelVendas> vendas = daovendas.listarVendasPorTempo(super.getUsuarioLogado(request).getId(), dataInicial, dataFinal);
					
					Gson gson = new Gson();
					String json = gson.toJson(vendas);
					PrintWriter printWriter = response.getWriter();
					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					printWriter.write(json);
					printWriter.close();
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("carregarListaLucros")) {
			
			String dataInicial = request.getParameter("dataInicial");
			String dataFinal = request.getParameter("dataFinal");
	
			try {
			
				if(dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()){
					
					
				}else{


				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}

}
