package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import DAO.API;
import DAO.DaoGenerico;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import DAO.SQL.SQLPedidos;
import DAO.SQL.SQLProdutos;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFornecimento;
import model.ModelProdutos;
import model.ModelUsuarios;

/**
 * Servlet implementation class servlet_cadastro_e_atualizacao_produtos
 */
public class servlet_cadastro_e_atualizacao_produtos extends servlet_recuperacao_login {
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoProdutos daoproduto = new daoProdutos();
	daoFornecimento daoFornecimento = new daoFornecimento();
	daoPedidos daopedidos = new daoPedidos();
	DaoGenerico dao = new DaoGenerico();
	SQLPedidos sqlpedidos = new SQLPedidos();
	SQLProdutos sqlprodutos = new SQLProdutos();
	API api = new API();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servlet_cadastro_e_atualizacao_produtos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			int id = super.getUsuarioLogado(request).getId();
			ModelUsuarios usuario = super.getUsuarioLogado(request);
			
			request.setAttribute("usuario", super.getUsuarioLogado(request));
			
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listar")) {

				api.setarAtributos(request, usuario);
				request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")){
				
				api.setarAtributosOFFSET(request, usuario);
				request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("excluir")){
				
				Long id_produto = Long.parseLong(request.getParameter("id"));
				daoproduto.excluirProduto(id_produto);
				
				api.setarAtributos(request, usuario);
				request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("configuracoes")){

				Long id_produto = Long.parseLong(request.getParameter("id"));
				api.setarAtributos(request, usuario);

				ModelProdutos dadosJsonUser1 = daoproduto.consultaProduto(id_produto, id);
				List<ModelFornecimento> dadosJsonUser2 = daoFornecimento.listarFornecedores(Long.parseLong(request.getParameter("id")));
				PrintWriter out = response.getWriter();
			    out.print(new Gson().toJson(dadosJsonUser1) + "|" + new Gson().toJson(dadosJsonUser2));
			    out.flush();
			    
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("historioPedidos")){

				String status = "0";
				api.impressaoJSONPedidos(response, daopedidos.listarPedidos(sqlpedidos.listaPedidosProdutoId(Integer.parseInt(request.getParameter("id")), status)));
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("exclusaoAjax")){
				
				Long id_produto = Long.parseLong(request.getParameter("id"));
				ModelProdutos dadosJsonUser = daoproduto.consultaProduto(id_produto, id);
				api.impressaoJSONProdutos(response, dadosJsonUser);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("confirmarPedido")){

				daoproduto.consultaProduto(Long.parseLong(request.getParameter("id_produto")), id);
				daoproduto.adicionaProdutoCaixa(Long.parseLong(request.getParameter("id_produto")), Integer.parseInt(request.getParameter("quantidade")));
				daopedidos.mudarStatus(Long.parseLong(request.getParameter("id")), 2);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelarPedido")){
				
				daopedidos.mudarStatus(Long.parseLong(request.getParameter("id")), 1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = super.getUsuarioLogado(request).getId();
			ModelUsuarios usuario = super.getUsuarioLogado(request);
			
			ModelProdutos modelProduto = new ModelProdutos();
			
			modelProduto.setNome(request.getParameter("nome"));
			modelProduto.setUsuario_pai_id(daologin.consultaUsuarioLogadoId(id));
			
			if(modelProduto.isNovo()) {
				daoproduto.gravarProduto(modelProduto);
			}else {
				daoproduto.atualizarProduto(modelProduto);
			}
			
			api.setarAtributos(request, usuario);
			request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.getRequestDispatcher("principal/principal.jsp").forward(request, response);
		}
		
	}
}
