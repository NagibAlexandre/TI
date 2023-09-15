import model.Pokemon;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAO extends DAO {
    public DAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public boolean insert(Pokemon pokemon) {
        boolean status = false;
        try {
            String sql = "INSERT INTO pokemon (nome, tipo, nivel) VALUES (?, ?, ?)";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, pokemon.getNome());
            st.setString(2, pokemon.getTipo());
            st.setInt(3, pokemon.getNivel());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public Pokemon get(int id) {
        Pokemon pokemon = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM pokemon WHERE id=" + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                pokemon = new Pokemon(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("tipo"),
                    rs.getInt("nivel")
                );
            }
            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return pokemon;
    }

    public List<Pokemon> getAll() {
        List<Pokemon> pokemons = new ArrayList<>();

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM pokemon";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Pokemon pokemon = new Pokemon(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("tipo"),
                    rs.getInt("nivel")
                );
                pokemons.add(pokemon);
            }
            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return pokemons;
    }

    public boolean update(Pokemon pokemon) {
        boolean status = false;
        try {
            String sql = "UPDATE pokemon SET nome = ?, tipo = ?, nivel = ? WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, pokemon.getNome());
            st.setString(2, pokemon.getTipo());
            st.setInt(3, pokemon.getNivel());
            st.setInt(4, pokemon.getId());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public boolean delete(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM pokemon WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }
}
