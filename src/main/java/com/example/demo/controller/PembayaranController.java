package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.History;
import com.example.demo.entity.Pembayaran;
import com.example.demo.service.HistoryService;
import com.example.demo.service.PembayaranService;

@Controller
public class PembayaranController {

    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private HistoryService historyService;

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "listPembayaran",
                pembayaranService.getAllPembayaran());

        model.addAttribute(
                "listHistory",
                historyService.getAllHistory());

        return "kelola";
    }

    @GetMapping("/tambah")
    public String tambah(Model model) {

        model.addAttribute(
                "pembayaran",
                new Pembayaran());

        return "form";
    }

    @PostMapping("/simpan")
    public String simpan(
            @ModelAttribute Pembayaran pembayaran,
            Model model) {

        Pembayaran hasil = pembayaranService.simpanPembayaran(pembayaran);

        model.addAttribute("pembayaran", hasil);

        return "struk";
    }

    @GetMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id) {

        pembayaranService.hapusPembayaran(id);

        return "redirect:/";
    }

    @GetMapping("/history/edit/{id}")
    public String editHistory(@PathVariable Long id, Model model) {

        History history = historyService.getHistoryById(id);

        model.addAttribute("history", history);

        return "edit-history";
    }

    @PostMapping("/history/update/{id}")
    public String updateHistory(
            @PathVariable Long id,
            @ModelAttribute History history) {

        historyService.updateHistory(id, history);

        return "redirect:/";
    }

    @GetMapping("/history/hapus/{id}")
    public String hapusHistory(@PathVariable Long id) {

        historyService.hapusHistory(id);

        return "redirect:/";
    }
}