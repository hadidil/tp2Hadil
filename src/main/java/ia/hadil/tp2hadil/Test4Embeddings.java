package ia.hadil.tp2hadil;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
//import dev.langchain4j.model.embedding.onnx.bgesmallenv15.BgeSmallEnV15EmbeddingModel;
import dev.langchain4j.model.google.genai.GoogleGenAiEmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;

import java.time.Duration;

public class Test4Embeddings {

    public static void main(String[] args) {
        String[][] couples = {
                {"Le chat dort sur le canapé.", "Un chat est endormi sur le sofa."},
                {"Il fait très chaud aujourd'hui.", "Il fait très froid aujourd'hui."},
                {"J'aime beaucoup les pommes.", "Je déteste les pommes."},
                {"La voiture est rapide.", "Le véhicule roule à grande vitesse."},
                {"Je programme en Java.", "Les bananes sont jaunes."},
                {"Paris est la capitale de la France.", "La capitale de la France est Paris."}
        };

//        EmbeddingModel modeleBge = new BgeSmallEnV15EmbeddingModel();

        EmbeddingModel modeleGoogle = GoogleGenAiEmbeddingModel.builder()
                .apiKey(System.getenv("GEMINI_KEY"))
                .modelName("gemini-embedding-001")
                .taskType(GoogleGenAiEmbeddingModel.TaskTypeEnum.SEMANTIC_SIMILARITY)
                .timeout(Duration.ofSeconds(10))
                .build();

//        afficher("Modèle BGE", modeleBge, couples);
        afficher("Modèle Google", modeleGoogle, couples);
    }

    static void afficher(String nom, EmbeddingModel modele, String[][] couples) {
        System.out.println(nom);
        for (String[] couple : couples) {
            Embedding e1 = modele.embed(couple[0]).content();
            Embedding e2 = modele.embed(couple[1]).content();
            double similarite = CosineSimilarity.between(e1, e2);
            System.out.println(couple[0] + " | " + couple[1] + " -> " + similarite);
        }
    }
}