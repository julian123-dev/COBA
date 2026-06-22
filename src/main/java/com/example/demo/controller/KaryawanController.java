package com.example.demo.controller;

import com.example.demo.entity.Karyawan;
import com.example.demo.service.KaryawanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/karyawan")
public class KaryawanController {

    @Autowired
    private KaryawanService karyawanService;

    // GET /karyawan -> daftar semua karyawan
    @GetMapping
    public String halamanKaryawan(Model model) {
        model.addAttribute("daftarKaryawan", karyawanService.lihatSemuaKaryawan());
        return "karyawan";
    }

    // GET /karyawan?cari=kata -> daftar hasil pencarian nama
    @GetMapping(params = "cari")
    public String cariKaryawan(@RequestParam("cari") String cari, Model model) {
        List<Karyawan> hasil = cari.isBlank() ? karyawanService.lihatSemuaKaryawan()
                : karyawanService.cariKaryawanByNama(cari);
        model.addAttribute("daftarKaryawan", hasil);
        model.addAttribute("kataDicari", cari);
        return "karyawan";
    }

    @GetMapping("/form")
    public String halamanFormTambah(Model model) {
        model.addAttribute("karyawan", new Karyawan());
        model.addAttribute("modeEdit", false);
        return "karyawan-form";
    }

    @GetMapping(value = "/form", params = "edit")
    public String halamanFormEdit(@RequestParam("edit") Integer edit, Model model) {
        model.addAttribute("karyawan", karyawanService.lihatKaryawanById(edit));
        model.addAttribute("modeEdit", true);
        return "karyawan-form";
    }

    @PostMapping("/form")
    public String simpanKaryawan(@ModelAttribute Karyawan karyawan, Model model) {
        try {
            if (karyawan.getIdKaryawan() == null) {
                karyawanService.tambahKaryawan(karyawan);
            } else {
                karyawanService.updateProfil(karyawan.getIdKaryawan(), karyawan.getNama(), karyawan.getEmail(),
                        karyawan.getNomorTelp());
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("karyawan", karyawan);
            model.addAttribute("modeEdit", karyawan.getIdKaryawan() != null);
            return "karyawan-form";
        }
        return "redirect:/karyawan";
    }

    @GetMapping("/{id}/hapus")
    public String hapusKaryawan(@PathVariable Integer id) {
        karyawanService.hapusKaryawan(id);
        return "redirect:/karyawan";
    }
}