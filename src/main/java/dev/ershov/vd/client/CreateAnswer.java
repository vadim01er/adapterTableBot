package dev.ershov.vd.client;

import dev.ershov.vd.GSheets.SheetsQuickstart;
import dev.ershov.vd.client.answer.tg.TgResponse;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
                "Фото:\n";
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
                File file = new File("C:\\Users\\Vadim\\IdeaProjects\\adapterTableBot\\src\\main\\resources\\static\\001.jpg");
                InputStream input = new FileInputStream(file);
                client.sendPhoto(input, "001.jpg", msg.getMessage().getChat().getId());
            }
        }
    }
}
