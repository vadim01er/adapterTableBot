package dev.ershov.vd.GSheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SheetsQuickstart {
    private static final String APPLICATION_NAME = "Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private ValueRange autorizeSheet() {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            final String spreadsheetId = "17_Zh3VvYUcQxNfL0QTQkem24obMRCqYulUyBTY4qjuY";
            final String range = "LeadsFromTilda!A2:I";
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            return service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
        } catch (GeneralSecurityException e) {
            log.warn("With GoogleNetHttpTransport");
        } catch (IOException e) {
            log.warn("With Google execute (may be no have access)");
        }
        return null;
    }


    public List<List<Object>> findPerson(String name) {
        // Build a new authorized API client service.
        ValueRange response = autorizeSheet();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            log.error("No data found.");
            return null;
        } else {
            String[] s = name.split(" ");
            if (s.length == 3) {
                return values.stream().filter(objects -> objects.get(0).equals(name)).collect(Collectors.toList());
            } else if (s.length == 2) {
                return values.stream().filter(objects -> {
                    String[] obj = ((String) objects.get(0)).split(" ");
                    return obj[0].equals(s[0]) && obj[1].equals(s[1]);
                }).collect(Collectors.toList());
            } else if (s.length == 1) {
                return values.stream().filter(objects -> {
                    String[] obj = ((String) objects.get(0)).split(" ");
                    return obj[0].equals(s[0]) || obj[1].equals(s[0]);
                }).collect(Collectors.toList());
            } else {
                return null;
            }
        }
    }

    public List<List<Object>> getAll() {
        return autorizeSheet().getValues();
    }

    public List<List<Object>> getUniversity(String university) {
        ValueRange response = autorizeSheet();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
            return null;
        } else {
            return values.stream()
                    .filter(objects -> objects.get(1).equals(university)).collect(Collectors.toList());
        }
    }

    public List<List<Object>> findUniversityAndPerson(String university, String name) {
        ValueRange response = autorizeSheet();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
            return null;
        } else {
            String[] s = name.split(" ");
            if (s.length == 3) {
                return values.stream()
                        .filter(objects -> objects.get(0).equals(name) && objects.get(1).equals(university))
                        .collect(Collectors.toList());
            } else if (s.length == 2) {
                return values.stream().filter(objects -> {
                    String[] obj = ((String) objects.get(0)).split(" ");
                    return obj[0].equals(s[0]) && obj[1].equals(s[1]) && objects.get(1).equals(university);
                }).collect(Collectors.toList());
            } else if (s.length == 1) {
                return values.stream().filter(objects -> {
                    String[] obj = ((String) objects.get(0)).split(" ");
                    return (obj[0].equals(s[0]) || obj[1].equals(s[0])) && objects.get(1).equals(university);
                }).collect(Collectors.toList());
            } else {
                return null;
            }
        }
    }
}
