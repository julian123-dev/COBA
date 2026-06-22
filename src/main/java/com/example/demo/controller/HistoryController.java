package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.HistoryService;

@Controller
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    // Halaman history (opsional, bukan utama)
    @GetMapping("/history")
    public String history(Model model) {

        model.addAttribute(
                "listHistory",
                historyService.getAllHistory());

        return "history";
    }
}
