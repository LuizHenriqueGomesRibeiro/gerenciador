package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelData;
import model.ModelPedidos;
import model.ModelProdutos;
import model.ModelVendas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import DAO.DaoGenerico;
import DAO.daoEntradasRelatorio;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.daoVendas;
import DAO.daoVendasRelatorio;
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
	daoVendasRelatorio daoVendasRelatorio = new daoVendasRelatorio();
	daoEntradasRelatorio daoEntradasRelatorio = new daoEntradasRelatorio();
	DaoGenerico dao = new DaoGenerico();
	
	public servlet_saida() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("caixaListar")) {
			try {
				String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " LIMIT 10";
				List<ModelProdutos> produtos = daoproduto.listarProdutos(sql, super.getUsuarioLogado(request).getId());
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
			Long id = Long.parseLong(request.getParameter("idProduto"));
			int valor = dao.converterDinheiroInteger(request.getParameter("valor"));//R$####,00;
			String data = dao.converterDatas(request.getParameter("dataVenda"));//dd-MM-yyyy
			int quantidade = Integer.parseInt(request.getParameter("quantidade"));
			
			try {
				
				ModelData dataVenda = new ModelData();
				
				Long valortotal = (long) (valor * quantidade);
				dataVenda.setDatavenda(data);
				dataVenda.setValortotal(valortotal);
				dataVenda.setUsuario_pai_id(super.getUsuarioLogado(request));
				
				boolean booleana = daoVendasRelatorio.buscarData(dataVenda);
				
				if(booleana) {
					daoVendasRelatorio.atualizarDataEValor(dataVenda);
				}else {
					daoVendasRelatorio.inserirDataEValor(dataVenda);
				}
				
				daoproduto.adicionaProdutoCaixa(Integer.parseInt(request.getParameter("idProduto")) , -quantidade);
				ModelVendas venda = new ModelVendas();
				ModelProdutos produto = daoproduto.consultaProduto(id, super.getUsuarioLogado(request).getId());
				venda.setProduto_pai(produto);
				venda.setQuantidade(quantidade);
				venda.setDataentrega(data);
				venda.setValortotal(valor*quantidade);
				daovendas.gravarVenda(venda, super.getUsuarioLogado(request).getId());
				String sql = "SELECT * FROM produtos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " LIMIT 10";
				List<ModelProdutos> produtos = daoproduto.listarProdutos(sql, super.getUsuarioLogado(request).getId());
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
				int status = 2;
				
				if(dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()){
					String sql = "SELECT valortotal, dataentrega FROM vendas WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " ORDER BY dataentrega ASC";
					BeanChart bean = daovendas.listarVendasGrafico(sql);
					sql = "SELECT valortotal, dataentrega FROM pedidos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() 
							+ " AND status = " + status + " ORDER BY dataentrega ASC";
					BeanChart entradas = daopedidos.listarEntradasGrafico(sql);
					
					ModelData dataVenda = new ModelData();
					dataVenda.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(dataVenda);
					
					ModelData dataEntrada = new ModelData();
					dataEntrada.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(dataEntrada);
					
					
					sql = "SELECT * FROM vendas WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId();
					String sqlSomaValores = "SELECT SUM(valortotal) AS soma FROM vendas WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId();
					String sqlSomaQuantidade = "SELECT SUM(quantidade) AS soma FROM vendas WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId();
					List<ModelVendas> vendas = daovendas.listarVendas(sql, sqlSomaValores, sqlSomaQuantidade);
					
					PrintWriter out = response.getWriter();
					Gson gson = new Gson();
				    out.print(gson.toJson(bean) + "|" + gson.toJson(vendas) + "|" + gson.toJson(entradas) + "|" + gson.toJson(dataVendas) + "|" + gson.toJson(dataEntradas));
				    out.flush();
				    
				}else{
					String sql = "SELECT valortotal, dataentrega FROM vendas WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() 
							+ " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "' ORDER BY dataentrega ASC";
					BeanChart bean = daovendas.listarVendasGrafico(sql);
					sql = "SELECT valortotal, dataentrega FROM pedidos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " AND status = " + status 
							+ " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
					BeanChart entradas = daopedidos.listarEntradasGrafico(sql);
					
					ModelData dataVenda = new ModelData();
					dataVenda.setUsuario_pai_id(super.getUsuarioLogado(request));
					
					List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(dataVenda, dataInicial, dataFinal);
					
					ModelData dataEntrada = new ModelData();
					dataEntrada.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(dataEntrada);

					sql = "SELECT * FROM vendas WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
					String sqlSomaValores = "SELECT SUM(valortotal) AS soma FROM vendas WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
					String sqlSomaQuantidade = "SELECT SUM(quantidade) AS soma FROM vendas WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
					List<ModelVendas> vendas = daovendas.listarVendas(sql, sqlSomaValores, sqlSomaQuantidade);
					
					PrintWriter out = response.getWriter();
					Gson gson = new Gson();
				    out.print(gson.toJson(bean) + "|" + gson.toJson(vendas) + "|" + gson.toJson(entradas) + "|" + gson.toJson(dataVendas) + "|" + gson.toJson(dataEntradas));
				    out.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("carregarListaEntradas")) {
			
			String dataInicial = request.getParameter("dataInicial");
			String dataFinal = request.getParameter("dataFinal");
			
			try {
				int status = 2;
				if(dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()){
					String sql = "SELECT valortotal, dataentrega FROM pedidos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() 
							+ " AND status = " + status + " ORDER BY dataentrega ASC";
					BeanChart bean = daopedidos.listarEntradasGrafico(sql);
					
					Gson gson = new Gson();
					String json1 = gson.toJson(bean);
					
					sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + super.getUsuarioLogado(request).getId();
					List<ModelPedidos> entradas = daopedidos.listarPedidos(sql);
					
					String json = gson.toJson(entradas);
					PrintWriter out = response.getWriter();
				    out.print(json1 + "|" + json);
				    out.flush();
				}else{
					String sql = "SELECT valortotal, dataentrega FROM pedidos WHERE usuario_pai_id = " + super.getUsuarioLogado(request).getId() + " AND status = " + status 
							+ " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
					BeanChart bean = daopedidos.listarEntradasGrafico(sql);
					
					Gson gson = new Gson();
					String json1 = gson.toJson(bean);

					sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + super.getUsuarioLogado(request).getId() 
							+ " AND dataentrega >= '" + dataInicial + "' AND dataentrega <= '" + dataFinal + "'";
					List<ModelPedidos> entradas = daopedidos.listarPedidos(sql);
					
					String json = gson.toJson(entradas);
					PrintWriter out = response.getWriter();
				    out.print(json1 + "|" + json);
				    out.flush();
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
