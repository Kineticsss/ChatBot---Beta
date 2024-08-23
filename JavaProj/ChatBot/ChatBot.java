package ChatBot;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChatBot {
    private Map<String, String> responses;

    public ChatBot() {
        responses = new HashMap<>();
        responses.put("Sup?", "Meh, you?");
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chatbot started! Type 'quit' to exit.");

        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("quit")) {
                System.out.println("Goodbye!");
                break;
            }

            String response = getResponse(userInput);
            if (response != null) {
                System.out.println("Chatbot: " + response);
            } else {
                System.out.println("Chatbot: Sorry, I didn't understand that.");
            }
        }

        scanner.close();
    }

    public String getResponse(String input) {
        return responses.get(input);
    }

    public static void main(String[] args) {
        ChatBot chatBot = new ChatBot();
        chatBot.start();

        String userInput = "Sup";
        String response = chatBot.getResponse(userInput);
        System.out.println(response);
    }
}