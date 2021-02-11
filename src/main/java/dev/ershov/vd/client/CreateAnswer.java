package dev.ershov.vd.client;

import dev.ershov.vd.GSheets.SheetsQuickstart;
import dev.ershov.vd.client.answer.tg.TgResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Component
public class CreateAnswer {
    private final TgClient client;
    private final SheetsQuickstart sheetsQuickstart;

    public CreateAnswer(TgClient client, SheetsQuickstart sheetsQuickstart) {
        this.client = client;
        this.sheetsQuickstart = sheetsQuickstart;
    }

    private String createAnswer(List<Object> person) {
        return "Имя: " + person.get(0) + "\n" +
                "Институт: " + person.get(1) + "\n" +
                "Номер группы: " + person.get(2) + "\n" +
                "Ссылка vk: " + person.get(3) + "\n" +
                "Ссылка instagram: " + person.get(4) + "\n" +
                "Номер телефона: " + person.get(5) + "\n" +
                "Мотивационное письмо: " + person.get(6) + "\n" +
                "Удобная дата очного этапа: " + person.get(7) + "\n" +
                "Фото: " + person.get(8) + "\n";
    }

    public void reply(TgResponse msg) throws IOException, GeneralSecurityException {
        String s = msg.getMessage().getText();
        List<List<Object>> persons = sheetsQuickstart.findPerson(s);
        if (persons == null || persons.isEmpty()) {
            client.sendMessage("Не найден: " + s, msg.getMessage().getChat().getId());
        } else {
            if (persons.size() > 1) {
                client.sendMessage("find " + persons.size() + " persons:", msg.getMessage().getChat().getId());
            }
            for (List<Object> objects : persons) {
                client.sendMessage(createAnswer(objects), msg.getMessage().getChat().getId());
            }
        }
    }
}
