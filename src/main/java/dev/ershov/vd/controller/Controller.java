package dev.ershov.vd.controller;

import dev.ershov.vd.GSheets.SheetsQuickstart;
import dev.ershov.vd.client.CreateAnswer;
import dev.ershov.vd.client.answer.tg.TgResponse;
import dev.ershov.vd.entities.AdapterSheet;
import dev.ershov.vd.entities.Adapter;
import dev.ershov.vd.service.AdaptersService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    private final CreateAnswer createAnswer;
    private final AdaptersService adaptersService;
    private final SheetsQuickstart sheetsQuickstart;

    public Controller(CreateAnswer createAnswer, AdaptersService adaptersService, SheetsQuickstart sheetsQuickstart) {
        this.createAnswer = createAnswer;
        this.adaptersService = adaptersService;
        this.sheetsQuickstart = sheetsQuickstart;
    }

    @PostMapping("/botTgTable")
    public String server(@RequestBody TgResponse msg) throws IOException, GeneralSecurityException {
        createAnswer.reply(msg);
        return HttpStatus.OK.getReasonPhrase();
    }

    @GetMapping("/")
    public ModelAndView home(HttpServletRequest httpServletRequest, Map<String, Object> model) throws IOException, GeneralSecurityException {
        if (httpServletRequest.isUserInRole("ADMIN")) {
            return admin(model);
        } else if (httpServletRequest.isUserInRole("iknt")) {
            return iknt(model);
        } else if (httpServletRequest.isUserInRole("ipmt")) {
            return ipmt(model);
        } else if (httpServletRequest.isUserInRole("ici")) {
            return ici(model);
        } else if (httpServletRequest.isUserInRole("gi")) {
            return gi(model);
        } else if (httpServletRequest.isUserInRole("immit")) {
            return immit(model);
        } else if (httpServletRequest.isUserInRole("ipmm")) {
            return ipmm(model);
        } else if (httpServletRequest.isUserInRole("ifnit")) {
            return ifnit(model);
        } else if (httpServletRequest.isUserInRole("ibcib")) {
            return ibcib(model);
        } else if (httpServletRequest.isUserInRole("ia")) {
            return ia(model);
        } else if (httpServletRequest.isUserInRole("ikizi")) {
            return ikizi(model);
        } else {
            return icpo(model);
        }
    }

    public ModelAndView admin(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> all = sheetsQuickstart.getAll();
        if (all != null && !all.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(all);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "");
        model.put("isAdmin", "ADMIN");
        return new ModelAndView("index", model);
    }

    @GetMapping("/search")
    public ModelAndView search(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> person = sheetsQuickstart.findPerson(keyword);
        if (person != null && !person.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(person);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/iknt")
    public ModelAndView iknt(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.getUniversity("ИКНТ");
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIknt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "iknt/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/iknt/search")
    public ModelAndView ikntSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.findUniversityAndPerson("ИКНТ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIknt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", "ИКНТ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ipmt")
    public ModelAndView ipmt(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИПМЭиТ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIpmt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "ipmt/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ipmt/search")
    public ModelAndView ipmtSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.findUniversityAndPerson("ИПМЭиТ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIknt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", "ИПМЭиТ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ici")
    public ModelAndView ici(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИСИ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIpmt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "ici/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ici/search")
    public ModelAndView iciSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.findUniversityAndPerson("ИСИ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIknt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", "ИСИ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/gi")
    public ModelAndView gi(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ГИ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIpmt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "gi/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/gi/search")
    public ModelAndView giSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.findUniversityAndPerson("ГИ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIknt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", "ГИ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/immit")
    public ModelAndView immit(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИММиТ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIpmt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "immit/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/immit/search")
    public ModelAndView immitSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.findUniversityAndPerson("ИММиТ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIknt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", "ИММиТ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ipmm")
    public ModelAndView ipmm(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИПММ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIpmt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "ipmm/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ipmm/search")
    public ModelAndView ipmmSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.findUniversityAndPerson("ИПММ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIknt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", "ИПММ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ifnit")
    public ModelAndView ifnit(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИФНиТ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIpmt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "ifnit/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ifnit/search")
    public ModelAndView ifnitSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.findUniversityAndPerson("ИФНиТ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIknt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", "ИФНиТ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ibcib")
    public ModelAndView ibcib(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИБСиБ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIpmt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "ibcib/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ibcib/search")
    public ModelAndView ibcibSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.findUniversityAndPerson("ИБСиБ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIknt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", "ИБСИБ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ia")
    public ModelAndView ia(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИЭ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIpmt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "ia/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ia/search")
    public ModelAndView iaSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.findUniversityAndPerson("ИЭ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIknt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", "ИЭ: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/icpo")
    public ModelAndView icpo(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИСПО");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIpmt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "icpo/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/icpo/search")
    public ModelAndView icpoSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.findUniversityAndPerson("ИСПО", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIknt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", "ИСПО: " + keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/ikizi")
    public ModelAndView ikizi(Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIpmt = sheetsQuickstart.getUniversity("ИКиЗИ");
        if (onlyIpmt != null && !onlyIpmt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIpmt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "ikizi/");
        return new ModelAndView("index", model);
    }

    @GetMapping("/ikizi/search")
    public ModelAndView ikiziSearch(String keyword, Map<String, Object> model) throws IOException, GeneralSecurityException {
        List<List<Object>> onlyIknt = sheetsQuickstart.findUniversityAndPerson("ИКиЗИ", keyword);
        if (onlyIknt != null && !onlyIknt.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyIknt);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", "ИКиЗИ: " + keyword);
        return new ModelAndView("search", model);
    }

    private List<AdapterSheet> getAdapters(List<List<Object>> all) {
        List<AdapterSheet> adapterSheets = new ArrayList<>(all.size());
        for (List<Object> one : all) {
            AdapterSheet adapterSheet = new AdapterSheet((String) one.get(0), (String) one.get(1), (String) one.get(2), (String) one.get(3),
                    (String) one.get(4), (String) one.get(5), (String) one.get(6), (String) one.get(7), (String) one.get(8));
            Adapter byName = adaptersService.getByName((String) one.get(0));
            if (byName != null) {
                adapterSheet.setComment(byName.getComment());
            } else {
                adapterSheet.setComment("No have comment");
            }
            adapterSheets.add(adapterSheet);
        }
        return adapterSheets;
    }

}
