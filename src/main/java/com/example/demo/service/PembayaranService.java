package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.History;
import com.example.demo.entity.Pembayaran;
import com.example.demo.repository.PembayaranRepository;

@Service
public class PembayaranService {

    @Autowired
    private PembayaranRepository pembayaranRepository;

    @Autowired
    private HistoryService historyService;

    public List<Pembayaran> getAllPembayaran() {
        return pembayaranRepository.findAll();
    }

    public Pembayaran simpanPembayaran(Pembayaran pembayaran) {

        Pembayaran hasil = pembayaranRepository.save(pembayaran);

        History history = new History();

        history.setNamaMember(
                hasil.getNamaPelanggan());

        history.setMetodePembayaran(
                hasil.getMetodePembayaran());

        history.setJumlah(
                hasil.getJumlah());

        historyService.simpanHistory(history);

        return hasil;
    }

    public void hapusPembayaran(Long id) {
        pembayaranRepository.deleteById(id);
    }
}