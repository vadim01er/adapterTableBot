package dev.ershov.vd.client;

import dev.ershov.vd.GSheets.SheetsQuickstart;
import dev.ershov.vd.client.answer.tg.TgResponse;
import dev.ershov.vd.entities.Admin;
import dev.ershov.vd.service.AdminsService;
import dev.ershov.vd.service.UsersService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Component
public class CreateAnswer {
    private final TgClient client;
    private final SheetsQuickstart sheetsQuickstart;
    private final AdminsService adminsService;
    private final UsersService usersService;

    public CreateAnswer(TgClient client, SheetsQuickstart sheetsQuickstart, AdminsService adminsService, UsersService usersService) {
        this.client = client;
        this.sheetsQuickstart = sheetsQuickstart;
        this.adminsService = adminsService;
        this.usersService = usersService;
    }

    private String createAnswer(List<Object> person) {
        return "Имя:  " + person.get(0) + "\n" +
                "Институт:  " + person.get(1) + "\n" +
                "Номер группы:  " + person.get(2) + "\n" +
                "Ссылка vk: " + person.get(3) + "\n" +
                "Ссылка instagram: " + person.get(4) + "\n" +
                "Номер телефона:  " + person.get(5) + "\n" +
                "Мотивационное письмо:  " + person.get(6) + "\n" +
                "Удобная дата очного этапа:  " + person.get(7) + "\n" +
                "Фото: " + person.get(8);
    }


    public void reply(TgResponse msg) throws IOException, GeneralSecurityException {
        String s = msg.getMessage().getText();
        int chatId = msg.getMessage().getChat().getId();
        if (s.startsWith("/")) {
            if (s.equals("/start")) {
                adminsService.addAdmin(chatId);
                client.sendMessage("Погнали! Напиши мне ФИО или ФИ или Ф или И и получишь этого человека",
                        chatId);
            } else if (s.split(" ")[0].equals("/c") || s.split(" ")[0].equals("/с")) {
                Admin byId = adminsService.findById(chatId);
                if (!byId.getLastName().equals("null")) {
                    usersService.updateComment(byId.getLastName(), s.substring(3));
                    client.sendMessage("Комментарий добавлен к " + byId.getLastName(), chatId);
                } else {
                    client.sendMessage("Ты еще никого не вводил", chatId);
                }
            } else if (s.equals("/site")) {
                client.sendMessage("Вот сайт: https://adapter-table-bot.herokuapp.com/", chatId);
            }
        } else {
            List<List<Object>> persons = sheetsQuickstart.findPerson(s);
            if (persons == null || persons.isEmpty()) {
                client.sendMessage("Не найден: " + s, chatId);
            } else {
                adminsService.updateLastName(chatId, s);
                if (persons.size() > 1) {
                    client.sendMessage(
                            "find " + persons.size() + " persons:",
                            chatId
                    );
                }
                for (List<Object> objects : persons) {
                    String answer = createAnswer(objects);
                    client.sendMessage(answer, chatId);
                }
                client.sendMessage(
                        "Вы можете добавить комментарий к нему. Просто напишите: \"/c *ваш комментарий*\"",
                        chatId
                );
            }
        }
    }
}
