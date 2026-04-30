import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Loja";
        try {//Tentar se conectar com o bando de Dados
            Connection conn = DriverManager.getConnection(
                    url, "postgres", "lgp715");
            System.out.printf("Conexão feita com sucesso");
        }
        catch (SQLException e){//caso Dê erro, desviar para ca"
            System.out.println("Erro ao se conectar com o banco de dados"+e.getMessage());

        }

    }
}