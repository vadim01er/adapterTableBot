package dev.ershov.vd.controller;

import dev.ershov.vd.GSheets.SheetsQuickstart;
import dev.ershov.vd.client.CreateAnswer;
import dev.ershov.vd.client.answer.tg.TgResponse;
import dev.ershov.vd.entities.Adapter;
import dev.ershov.vd.entities.User;
import dev.ershov.vd.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    private final CreateAnswer createAnswer;
    private final UsersService usersService;
    private final SheetsQuickstart sheetsQuickstart;

    public Controller(CreateAnswer createAnswer, UsersService usersService, SheetsQuickstart sheetsQuickstart) {
        this.createAnswer = createAnswer;
        this.usersService = usersService;
        this.sheetsQuickstart = sheetsQuickstart;
    }

    @PostMapping("/botTgTable")
    public String server(@RequestBody TgResponse msg) throws IOException, GeneralSecurityException {
        createAnswer.reply(msg);
        return HttpStatus.OK.getReasonPhrase();
    }

    @GetMapping("/")
    public ModelAndView home(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> all = sheetsQuickstart.getAll();
        if (all != null && !all.isEmpty()) {
            List<Adapter> adapters = getAdapters(all);
            model.put("listAdapter", adapters);
        }
        model.put("test", "");
        return new ModelAndView("index", model);
    }

    @GetMapping("/search")
    public ModelAndView search(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> person = sheetsQuickstart.findPerson(keyword);
        if (person != null && !person.isEmpty()) {
            List<Adapter> adapters = getAdapters(person);
            model.put("listAdapter", adapters);
        }
        model.put("search", keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/iknt")
    public ModelAndView iknt(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.getUniversity("ИКНТ");
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIknt);
            model.put("listAdapter", adapters);
        }
        model.put("test", "iknt/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/iknt/search")
    public ModelAndView ikntSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.getPersonAndUniversity("ИКНТ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIknt);
            model.put("listAdapter", adapters);
        }
        model.put("search", "ИКНТ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ipmt")
    public ModelAndView ipmt(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИПМЭиТ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIpmt);
            model.put("listAdapter", adapters);
        }
        model.put("test", "ipmt/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ipmt/search")
    public ModelAndView ipmtSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.getPersonAndUniversity("ИПМЭиТ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIknt);
            model.put("listAdapter", adapters);
        }
        model.put("search", "ИПМЭиТ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ici")
    public ModelAndView ici(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИСИ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIpmt);
            model.put("listAdapter", adapters);
        }
        model.put("test", "ici/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ici/search")
    public ModelAndView iciSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.getPersonAndUniversity("ИСИ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIknt);
            model.put("listAdapter", adapters);
        }
        model.put("search", "ИСИ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/gi")
    public ModelAndView gi(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ГИ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIpmt);
            model.put("listAdapter", adapters);
        }
        model.put("test", "gi/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/gi/search")
    public ModelAndView giSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.getPersonAndUniversity("ГИ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIknt);
            model.put("listAdapter", adapters);
        }
        model.put("search", "ГИ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/immit")
    public ModelAndView immit(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИММиТ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIpmt);
            model.put("listAdapter", adapters);
        }
        model.put("test", "immit/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/immit/search")
    public ModelAndView immitSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.getPersonAndUniversity("ИММиТ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIknt);
            model.put("listAdapter", adapters);
        }
        model.put("search", "ИММиТ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ipmm")
    public ModelAndView ipmm(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИПММ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIpmt);
            model.put("listAdapter", adapters);
        }
        model.put("test", "ipmm/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ipmm/search")
    public ModelAndView ipmmSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.getPersonAndUniversity("ИПММ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIknt);
            model.put("listAdapter", adapters);
        }
        model.put("search", "ИПММ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ifnit")
    public ModelAndView ifnit(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИФНиТ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIpmt);
            model.put("listAdapter", adapters);
        }
        model.put("test", "ifnit/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ifnit/search")
    public ModelAndView ifnitSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.getPersonAndUniversity("ИФНиТ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIknt);
            model.put("listAdapter", adapters);
        }
        model.put("search", "ИФНиТ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ibcib")
    public ModelAndView ibcib(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИБСИБ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIpmt);
            model.put("listAdapter", adapters);
        }
        model.put("test", "ibcib/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ibcib/search")
    public ModelAndView ibcibSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.getPersonAndUniversity("ИБСИБ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIknt);
            model.put("listAdapter", adapters);
        }
        model.put("search", "ИБСИБ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ia")
    public ModelAndView ia(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИЭ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIpmt);
            model.put("listAdapter", adapters);
        }
        model.put("test", "ia/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ia/search")
    public ModelAndView iaSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.getPersonAndUniversity("ИЭ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIknt);
            model.put("listAdapter", adapters);
        }
        model.put("search", "ИЭ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/icpo")
    public ModelAndView icpo(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИСПО");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIpmt);
            model.put("listAdapter", adapters);
        }
        model.put("test", "icpo/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/icpo/search")
    public ModelAndView icpoSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.getPersonAndUniversity("ИСПО", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<Adapter> adapters = getAdapters(onlyIknt);
            model.put("listAdapter", adapters);
        }
        model.put("search", "ИСПО: " + keyword);
        return new ModelAndView("search", model);
    }

    private List<Adapter> getAdapters( List<List<Object>> all) {
        List<Adapter> adapters = new ArrayList<>(all.size());
        for (List<Object> one : all) {
            Adapter adapter = new Adapter((String) one.get(0), (String) one.get(1), (String) one.get(2), (String) one.get(3),
                    (String) one.get(4), (String) one.get(5), (String) one.get(6), (String) one.get(7), (String) one.get(8));
            User byName = usersService.getByName((String) one.get(0));
            if (byName != null) {
                adapter.setComment(byName.getComment());
            } else {
                adapter.setComment("No have comment");
            }
            adapters.add(adapter);
        }
        return adapters;
    }

}
