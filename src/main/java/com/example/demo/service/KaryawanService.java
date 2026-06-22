package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Karyawan;
import com.example.demo.repository.AbsensiRepository;
import com.example.demo.repository.KaryawanRepository;

@Service
public class KaryawanService {

    @Autowired
    private KaryawanRepository karyawanRepository;

    @Autowired
    private AbsensiRepository absensiRepository;

    // ✅ LIHAT SEMUA
    public List<Karyawan> lihatSemuaKaryawan() {
        return karyawanRepository.findAll();
    }

    // ✅ LIHAT BY ID
    public Karyawan lihatKaryawanById(Integer id) {
        return karyawanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Karyawan tidak ditemukan"));
    }

    // ✅ TAMBAH KARYAWAN
    public Karyawan tambahKaryawan(Karyawan karyawan) {

        // validasi email unik
        if (karyawanRepository.existsByEmail(karyawan.getEmail())) {
            throw new IllegalArgumentException("Email sudah digunakan!");
        }

        // default status kalau kosong
        if (karyawan.getStatus() == null || karyawan.getStatus().isBlank()) {
            karyawan.setStatus("AKTIF");
        }

        return karyawanRepository.save(karyawan);
    }

    // ✅ UPDATE KARYAWAN
    public Karyawan updateProfil(Integer id, String nama, String email, String nomorTelp) {

        Karyawan karyawan = lihatKaryawanById(id);

        // validasi email kalau diubah
        if (!karyawan.getEmail().equals(email) &&
                karyawanRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email sudah digunakan!");
        }

        karyawan.setNama(nama);
        karyawan.setEmail(email);
        karyawan.setNomorTelp(nomorTelp);

        return karyawanRepository.save(karyawan);
    }

    // ✅ HAPUS KARYAWAN (ANTI ERROR FK 🔥)
    public void hapusKaryawan(Integer id) {

        Karyawan karyawan = lihatKaryawanById(id);

        // 🔥 HAPUS SEMUA ABSENSI TERKAIT DULU
        absensiRepository.deleteByKaryawan(karyawan);

        // 🔥 BARU HAPUS KARYAWAN
        karyawanRepository.delete(karyawan);
    }

    // ✅ CARI KARYAWAN BY NAMA
    public List<Karyawan> cariKaryawanByNama(String nama) {
        return karyawanRepository.findByNamaContainingIgnoreCase(nama);
    }
}