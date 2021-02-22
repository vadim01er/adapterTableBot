package dev.ershov.vd.controller;

import dev.ershov.vd.GSheets.SheetsQuickstart;
import dev.ershov.vd.client.CreateAnswer;
import dev.ershov.vd.client.answer.tg.TgResponse;
import dev.ershov.vd.entities.AdapterSheet;
import dev.ershov.vd.entities.Adapter;
import dev.ershov.vd.service.AdaptersService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    private final CreateAnswer createAnswer;
    private final AdaptersService adaptersService;
    private final SheetsQuickstart sheetsQuickstart;

    @Value("${download.path}")
    private String downloadPath;

    public Controller(CreateAnswer createAnswer, AdaptersService adaptersService, SheetsQuickstart sheetsQuickstart) {
        this.createAnswer = createAnswer;
        this.adaptersService = adaptersService;
        this.sheetsQuickstart = sheetsQuickstart;
    }

    @PostMapping("/botTgTable")
    public String server(@RequestBody TgResponse msg) {
        createAnswer.reply(msg);
        return HttpStatus.OK.getReasonPhrase();
    }

    @GetMapping("/")
    public ModelAndView home(HttpServletRequest httpServletRequest, Map<String, Object> model) {
        if (httpServletRequest.isUserInRole("ADMIN")) {
            return admin(model);
        } else if (httpServletRequest.isUserInRole("iknt")) {
            return university("iknt", model);
        } else if (httpServletRequest.isUserInRole("ipmt")) {
            return university("ipmt", model);
        } else if (httpServletRequest.isUserInRole("ici")) {
            return university("ici", model);
        } else if (httpServletRequest.isUserInRole("gi")) {
            return university("gi", model);
        } else if (httpServletRequest.isUserInRole("immit")) {
            return university("immit", model);
        } else if (httpServletRequest.isUserInRole("ipmm")) {
            return university("ipmm", model);
        } else if (httpServletRequest.isUserInRole("ifnit")) {
            return university("ifnit", model);
        } else if (httpServletRequest.isUserInRole("ibcib")) {
            return university("ibcib", model);
        } else if (httpServletRequest.isUserInRole("ia")) {
            return university("ia", model);
        } else if (httpServletRequest.isUserInRole("ikizi")) {
            return university("ikizi", model);
        } else {
            return university("icpo", model);
        }
    }

    public ModelAndView admin(Map<String, Object> model) {
        List<List<Object>> all = sheetsQuickstart.getAll();
        if (all != null && !all.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(all);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", "");
        model.put("isAdmin", "ADMIN");
        model.put("filename", "all");
        return new ModelAndView("index", model);
    }

    @RequestMapping(value = "/download/{filename}", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadFile(@PathVariable String filename) {
        String name = getName(filename);
        List<List<Object>> onlyUniversity;
        if (name.equals("all")) {
            onlyUniversity = sheetsQuickstart.getAll();
        } else {
            onlyUniversity = sheetsQuickstart.getUniversity(name);
        }
        if (onlyUniversity != null && !onlyUniversity.isEmpty()) {
            new File(downloadPath).mkdir();
            File file = new File(downloadPath + "/" + filename + ".xls");
            try {
                file.createNewFile();
                Workbook book = new HSSFWorkbook();
                Sheet sheet = book.createSheet("Адаптеры " + name);
                for (int i = 0; i < onlyUniversity.size(); i++) {
                    Row row = sheet.createRow(i);
                    for (int j = 0; j < onlyUniversity.get(i).size(); j++) {
                        row.createCell(j).setCellValue((String) onlyUniversity.get(i).get(j));
                    }
                }
                book.write(new FileOutputStream(file));
                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                HttpHeaders headers = new HttpHeaders();

                headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");

                ResponseEntity<Object> responseEntity =
                        ResponseEntity.ok()
                                .headers(headers)
                                .contentLength(file.length())
                                .contentType(MediaType.parseMediaType("application/xls"))
                                .body(resource);
                return responseEntity;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @GetMapping("/search")
    public ModelAndView search(String keyword, Map<String, Object> model) {
        List<List<Object>> person = sheetsQuickstart.findPerson(keyword);
        if (person != null && !person.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(person);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", keyword);
        return new ModelAndView("search", model);
    }

    @GetMapping("/{name}")
    public ModelAndView university(@PathVariable String name, Map<String, Object> model) {
        if (name.equals("login")) {
            return new ModelAndView("login", model);
        }
        List<List<Object>> onlyUniversity = sheetsQuickstart.getUniversity(getName(name));
        if (onlyUniversity != null && !onlyUniversity.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyUniversity);
            model.put("listAdapter", adapterSheets);
        }
        model.put("test", name + "/");
        model.put("filename", name);
        return new ModelAndView("index", model);
    }

    @GetMapping("/{name}/search")
    public ModelAndView universitySearch(@PathVariable String name, String keyword, Map<String, Object> model) {
        String name1 = getName(name);
        List<List<Object>> onlyUniversity = sheetsQuickstart.findUniversityAndPerson(name1, keyword);
        if (onlyUniversity != null && !onlyUniversity.isEmpty()) {
            List<AdapterSheet> adapterSheets = getAdapters(onlyUniversity);
            model.put("listAdapter", adapterSheets);
        }
        model.put("search", name1 + ": " + keyword);
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

    private String getName(String requestName) {
        switch (requestName) {
            case "iknt":
                return "ИКНТ";
            case "ipmt":
                return "ИПМЭиТ";
            case "ici":
                return "ИСИ";
            case "gi":
                return "ГИ";
            case "immit":
                return "ИММиТ";
            case "ipmm":
                return "ИПММ";
            case "ifnit":
                return "ИФНиТ";
            case "ibcib":
                return "ИБСиБ";
            case "ia":
                return "ИЭ";
            case "icpo":
                return "ИСПО";
            case "ikizi":
                return "ИКиЗИ";
            default:
                return "all";
        }
    }


}
