package ia.hadil.tp2hadil;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.model.output.TokenUsage;

public class Test2Couts {

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");

        ChatModel modele = GoogleGenAiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

        ChatResponse reponse = modele.chat(UserMessage.from("Je dois partir dans 2 jours à Paris ; j'y resterai 3 jours. Est-ce que je dois mettre un parapluie dans mes valises ?"));

        System.out.println("Réponse : " + reponse.aiMessage().text());

        TokenUsage usage = reponse.tokenUsage();
        System.out.println("Tokens d'entrée : " + usage.inputTokenCount());
        System.out.println("Tokens de sortie : " + usage.outputTokenCount());
        System.out.println("Total : " + usage.totalTokenCount());

        double prixEntree = 0.30;
        double prixSortie = 2.50;
        double cout = usage.inputTokenCount() / 1_000_000.0 * prixEntree
                + usage.outputTokenCount() / 1_000_000.0 * prixSortie;
        System.out.println("Coût en dollars : " + cout);
        System.out.println("Requêtes pour 1 dollar : " + (1 / cout));
    }
}