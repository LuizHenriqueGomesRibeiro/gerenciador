package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;

import DAO.DaoGenerico;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import Servlet.SQL.SQL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;

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
	SQL sql = new SQL();
       
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
			request.setAttribute("usuario", super.getUsuarioLogado(request));
			
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listar")) {
				request.setAttribute("produtos", daoproduto.listarProdutos(sql.listaProdutosLIMIT10(id), id));
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(id)));
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(id));
				
				request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")){
			
				request.setAttribute("produtos", daoproduto.listarProdutos(sql.listaProdutosOFFSET(id, Integer.parseInt(request.getParameter("pagina"))), id));
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(id));
				request.setAttribute("usuario", super.getUsuarioLogado(request));
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(id)));
				
				request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("excluir")){
				Long id_produto = Long.parseLong(request.getParameter("id"));
				request.setAttribute("produtos", daoproduto.listarProdutos(sql.listaProdutosLIMIT10(id), id));
				daoproduto.excluirProduto(id_produto);
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(id));
				request.setAttribute("usuario", super.getUsuarioLogado(request));
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(id)));
				
				request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("configuracoes")){
				Long id_produto = Long.parseLong(request.getParameter("id"));
				request.setAttribute("produtos", daoproduto.listarProdutos(sql.listaProdutosLIMIT10(id), id));
				request.setAttribute("usuario", super.getUsuarioLogado(request));
				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(id));
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(id)));

				ModelProdutos dadosJsonUser1 = daoproduto.consultaProduto(id_produto, id);
				List<ModelFornecimento> dadosJsonUser2 = daoFornecimento.listarFornecedores(Long.parseLong(request.getParameter("id")));
				PrintWriter out = response.getWriter();
			    out.print(new Gson().toJson(dadosJsonUser1) + "|" + new Gson().toJson(dadosJsonUser2));
			    out.flush();
			    
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("historioPedidos")){
				int status = 0;
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(new Gson().toJson(daopedidos.listarPedidos(sql.listaPedidos(Integer.parseInt(request.getParameter("id")), status))));
				printWriter.close();
				
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("exclusaoAjax")){
				
				Long id_produto = Long.parseLong(request.getParameter("id"));
				ModelProdutos dadosJsonUser = daoproduto.consultaProduto(id_produto, id);
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				printWriter.write(new Gson().toJson(dadosJsonUser));
				printWriter.close();
				
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
			ModelProdutos modelProduto = new ModelProdutos();
			
			modelProduto.setNome(request.getParameter("nome"));
			modelProduto.setUsuario_pai_id(daologin.consultaUsuarioLogadoId(id));
			
			if(modelProduto.isNovo()) {
				daoproduto.gravarProduto(modelProduto);
			}else {
				daoproduto.atualizarProduto(modelProduto);
			}
			
			request.setAttribute("produtos", daoproduto.listarProdutos(sql.listaProdutosLIMIT10(id), id));
			request.setAttribute("usuario", super.getUsuarioLogado(request));
			request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(id)));
			request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(id));
			
			request.getRequestDispatcher("principal/listar.jsp").forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.getRequestDispatcher("principal/principal.jsp").forward(request, response);
		}
		
	}
}
