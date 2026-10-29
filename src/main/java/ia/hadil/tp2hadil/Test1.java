package ia.hadil.tp2hadil;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;

public class Test1 {

    public static void main(String[] args) {
        String cle = System.getenv("GEMINI_KEY");

        ChatModel modele = GoogleGenAiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .build();

        // Test 1 : question simple
        System.out.println("Question simple : " + modele.chat("Traduis 'bonjour' en anglais"));

        // Test 2 : l'heure
        System.out.println("Heure : " + modele.chat("Quelle heure est-il ?"));

        // Test 3 : le nom, avec deux appels indépendants
        System.out.println("Réponse 1 : " + modele.chat("Bonjour, je m'appelle Hadil Maghnaoui."));
        System.out.println("Réponse 2 : " + modele.chat("Comment je m'appelle ?"));

    }
}