package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelPedidos;
import model.ModelVendas;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import DAO.daoPedidos;
import DAO.daoVendas;
import Util.ReportUtil;

/**
 * Servlet implementation class ServletRelatorios
 */
public class ServletRelatorios extends servlet_recuperacao_login{
	private static final long serialVersionUID = 1L;
	
	daoVendas daoVendas = new daoVendas();
	daoPedidos daoPedidos = new daoPedidos();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRelatorios() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("irParaRelatorios")) {
			
			RequestDispatcher despache = request.getRequestDispatcher("principal/relatorios.jsp");
			despache.forward(request, response);
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("vendas")) {
			
			try {
				List<ModelVendas> vendas = daoVendas.listarVendasTotais(super.getUsuarioLogado(request).getId());
				Gson gson = new Gson();
				String json = gson.toJson(vendas);
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(json);
				printWriter.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("entradas")) {
			try {
				int a = 2;
				List<ModelPedidos> entregas = daoPedidos.listarRelatorio(super.getUsuarioLogado(request).getId(), a);
				System.out.println(entregas.toString());
				Gson gson = new Gson();
				String json = gson.toJson(entregas);
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(json);
				printWriter.close();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("pedidos")) {
			
			try {
				List<ModelPedidos> pedidos = daoPedidos.listarPedidosRelatorio(super.getUsuarioLogado(request).getId());
				Gson gson = new Gson();
				String json = gson.toJson(pedidos);
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(json);
				printWriter.close();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelamentos")) {
			try {
				int a = 1;
				List<ModelPedidos> cancelamentos = daoPedidos.listarRelatorio(super.getUsuarioLogado(request).getId(), a);
				System.out.println(cancelamentos.toString());
				Gson gson = new Gson();
				String json = gson.toJson(cancelamentos);
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(json);
				printWriter.close();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("printFormVendasPDF")) {
			try{
				List<ModelVendas> vendas = daoVendas.listarVendas(super.getUsuarioLogado(request).getId());
				ReportUtil report = new ReportUtil();
				byte[] relatorio = report.geraReltorioPDF(vendas, "vendas", request.getServletContext());
				System.out.println(relatorio);
				
				response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
				response.getOutputStream().write(relatorio);
				
			}catch (Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
