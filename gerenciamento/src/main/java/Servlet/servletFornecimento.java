package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import DAO.DaoGenerico;
import DAO.daoEntradasRelatorio;
import DAO.daoFornecimento;
import DAO.daoLogin;
import DAO.daoPedidos;
import DAO.daoProdutos;
import Servlet.SQL.SQL;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelData;
import model.ModelFornecimento;
import model.ModelPedidos;
import model.ModelProdutos;

/**
 * Servlet implementation class servletFornecimento
 */
public class servletFornecimento extends servlet_recuperacao_login {
	private static final long serialVersionUID = 1L;
	
	daoLogin daologin = new daoLogin();
	daoFornecimento daofornecimento = new daoFornecimento();
	daoProdutos daoproduto = new daoProdutos();
	daoPedidos pedido = new daoPedidos();
	daoPedidos daopedidos = new daoPedidos();
	daoEntradasRelatorio daoEntradaRelatorio = new daoEntradasRelatorio();
	DaoGenerico dao = new DaoGenerico();
	SQL sql = new SQL();
       
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
			int id = super.getUsuarioLogado(request).getId();
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("incluirPedido")) {
				Long id_Produto = Long.parseLong(request.getParameter("idProduto"));
				String fornecimento_pai_id = request.getParameter("fornecimento_pai_id");
				String quantidade = request.getParameter("quantidade");
				String dataPedido = request.getParameter("dataPedido");
				Long fornecimento_pai_id_int = Long.parseLong(fornecimento_pai_id);
				
				dataPedido = dataPedido.replaceAll("\\/", "-");

				ModelFornecimento fornecedor = daofornecimento.consultaFornecedor(fornecimento_pai_id_int, id_Produto, id);

				LocalDate data = LocalDate.parse(dataPedido, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				data = data.plusDays(fornecedor.getTempoentrega());

				ModelPedidos modelPedidos = new ModelPedidos();
				modelPedidos.setFornecedor_pai_id(fornecedor);
				modelPedidos.setQuantidade(Long.parseLong(dao.tirarPonto(quantidade)));
				modelPedidos.setDatapedido(dataPedido);
				modelPedidos.setDataentrega(data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				modelPedidos.setValor(fornecedor.getValor());
				modelPedidos.setNome(daoproduto.consultaProduto(id_Produto, id).getNome());
				modelPedidos.setUsuario_pai_id(super.getUsuarioLogado(request));

				pedido.gravarPedido(modelPedidos);

				request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(id));
				request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(id)));
				request.setAttribute("produtos", daoproduto.listarProdutos(sql.listaProdutosLIMIT10(id), id));
					
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("confirmarPedido")){
				String quantidade = request.getParameter("quantidade");
				String data = request.getParameter("data");
				int status = 2;
				
				ModelData modelData = new ModelData();
				modelData.setDatavenda(data);
				modelData.setUsuario_pai_id(super.getUsuarioLogado(request));

				modelData.setValortotal(daopedidos.listarPedidos(sql.procuraPedido(Long.parseLong(request.getParameter("id")))).get(0).getValorTotal());
				boolean booleana = daoEntradaRelatorio.buscarData(modelData);

				if (booleana) {
					daoEntradaRelatorio.atualizarDataEValor(modelData);
				} else {
					daoEntradaRelatorio.inserirDataEValor(modelData);
				}

				daoproduto.adicionaProdutoCaixa(Long.parseLong(request.getParameter("id_produto")), Integer.parseInt(quantidade));
				daopedidos.mudarStatus(Long.parseLong(request.getParameter("id")), status);
					
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("cancelarPedido")){
				Long id_pedido = Long.parseLong(request.getParameter("id"));
			}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarFornecedor")){
				String id_fornecedor = request.getParameter("id");
				daofornecimento.excluirFornecedor(id_fornecedor);
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
			int id = super.getUsuarioLogado(request).getId();
			String nomeFornecedor = request.getParameter("nomeFornecedor");
			String tempoentrega = request.getParameter("tempoentrega");
			
			ModelProdutos modelProdutos = new ModelProdutos();
			modelProdutos.setId(request.getParameter("id") != null && !request.getParameter("id").isEmpty() ? Long.parseLong(request.getParameter("id")) : null);
			
			new daoFornecimento().gravarNovoFornecedor(nomeFornecedor, modelProdutos, Integer.parseInt(tempoentrega), dao.converterDinheiroInteger(request.getParameter("valor")));
			
			request.setAttribute("totalPagina", daoproduto.consultaProdutosPaginas(this.getUsuarioLogado(request).getId()));
			request.setAttribute("soma", dao.converterIntegerDinheiro(daoproduto.somaProdutos(id)));
			request.setAttribute("produtos", daoproduto.listarProdutos(sql.listaProdutosLIMIT10(id), id));
			
			RequestDispatcher despache = request.getRequestDispatcher("principal/listar.jsp");
			despache.forward(request, response);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
