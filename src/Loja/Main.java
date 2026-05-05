import java.sql.*;

public class Main {
    public static void criarTabela(Connection conn) throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS produtos(" +
                    "id SERIAL PRIMARY KEY, "    +
                    "nome TEXT NOT NULL,    "   +
                    "preco REAL NOT NULL,   "  +
                    "estoque INTEGER DEFAULT 0) ";
                        //cria objetos de instrução SQL
        Statement stmt = conn.createStatement();
        stmt.execute(sql);//executar comando SQL
        stmt.close();//Fechar instrução SQL
    }
    public static void insere(Connection conn, String nome ,double preco,int estoque)throws SQLException{
        //Criar sql
        String sql= "INSERT INTO produtos (nome , preco, estoque) values (?, ?, ?)";
        //preparar uma instrução SQL
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, nome);//definir o nome do produtos
        ps.setDouble(2, preco);//definir os precos dos produtos
        ps.setInt(3, estoque);//definir estoque do produto
        ps.executeUpdate();//executa o SQl
        System.out.println("Produto Inserido comsucesso");
        ps.close();

    }
    public static void consulta(Connection conn) throws SQLException{
        //criar comando SQL
        String sql = "SELECT * FROM produtos ORDER BY nome";
        Statement stmt = conn.createStatement();
        //execultar a consulta no banco e amarzena o resuldado em rs
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            //recuperar o valor de cada coluna
            int id = rs.getInt("id");
            String nome = rs.getString("nome");
            double preco = rs.getDouble("preco");
            int estoque = rs.getInt("estoque");
            //mostra os registros da consulta
            System.out.printf("(%d)%s - R$ %.2f (estoque: %d %n)",id,nome,preco,estoque);

        }
    }
    public static void remove(Connection conn,int id) throws SQLException{
        String sql = "DELETE FROM produtos WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,id);
        int linhasAfetadas = ps.executeUpdate();
        ps.close();
        if (linhasAfetadas > 0){
            System.out.println("Produto Removido:");
        }
        else System.out.println("ID nao encontrado:");
    }
    public static void main(String[] args){
        String url = "jdbc:postgresql://localhost:5432/Loja";
        try { // tenta se conectar no banco de dados
            Connection conn = DriverManager.getConnection(
                    url, "postgres", "lgp715");
            System.out.println("Conexão com sucesso");
            //criar tabela
            criarTabela(conn);
            insere(conn ,"Memoria ram", 250,5 );
            consulta(conn);
            remove(conn);
        }
        catch (SQLException e){ // caso dê erro, desvia pra cá
            System.out.println("Erro ao conectar com o banco " + e.getMessage());
        }
    }
}