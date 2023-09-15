import dao.DAO;
import model.Pokemon;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemonService {

    private DAO pokemonDAO = new DAO();

    public PokemonService() {
        setupRoutes();
    }

    private void setupRoutes() {
        Spark.staticFileLocation("/public");
        Spark.port(8080);

        Spark.get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Pokemon> allPokemon = pokemonDAO.getAll();
            model.put("pokemonList", allPokemon);
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, "templates/index.vm")
            );
        });

        Spark.get("/pokemon/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(request.params(":id"));
            Pokemon pokemon = pokemonDAO.get(id);
            model.put("pokemon", pokemon);
            return new VelocityTemplateEngine().render(
                    new ModelAndView(model, "templates/detail.vm")
            );
        });

        Spark.post("/pokemon", (request, response) -> {
            String name = request.queryParams("name");
            if (name != null && !name.isEmpty()) {
                Pokemon newPokemon = new Pokemon(name);
                pokemonDAO.insert(newPokemon);
            }
            response.redirect("/");
            return null;
        });

        Spark.post("/pokemon/:id/update", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Pokemon pokemon = pokemonDAO.get(id);
            if (pokemon != null) {
                String newName = request.queryParams("newName");
                if (newName != null && !newName.isEmpty()) {
                    pokemon.setName(newName);
                    pokemonDAO.update(pokemon);
                }
            }
            response.redirect("/");
            return null;
        });

        Spark.get("/pokemon/:id/delete", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            Pokemon pokemon = pokemonDAO.get(id);
            if (pokemon != null) {
                pokemonDAO.delete(id);
            }
            response.redirect("/");
            return null;
        });
    }

    public static void main(String[] args) {
        new PokemonService();
    }
}
