import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class App {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
		var client = HttpClient.newHttpClient();
		var endereco = URI.create(url);
		var request = HttpRequest.newBuilder(endereco).GET().build();
		var response = client.send(request, BodyHandlers.ofString());
		String body = response.body();
		
		var parser = new JsonParser();
		List<Map<String, String>>listaDeFilmes = parser.parse(body);
		
		for (Map<String, String> filme : listaDeFilmes) {
			System.out.println("\033[32;1mTitulo: \u001b[m"+filme.get("title"));
			double classificação = Double.parseDouble(filme.get("imDbRating"));
			int estrelas=(int) classificação;
			for (int i=1;i<=estrelas;i++){
				if (i==1){
					System.out.print("\033[32;1mRating: \u001b[m("+filme.get("imDbRating")+")⭐");
				} else{
					System.out.print("⭐");
				}
			}
			System.out.println("\n\033[32;1mImagem: \u001b[m"+filme.get("image"));
			System.out.println("\n");
		}
	}
}