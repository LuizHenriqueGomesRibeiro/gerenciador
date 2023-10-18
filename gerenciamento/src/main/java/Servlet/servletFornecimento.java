package Servlet;

import java.io.IOException;

import DAO.Despache;
import DAO.DaoGenerico;
import DAO.daoEntradasRelatorio;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLFornecimento;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import Servlet.API.Extends.APIFornecimento;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelData;
import model.ModelPedidos;
import model.ModelProdutos;
import model.ModelUsuarios;

/**
 * Servlet implementation class servletFornecimento
 */
public class servletFornecimento extends APIFornecimento {
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoFornecimento daofornecimento = new daoFornecimento();
	daoProdutos daoproduto = new daoProdutos();
	daoPedidos pedido = new daoPedidos();
	daoPedidos daopedidos = new daoPedidos();
	daoEntradasRelatorio daoEntradaRelatorio = new daoEntradasRelatorio();
	DaoGenerico dao = new DaoGenerico();
	SQLProdutos sqlprodutos = new SQLProdutos();
	SQLPedidos sqlpedidos = new SQLPedidos();
	SQLFornecimento sqlFornecimento = new SQLFornecimento();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletFornecimento() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String acao = request.getParameter("acao");
		try {
			ModelUsuarios usuario = super.getUsuarioLogado(request);
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("incluirPedido")) {
				incluirPedido(request, usuario);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("confirmarPedido")){
				confirmarPedido(request, usuario);
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelarPedido")){
				//Long id_pedido = Long.parseLong(request.getParameter("id"));
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarFornecedor")){
				deletarFornecedor(request, usuario);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String nomeFornecedor = request.getParameter("nomeFornecedor");
			String tempoentrega = request.getParameter("tempoentrega");
			int valor = dao.converterDinheiroInteger(request.getParameter("valor"));
			
			ModelProdutos modelProdutos = new ModelProdutos();
			modelProdutos.setId(request.getParameter("id") != null && !request.getParameter("id").isEmpty() ? Long.parseLong(request.getParameter("id")) : null);
			new daoFornecimento().gravarNovoFornecedor(sqlFornecimento.gravar(nomeFornecedor, modelProdutos, Integer.parseInt(tempoentrega), valor));
			
			super.setarAtributos(request, super.getUsuarioLogado(request));
			request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	protected void incluirPedido(HttpServletRequest request, ModelUsuarios usuario) throws Exception {
		ModelPedidos modelPedido = super.parametrosIncluirPedido(request, usuario);
		pedido.gravarPedido(modelPedido);
		super.setarAtributos(request, usuario);
	}

	protected void confirmarPedido(HttpServletRequest request, ModelUsuarios usuario) throws Exception {
		ModelData modelData = super.parametrosConfirmarPedido(request, usuario);
		daoEntradaRelatorio.alternarData(modelData);
		daoproduto.adicionaProdutoCaixa(Long.parseLong(request.getParameter("id_produto")), Integer.parseInt(request.getParameter("quantidade")));
		daopedidos.mudarStatus(Long.parseLong(request.getParameter("id")), 2);
	}
	
	protected void deletarFornecedor(HttpServletRequest request, ModelUsuarios usuario) throws Exception {
		daofornecimento.excluirFornecedor(Long.parseLong(request.getParameter("id")));
	}
}
