package dev.ershov.vd.controller;

import dev.ershov.vd.client.CreateAnswer;
import dev.ershov.vd.client.answer.tg.TgResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class Controller {

    private CreateAnswer createAnswer;

    public Controller(CreateAnswer createAnswer) {
        this.createAnswer = createAnswer;
    }

    @PostMapping("/botTgTable")
    public String server(@RequestBody TgResponse msg) throws IOException, GeneralSecurityException {
        createAnswer.reply(msg);
        return HttpStatus.OK.getReasonPhrase();
    }
}
