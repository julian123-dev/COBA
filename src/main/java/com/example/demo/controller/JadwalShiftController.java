package com.example.demo.controller;

import com.example.demo.entity.JadwalShift;
import com.example.demo.service.JadwalShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/jadwal")
public class JadwalShiftController {

    @Autowired
    private JadwalShiftService jadwalShiftService;

    // GET /jadwal -> tampilkan halaman daftar jadwal
    @GetMapping
    public String halamanJadwal(Model model) {
        model.addAttribute("daftarJadwal", jadwalShiftService.lihatSemuaJadwal());
        return "jadwal";
    }

    // GET /jadwal/tambah -> tampilkan halaman form buat jadwal
    @GetMapping("/tambah")
    public String halamanTambahJadwal() {
        return "jadwal-tambah";
    }

    // POST /jadwal -> proses buat jadwal dari form
    @PostMapping
    public String buatJadwal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tanggal,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime jamMulai,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime jamSelesai,
            @RequestParam(required = false) String keterangan,
            Model model) {
        try {
            jadwalShiftService.buatJadwal(tanggal, jamMulai, jamSelesai, keterangan);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "jadwal-tambah"; // kembali ke halaman form jika error
        }
        return "redirect:/jadwal";
    }

    // GET /jadwal/{id}/hapus -> hapus jadwal lalu kembali ke halaman
    @GetMapping("/{id}/hapus")
    public String hapusJadwal(@PathVariable Integer id) {
        jadwalShiftService.hapusJadwal(id);
        return "redirect:/jadwal";
    }
}