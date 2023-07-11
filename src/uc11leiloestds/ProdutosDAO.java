
package uc11leiloestds;


import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
      
        
      conn = new conectaDAO().connectDB();
      String sql = ("INSERT INTO produtos(nome, valor, status) VALUES(?,?,?)");
        try {

            prep = this.conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.execute();

        } catch (Exception e) {
            System.out.println("Erro ao cadastar: " + e.getMessage());
        }
   
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
    try {
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos";
        prep = conn.prepareStatement(sql);
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));
            listagem.add(produto);
        }

    } catch (Exception e) {
        System.out.println("Erro ao listar produtos: " + e.getMessage());
    } finally {
    }

    return listagem;
}
    
    public void venderProduto(int id) {
    conn = new conectaDAO().connectDB();
    String sql = "UPDATE produtos SET status = ? WHERE id = ?";

    try {
        prep = conn.prepareStatement(sql);
        prep.setString(1, "Vendido");
        prep.setInt(2, id);
        prep.executeUpdate();
        System.out.println("Produto vendido com sucesso!");
    } catch (Exception e) {
        System.out.println("Erro ao vender produto: " + e.getMessage());
    } finally {
     
    }
}
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
    
    ArrayList<ProdutosDTO> produtosVendidos = new ArrayList<>();
    conn = new conectaDAO().connectDB();
    String sql = "SELECT * FROM produtos WHERE status = ?";
  
    try {
        prep = conn.prepareStatement(sql);
        prep.setString(1, "Vendido");
        resultset = prep.executeQuery();

        while (resultset.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(resultset.getInt("id"));
            produto.setNome(resultset.getString("nome"));
            produto.setValor(resultset.getInt("valor"));
            produto.setStatus(resultset.getString("status"));
            produtosVendidos.add(produto);
        }
    } catch (Exception e) {
        System.out.println("Erro ao listar produtos vendidos: " + e.getMessage());
    } finally {
        
    }
  
    return produtosVendidos;
}

}
