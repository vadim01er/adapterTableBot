package dev.ershov.vd.service;

import dev.ershov.vd.entities.Adapter;
import dev.ershov.vd.entities.AdapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdaptersService {

    private final AdapterRepository adapterRepository;

    public AdaptersService(AdapterRepository adapterRepository) {
        this.adapterRepository = adapterRepository;
    }

    public List<Adapter> listAll() {
        return List.of(new Adapter());
    }

    public Adapter getByName(String name) {
        return adapterRepository.findById(name).orElse(null);
    }

    public void updateComment(String name, String comment) {
        if (!adapterRepository.existsById(name)) {
            adapterRepository.save(new Adapter(name, comment));
        } else {
            Adapter adapter = adapterRepository.findById(name).get();
            adapter.setComment(adapter.getComment() + ";  " + comment);
            adapterRepository.save(adapter);
        }
    }
}
