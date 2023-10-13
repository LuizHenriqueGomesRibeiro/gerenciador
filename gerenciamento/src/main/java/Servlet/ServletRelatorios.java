package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import org.apache.tomcat.jakartaee.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import DAO.DAOGeneric;
import DAO.DaoGenerico;
import DAO.daoPedidos;
import DAO.daoVendas;
import Util.ReportUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelPedidos;
import model.ModelVendas;
import java.io.OutputStream;

/**
 * Servlet implementation class ServletRelatorios
 */
public class ServletRelatorios extends servlet_recuperacao_login{
	private static final long serialVersionUID = 1L;
	
	daoVendas daoVendas = new daoVendas();
	daoPedidos daoPedidos = new daoPedidos();
	DaoGenerico dao = new DaoGenerico();
       
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
				List<ModelVendas> vendas = daoVendas.listarVendas(super.getUsuarioLogado(request).getId());
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
				int status = 2;
				String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + super.getUsuarioLogado(request).getId();
				List<ModelPedidos> entregas = daoPedidos.listarPedidos(sql);
				Gson gson = new Gson();
				String json = gson.toJson(entregas);
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(json);
				printWriter.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("pedidos")) {
			
			try {
				String sql = "SELECT * FROM pedidos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId();
				List<ModelPedidos> pedidos = daoPedidos.listarPedidos(sql);
				Gson gson = new Gson();
				String json = gson.toJson(pedidos);
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(json);
				printWriter.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelamentos")) {
			try {
				int status = 1;
				String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + super.getUsuarioLogado(request).getId();
				List<ModelPedidos> cancelamentos = daoPedidos.listarPedidos(sql);
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
			
			String dataInicial = request.getParameter("dataInicial");
			String dataFinal = request.getParameter("dataFinal");
			
			try{
				if(dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()){
					List<ModelVendas> vendas = daoVendas.listarVendas(super.getUsuarioLogado(request).getId());
					
					ReportUtil reportUtil = new ReportUtil();
					byte[] relatorio = reportUtil.geraReltorioPDF(vendas, "vendas", request.getServletContext());

					if (relatorio != null) {
						response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
			            response.setContentType("application/octet-stream");

			            try (OutputStream outputStream = response.getOutputStream()) {
			                outputStream.write(relatorio);
			                outputStream.flush();
			            }
			        } else {
			            response.sendError(HttpServletResponse.SC_NOT_FOUND);
			        }
				}else {
					List<ModelVendas> vendas = daoVendas.listarVendasPorTempo(super.getUsuarioLogado(request).getId(), dataInicial, dataFinal);
					
					ReportUtil reportUtil = new ReportUtil();
					byte[] relatorio = reportUtil.geraReltorioPDF(vendas, "vendas", request.getServletContext());

					if (relatorio != null) {
						response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
			            response.setContentType("application/octet-stream");

			            try (OutputStream outputStream = response.getOutputStream()) {
			                outputStream.write(relatorio);
			                outputStream.flush();
			            }
			        } else {
			            response.sendError(HttpServletResponse.SC_NOT_FOUND);
			        }
				}
				
			}catch (Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("printFormEntradasPDF")) {
			String dataInicial = request.getParameter("dataInicial");
			String dataFinal = request.getParameter("dataFinal");
			
			try{
				if(dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()){
					int status = 2;
					String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + super.getUsuarioLogado(request).getId();
					List<ModelPedidos> entradas = daoPedidos.listarPedidos(sql);
					
					ReportUtil reportUtil = new ReportUtil();
					byte[] relatorio = reportUtil.geraReltorioPDF(entradas, "entradas", request.getServletContext());

					if (relatorio != null) {
						response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
			            response.setContentType("application/octet-stream");

			            try (OutputStream outputStream = response.getOutputStream()) {
			                outputStream.write(relatorio);
			                outputStream.flush();
			            }
			        } else {
			            response.sendError(HttpServletResponse.SC_NOT_FOUND);
			        }
				}else {
					int status = 2;
					String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + super.getUsuarioLogado(request).getId() 
							+ " AND dataentrega >= " +     dao.converterDatas(dataInicial) + " AND dataentrega <= " + dao.converterDatas(dataFinal);
					List<ModelPedidos> entradas = daoPedidos.listarPedidos(sql);
					
					ReportUtil reportUtil = new ReportUtil();
					byte[] relatorio = reportUtil.geraReltorioPDF(entradas, "entradas", request.getServletContext());

					if (relatorio != null) {
						response.setHeader("Content-Disposition", "attachment;filename=arquivo.pdf");
			            response.setContentType("application/octet-stream");

			            try (OutputStream outputStream = response.getOutputStream()) {
			                outputStream.write(relatorio);
			                outputStream.flush();
			            }
			        } else {
			            response.sendError(HttpServletResponse.SC_NOT_FOUND);
			        }
				}
				
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
