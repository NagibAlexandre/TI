import static spark.Spark.*;
import service.PokemonService;

public class Aplicacao {
	
	private static PokemonService pokemonService = new PokemonService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/pokemon/insert", (request, response) -> pokemonService.insert(request, response));

        get("/pokemon/:id", (request, response) -> pokemonService.get(request, response));
        
        get("/pokemon/list/:orderby", (request, response) -> pokemonService.getAll(request, response));

        get("/pokemon/update/:id", (request, response) -> pokemonService.getToUpdate(request, response));
        
        post("/pokemon/update/:id", (request, response) -> pokemonService.update(request, response));
           
        get("/pokemon/delete/:id", (request, response) -> pokemonService.delete(request, response));
    }
}
