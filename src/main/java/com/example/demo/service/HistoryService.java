package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.History;
import com.example.demo.repository.HistoryRepository;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }

    public void simpanHistory(History history) {
        historyRepository.save(history);
    }

    public History getHistoryById(Long id) {
        return historyRepository.findById(id).orElse(null);
    }

    public void updateHistory(Long id, History dataBaru) {
        History history = historyRepository.findById(id).orElse(null);

        if (history != null) {
            history.setNamaMember(dataBaru.getNamaMember());
            history.setMetodePembayaran(dataBaru.getMetodePembayaran());
            history.setJumlah(dataBaru.getJumlah());

            historyRepository.save(history);
        }
    }

    public void hapusHistory(Long id) {
        historyRepository.deleteById(id);
    }
}