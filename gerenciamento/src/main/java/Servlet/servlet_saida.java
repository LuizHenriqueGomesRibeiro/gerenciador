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
import Servlet.SQL.SQL;
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
	SQL sql = new SQL();
	
	public servlet_saida() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("caixaListar")) {
			try {
				int id_usuario = super.getUsuarioLogado(request).getId();
				List<ModelProdutos> produtos = daoproduto.listarProdutos(sql.listaProdutosLIMIT10(id_usuario), id_usuario);
				request.setAttribute("produtos", produtos);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.getRequestDispatcher("principal/saida.jsp").forward(request, response);
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("vender")) {
			Long id = Long.parseLong(request.getParameter("idProduto"));
			int valor = dao.converterDinheiroInteger(request.getParameter("valor"));//R$####,00;
			String data = dao.converterDatas(request.getParameter("dataVenda"));//dd-MM-yyyy
			int quantidade = Integer.parseInt(request.getParameter("quantidade"));
			try {
				int id_usuario = super.getUsuarioLogado(request).getId();
				
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
				ModelProdutos produto = daoproduto.consultaProduto(id, id_usuario);
				venda.setProduto_pai(produto);
				venda.setQuantidade(quantidade);
				venda.setDataentrega(data);
				venda.setValortotal(valor*quantidade);
				daovendas.gravarVenda(venda, super.getUsuarioLogado(request).getId());
				List<ModelProdutos> produtos = daoproduto.listarProdutos(sql.listaProdutosLIMIT10(id_usuario), id_usuario);
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
				int id = super.getUsuarioLogado(request).getId();
				
				if(dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()){
					BeanChart bean = daovendas.listarVendasGrafico(sql.listaVendasValorData(id));
					BeanChart entradas = daopedidos.listarEntradasGrafico(sql.listaEntradasValorData(id, status));
					
					ModelData dataVenda = new ModelData();
					dataVenda.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(dataVenda);
					
					ModelData dataEntrada = new ModelData();
					dataEntrada.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(dataEntrada);
					
					List<ModelVendas> vendas = daovendas.listarVendas(sql.listaVendas(id), sql.somaValoresVendas(id), sql.somaQuantidadeVendas(id));
					
					PrintWriter out = response.getWriter();
					Gson gson = new Gson();
				    out.print(gson.toJson(bean) + "|" + gson.toJson(vendas) + "|" + gson.toJson(entradas) + "|" + gson.toJson(dataVendas) + "|" + gson.toJson(dataEntradas));
				    out.flush();
				    
				}else{
					BeanChart bean = daovendas.listarVendasGrafico(sql.listaVendasValorDataTempo(id, dataInicial, dataFinal));
					BeanChart entradas = daopedidos.listarEntradasGrafico(sql.listaEntradasValorDataTempo(id, status, dataInicial, dataFinal));
					
					ModelData dataVenda = new ModelData();
					dataVenda.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataVendas = daoVendasRelatorio.listarDatasVendas(dataVenda, dataInicial, dataFinal);
					
					ModelData dataEntrada = new ModelData();
					dataEntrada.setUsuario_pai_id(super.getUsuarioLogado(request));
					List<ModelData> dataEntradas = daoEntradasRelatorio.listarDatasEntradas(dataEntrada);

					List<ModelVendas> vendas = daovendas.listarVendas(sql.listaVendasTempo(id, dataInicial, dataFinal), sql.somaValoresVendasTempo(id, dataInicial, dataFinal), sql.somaQuantidadeVendasTempo(id, dataInicial, dataFinal));
					
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
				int id = super.getUsuarioLogado(request).getId();
				if(dataInicial == null || dataInicial.isEmpty() || dataFinal == null || dataFinal.isEmpty()){
					BeanChart bean = daopedidos.listarEntradasGrafico(sql.listaEntradasValorData(id, status));
					
					Gson gson = new Gson();
					String json1 = gson.toJson(bean);
					
					String sql = "SELECT * FROM pedidos WHERE status = " + status + " AND usuario_pai_id = " + id;
					List<ModelPedidos> entradas = daopedidos.listarPedidos(sql);
					
					String json = gson.toJson(entradas);
					PrintWriter out = response.getWriter();
				    out.print(json1 + "|" + json);
				    out.flush();
				}else{
					BeanChart bean = daopedidos.listarEntradasGrafico(sql.listaEntradasValorDataTempo(id, status, dataInicial, dataFinal));
					
					Gson gson = new Gson();
					String json1 = gson.toJson(bean);

					List<ModelPedidos> entradas = daopedidos.listarPedidos(sql.listaEntradasTempo(id, status, dataInicial, dataFinal));
					
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
