package ia.hadil.tp2hadil;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.service.AiServices;

import java.util.Scanner;

public class Test7Outils {

    public static void main(String[] args) {
        ChatModel modele = GoogleGenAiChatModel.builder()
                .apiKey(System.getenv("GEMINI_KEY"))
                .modelName("gemini-3.5-flash-lite")
                .temperature(0.7)
                .logRequestsAndResponses(true)
                .build();
        AssistantMeteo assistant = AiServices.builder(AssistantMeteo.class)
                .chatModel(modele)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(new MeteoTool())
                .build();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Question (tapez 'fin' pour quitter) : ");
            String question = scanner.nextLine();
            if (question.equalsIgnoreCase("fin")) {
                break;
            }
            System.out.println(assistant.chat(question));
        }
    }
}