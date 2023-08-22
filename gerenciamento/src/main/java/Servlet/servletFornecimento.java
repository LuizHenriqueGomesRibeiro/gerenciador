package Servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;

import java.io.IOException;
import java.util.List;

import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoProdutos;

/**
 * Servlet implementation class servletFornecimento
 */
public class servletFornecimento extends servlet_recuperacao_login {
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoFornecimento daofornecimento = new daoFornecimento();
	daoProdutos daoproduto = new daoProdutos();
       
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
		
		if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("incluirPedido")) {

			String fornecimento_pai_id = request.getParameter("fornecimento_pai_id");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");
			String dataPedido = request.getParameter("dataPedido");
			String dataEntrega = request.getParameter("dataEntrega");
			
			quantidade = quantidade.replaceAll("\\.", "");
			String valor_R$ = valor.replace("R$", "");
			valor_R$ = valor_R$.replaceAll("[^0-9]", "");
			//é necessário pegar o id do produto para continuar com o insert pedido;
			//ModelFornecimento modelFornecimento = daofornecimento.listarFornecedores();
			
			
			
			ModelPedidos modelPedidos = new ModelPedidos();
			
			
			
			//modelPedidos.setFornecedor_pai_id();
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String nomeFornecedor = request.getParameter("nomeFornecedor");
			String id = request.getParameter("id");
			
			ModelProdutos modelProdutos = new ModelProdutos();
			modelProdutos.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			
			daoFornecimento daoFornecimento = new daoFornecimento();
			
			daoFornecimento.gravarNovoFornecedor(nomeFornecedor, modelProdutos);
			
			request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
			String numero = "R$" + daoproduto.somaProdutos(this.getUsuarioLogado(request).getId()) + ",00";
			request.setAttribute("soma", numero);
	
			List<ModelProdutos> produtos = daoproduto.listarProdutos(super.getUsuarioLogado(request).getId());
			request.setAttribute("produtos", produtos);
			
			RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
			despache.forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
