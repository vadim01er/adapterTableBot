package dev.ershov.vd.client;

import dev.ershov.vd.GSheets.SheetsQuickstart;
import dev.ershov.vd.client.answer.tg.TgResponse;
import dev.ershov.vd.entities.UserTg;
import dev.ershov.vd.service.AdaptersService;
import dev.ershov.vd.service.UsersTgService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateAnswer {
    private final TgClient client;
    private final SheetsQuickstart sheetsQuickstart;
    private final UsersTgService usersTgService;
    private final AdaptersService adaptersService;

    public CreateAnswer(TgClient client, SheetsQuickstart sheetsQuickstart,
                        UsersTgService usersTgService, AdaptersService adaptersService) {
        this.client = client;
        this.sheetsQuickstart = sheetsQuickstart;
        this.usersTgService = usersTgService;
        this.adaptersService = adaptersService;
    }

    private List<String> createAnswer(List<Object> person) {
        String answer = "Имя:  " + person.get(0) + "\n" +
                "Институт:  " + person.get(1) + "\n" +
                "Номер группы:  " + person.get(2) + "\n" +
                "Ссылка vk: " + person.get(3) + "\n" +
                "Ссылка instagram: " + person.get(4) + "\n" +
                "Номер телефона:  " + person.get(5) + "\n";
        String answerEnd = "\nУдобная дата очного этапа:  " + person.get(7) + "\n" +
                "Фото: " + person.get(8);
        List<String> list = new ArrayList<>();
        String letter = (String) person.get(6);
        if (letter.length() + answer.length() + answerEnd.length() > 3000) {
            list.add(answer);
            for (int i = 0; i < letter.length(); i += 3000) {
                if (letter.length() - i < 3000) {
                    list.add(letter.substring(i));
                } else {
                    list.add(letter.substring(i, i + 3000));
                }
            }
            list.add(answerEnd);
        } else {
            list.add(answer + letter + answerEnd);
        }
        return list;
    }


    public void reply(TgResponse msg) {
        if (msg.getMessage() == null) {
            return;
        }
        String s = msg.getMessage().getText();

        int chatId = msg.getMessage().getChat().getId();
        if (s == null) {
            client.sendMessage("Можно только писать",
                    chatId);
        }
        if (s.equals("/start") || !usersTgService.existById(chatId)) {
            usersTgService.addUserTg(chatId);
            client.sendMessage("Погнали! Напиши мне свой идентификатор который дал тебе Админ!",
                    chatId);
            return;
        }
        if (s.equals("/site")) {
            client.sendMessage("https://adapter-table-bot.herokuapp.com/", chatId);
            return;
        }
        UserTg userTg = usersTgService.findById(chatId);
        List<List<Object>> adapters;
        switch (userTg.getRole()) {
            case 0: // auth
                switch (s) {
                    case "0555": //икнт
                        usersTgService.updateUserTg(chatId, 1, 0);
                        client.sendMessage("Ты прикреплен к ИКНТ напиши имя человека", chatId);
                        break;
                    case "0426":
                        usersTgService.updateUserTg(chatId, 1, 1);
                        client.sendMessage("Ты прикреплен к ИПМЭиТ напиши имя человека", chatId);
                        break;
                    case "0738":
                        usersTgService.updateUserTg(chatId, 1, 2);
                        client.sendMessage("Ты прикреплен к ИСИ напиши имя человека", chatId);
                        break;
                    case "0813":
                        usersTgService.updateUserTg(chatId, 1, 3);
                        client.sendMessage("Ты прикреплен к ГИ напиши имя человека", chatId);
                        break;
                    case "0692":
                        usersTgService.updateUserTg(chatId, 1, 4);
                        client.sendMessage("Ты прикреплен к ИММиТ напиши имя человека", chatId);
                        break;
                    case "0217":
                        usersTgService.updateUserTg(chatId, 1, 5);
                        client.sendMessage("Ты прикреплен к ИПММ напиши имя человека", chatId);
                        break;
                    case "0964":
                        usersTgService.updateUserTg(chatId, 1, 6);
                        client.sendMessage("Ты прикреплен к ИФНиТ напиши имя человека", chatId);
                        break;
                    case "0073":
                        usersTgService.updateUserTg(chatId, 1, 7);
                        client.sendMessage("Ты прикреплен к ИБСиБ напиши имя человека", chatId);
                        break;
                    case "0154":
                        usersTgService.updateUserTg(chatId, 1, 8);
                        client.sendMessage("Ты прикреплен к ИЭ напиши имя человека", chatId);
                        break;
                    case "0341":
                        usersTgService.updateUserTg(chatId, 1, 9);
                        client.sendMessage("Ты прикреплен к ИСПО напиши имя человека", chatId);
                        break;
                    case "0631":
                        usersTgService.updateUserTg(chatId, 1, 10);
                        client.sendMessage("Ты прикреплен к ИКиЗИ напиши имя человека", chatId);
                        break;
                    case "0777":
                        usersTgService.updateUserTg(chatId, 1, 100);
                        client.sendMessage("Ты прикреплен к ADMIN" +
                                "(можете получать по всем людям информацию) напиши имя человека", chatId);
                        break;
                    default:
                        client.sendMessage("Вы ввели неверный индикатор: " + s, chatId);
                }
                return;
            case 1: // search
                if (s.equals("/comment")) {
                    usersTgService.updateRole(chatId, 2);
                    return;
                }

                if (userTg.getUniversity() == 100) {
                    adapters = sheetsQuickstart.findPerson(s);
                } else {
                    adapters = sheetsQuickstart.findUniversityAndPerson(getUniversity(userTg.getUniversity()), s);
                }
                if (adapters == null || adapters.isEmpty()) {
                    client.sendMessage("Не найден: " + s, chatId);
                } else {
                    boolean isOne = true;
                    if (adapters.size() > 1) {
                        client.sendMessage(
                                "find " + adapters.size() + " adapters:",
                                chatId
                        );
                        isOne = false;
                    }
                    usersTgService.updateLastNameAndOne(chatId, s, isOne);
                    for (int i = 0; i < adapters.size(); i++) {
                        List<String> answer = createAnswer(adapters.get(i));
                        if (!isOne)
                            answer.set(0, "" + (i + 1) + ". " + answer.get(0));
                        for (String value : answer)
                            client.sendMessage(value, chatId);
                    }
                    if (isOne) {
                        client.sendMessage(
                                "Вы можете добавить комментарий к нему. Просто нажмите на \"/comment\" " +
                                        "и следующем сообщением напишите свой комментарий",
                                chatId
                        );
                    } else {
                        client.sendMessage(
                                "Вы можете добавить комментарий к нему. Просто нажмите на " +
                                        "\"/comment\" и первым словом введите номер человека",
                                chatId
                        );
                    }
                }
                return;
            case 2: // comment
                UserTg byId = usersTgService.findById(chatId);
                if (!byId.getLastName().equals("null")) {
                    if (byId.isOne()) {
                        adaptersService.updateComment(
                                (String) sheetsQuickstart.findPerson(byId.getLastName()).get(0).get(0),
                                s
                        );
                        client.sendMessage("Комментарий добавлен к " + byId.getLastName(), chatId);
                    } else {
                        if (userTg.getUniversity() == 100) {
                            adapters = sheetsQuickstart.findPerson(byId.getLastName());
                        } else {
                            adapters = sheetsQuickstart.findUniversityAndPerson(
                                    getUniversity(userTg.getUniversity()),
                                    byId.getLastName()
                            );
                        }
                        String s1 = s.split(" ")[0];
                        int i;
                        try {
                            i = Integer.parseInt(s1);
                        } catch (NumberFormatException e) {
                            client.sendMessage("Первым словом твоего комментария должен быть номер человека",
                                    chatId);
                            return;
                        }
                        if (i <= adapters.size() && i > 0) {
                            String o = (String) adapters.get(i - 1).get(0);
                            try {
                                String substring = s.substring(s1.length() + 1);
                                adaptersService.updateComment(o, substring);
                                client.sendMessage("Комментарий добавлен к " + o, chatId);
                            } catch (IndexOutOfBoundsException e) {
                                client.sendMessage("Первым словом твоего комментария должен быть номер человека!",
                                        chatId);
                            }
                        } else {
                            client.sendMessage("Твоё число не в диапазоне, попробуй еще раз", chatId);
                            return;
                        }
                    }
                } else {
                    client.sendMessage("Ты еще никого не вводил", chatId);
                }
                usersTgService.updateRole(chatId, 1);
                break;
        }
    }

    private String getUniversity(int num) {
        switch (num) {
            case 0:
                return "ИКНТ";
            case 1:
                return "ИПМЭиТ";
            case 2:
                return "ИСИ";
            case 3:
                return "ГИ";
            case 4:
                return "ИММиТ";
            case 5:
                return "ИПММ";
            case 6:
                return "ИФНиТ";
            case 7:
                return "ИБСиБ";
            case 8:
                return "ИЭ";
            case 9:
                return "ИСПО";
            case 10:
                return "ИКиЗИ";
            default:
                return "All";
        }
    }
}
