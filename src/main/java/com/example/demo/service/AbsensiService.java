package com.example.demo.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Absensi;
import com.example.demo.entity.JadwalShift;
import com.example.demo.entity.Karyawan;
import com.example.demo.repository.AbsensiRepository;

@Service
public class AbsensiService {

    @Autowired
    private AbsensiRepository absensiRepository;

    @Autowired
    private KaryawanService karyawanService;

    @Autowired
    private JadwalShiftService jadwalShiftService;

    public Absensi catatAbsensi(Integer idKaryawan, Integer idJadwal) {
        Karyawan karyawan = karyawanService.lihatKaryawanById(idKaryawan);
        JadwalShift jadwal = jadwalShiftService.lihatJadwalById(idJadwal);

        Absensi absensi = new Absensi(karyawan, jadwal);
        LocalTime sekarang = LocalTime.now();
        absensi.setJamMasuk(sekarang);

        if (sekarang.isAfter(jadwal.getJamMulai())) {
            absensi.setStatusHadir("TELAT");
        } else {
            absensi.setStatusHadir("HADIR");
        }

        return absensiRepository.save(absensi);
    }

    public Absensi catatJamKeluar(Integer idAbsensi) {
        Absensi absensi = lihatAbsensiById(idAbsensi);
        absensi.setJamKeluar(LocalTime.now());
        return absensiRepository.save(absensi);
    }

    public Absensi lihatAbsensiById(Integer idAbsensi) {
        return absensiRepository.findById(idAbsensi)
                .orElseThrow(() -> new IllegalArgumentException("Absensi dengan id " + idAbsensi + " tidak ditemukan"));
    }

    public List<Absensi> lihatAbsensiKaryawan(Integer idKaryawan) {
        return absensiRepository.findByKaryawan_IdKaryawan(idKaryawan);
    }

    public Absensi verifikasiAbsensi(Integer idAbsensi, String statusBaru) {
        Absensi absensi = lihatAbsensiById(idAbsensi);
        absensi.setStatusHadir(statusBaru);
        return absensiRepository.save(absensi);
    }

    // Riwayat semua absensi dari seluruh karyawan, tanpa filter
    public List<Absensi> lihatSemuaAbsensi() {
        return absensiRepository.findAll();
    }
}