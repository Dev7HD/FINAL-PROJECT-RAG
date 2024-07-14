package ma.dev7hd.finalexamrag.web;

import lombok.AllArgsConstructor;
import ma.dev7hd.finalexamrag.services.ChatBotAiService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/chatbot", produces = MediaType.TEXT_PLAIN_VALUE)
@AllArgsConstructor
public class ChatBotAiController {
    private ChatBotAiService chatBotAiService;

    @GetMapping("/ask")
    public String userAsk(String message) {
        return chatBotAiService.ragChat(message);
    }
}
