import javax.swing.JOptionPane;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

	public static void main(String[] args) throws Exception {
		
		String[] opc = {"Stickers de Series", "Seu Sticker", "Sair"};
		var Generator = new GeradorDeFigurinhas();
		while (true){

			char rep=0;
			int selectedOpc = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Generator de Stickers", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opc, opc[0]);

			switch (selectedOpc){
				case 0:
					String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
					var client = HttpClient.newHttpClient();
					var address = URI.create(url);
					var request = HttpRequest.newBuilder(address).GET().build();
					var response = client.send(request, BodyHandlers.ofString());
					String body = response.body();
					
					var parser = new JsonParser();
					List<Map<String, String>>listOfMovies = parser.parse(body);

					for (Map<String, String> film : listOfMovies) {
						double classification = Double.parseDouble(film.get("imDbRating"));
						String txt01="";
						if (classification>=8.00){
							txt01 = "Otimo Filme!!!";
						} else if(classification>=6.00 && classification<8.00){
							txt01 = "Bom Filme!!";
						} else{
							txt01 = "Péssimo Filme!";
						}
						String fileName = film.get("title").replace(":", "-");
						Generator.cria(film.get("image"), txt01, fileName, false);
					}
					break;

				case 1:
					String urlImg=JOptionPane.showInputDialog(null, "Insira a url da imagem: ");
					String txt02=JOptionPane.showInputDialog(null, "Insira o texto da figurinha: ");
					Generator.cria(urlImg, txt02, "figurinha", true);
					break;

				case 2:
					rep=1;
					break;
			}
			if (rep==1) {
				break;
			}
		}
	}
}