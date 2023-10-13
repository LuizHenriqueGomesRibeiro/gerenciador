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
					BeanChart bean = daovendas.listarVendasGrafico(super.getUsuarioLogado(request).getId());
					BeanChart entradas = daopedidos.listarEntradasGrafico(super.getUsuarioLogado(request).getId(), status);
					
					ModelData dataVenda = new ModelData();
					dataVenda.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(dataVenda);
					
					ModelData dataEntrada = new ModelData();
					dataEntrada.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(dataEntrada);
					
					Gson gson = new Gson();
					String json1 = gson.toJson(bean);
					String json2 = gson.toJson(entradas);
					String json3 = gson.toJson(dataVendas);
					String json4 = gson.toJson(dataEntradas);
					
					List<ModelVendas> vendas = daovendas.listarVendas(super.getUsuarioLogado(request).getId());
					
					String json = gson.toJson(vendas);
					PrintWriter out = response.getWriter();
				    out.print(json1 + "|" + json + "|" + json2 + "|" + json3 + "|" + json4);
				    out.flush();
				    
				}else{
					BeanChart bean = daovendas.listarVendasGrafico(super.getUsuarioLogado(request).getId(), dataInicial, dataFinal);
					BeanChart entradas = daopedidos.listarEntradasGrafico(super.getUsuarioLogado(request).getId(), status, dataInicial, dataFinal);
					
					ModelData dataVenda = new ModelData();
					dataVenda.setUsuario_pai_id(super.getUsuarioLogado(request));
					
					List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(dataVenda, dataInicial, dataFinal);
					
					ModelData dataEntrada = new ModelData();
					dataEntrada.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(dataEntrada);

					Gson gson = new Gson();
					String json1 = gson.toJson(bean);
					String json2 = gson.toJson(entradas);
					String json3 = gson.toJson(dataVendas);
					String json4 = gson.toJson(dataEntradas);
					
					List<ModelVendas> vendas = daovendas.listarVendasPorTempo(super.getUsuarioLogado(request).getId(), dataInicial, dataFinal);
					
					String json = gson.toJson(vendas);
					PrintWriter out = response.getWriter();
				    out.print(json1 + "|" + json + "|" + json2 + "|" + json3 + "|" + json4);
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
					BeanChart bean = daopedidos.listarEntradasGrafico(super.getUsuarioLogado(request).getId(), status);
					
					Gson gson = new Gson();
					String json1 = gson.toJson(bean);
					
					String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + super.getUsuarioLogado(request).getId();
					List<ModelPedidos> entradas = daopedidos.listarPedidos(sql);
					
					String json = gson.toJson(entradas);
					PrintWriter out = response.getWriter();
				    out.print(json1 + "|" + json);
				    out.flush();
				}else{
					BeanChart bean = daopedidos.listarEntradasGrafico(super.getUsuarioLogado(request).getId(), status, dataInicial, dataFinal);
					
					Gson gson = new Gson();
					String json1 = gson.toJson(bean);

					String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + super.getUsuarioLogado(request).getId() 
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
