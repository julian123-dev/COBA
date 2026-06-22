package com.example.demo.controller;

import com.example.demo.entity.Absensi;
import com.example.demo.service.AbsensiService;
import com.example.demo.service.JadwalShiftService;
import com.example.demo.service.KaryawanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/absensi")
public class AbsensiController {

    @Autowired
    private AbsensiService absensiService;

    @Autowired
    private JadwalShiftService jadwalShiftService;

    @Autowired
    private KaryawanService karyawanService; // tambah inject KaryawanService

    // GET /absensi -> halaman riwayat semua absensi saja
    @GetMapping
    public String halamanAbsensi(Model model) {
        model.addAttribute("semuaAbsensi", absensiService.lihatSemuaAbsensi());
        return "absensi";
    }

    // GET /absensi/form -> halaman khusus catat absensi
    @GetMapping("/form")
    public String halamanFormAbsensi(Model model) {
        model.addAttribute("daftarJadwal", jadwalShiftService.lihatSemuaJadwal());
        model.addAttribute("daftarKaryawan", karyawanService.lihatSemuaKaryawan()); // tambah ini
        return "absensi-form";
    }

    // POST /absensi/form -> proses catat absensi
    @PostMapping("/form")
    public String catatAbsensi(@RequestParam Integer idKaryawan, @RequestParam Integer idJadwal, Model model) {
        try {
            absensiService.catatAbsensi(idKaryawan, idJadwal);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("daftarJadwal", jadwalShiftService.lihatSemuaJadwal());
            model.addAttribute("daftarKaryawan", karyawanService.lihatSemuaKaryawan()); // tambah ini juga
            return "absensi-form";
        }
        return "redirect:/absensi";
    }

    @GetMapping("/{id}/keluar")
    public String catatJamKeluar(@PathVariable Integer id) {
        absensiService.catatJamKeluar(id);
        return "redirect:/absensi";
    }

    @GetMapping("/{id}/verifikasi")
    public String verifikasiAbsensi(@PathVariable Integer id, @RequestParam String statusBaru) {
        absensiService.verifikasiAbsensi(id, statusBaru);
        return "redirect:/absensi";
    }
}