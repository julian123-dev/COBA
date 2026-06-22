package com.example.demo;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Absensi;
import com.example.demo.entity.JadwalShift;
import com.example.demo.entity.Karyawan;
import com.example.demo.repository.AbsensiRepository;
import com.example.demo.repository.JadwalShiftRepository;
import com.example.demo.repository.KaryawanRepository;

/**
 * Mengisi data dummy otomatis saat aplikasi pertama kali dijalankan,
 * supaya halaman Riwayat Absensi tidak kosong saat demo/presentasi.
 * Hanya berjalan jika tabel karyawan masih kosong (tidak akan menduplikasi
 * data).
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final KaryawanRepository karyawanRepository;
    private final JadwalShiftRepository jadwalShiftRepository;
    private final AbsensiRepository absensiRepository;

    public DataInitializer(KaryawanRepository karyawanRepository,
            JadwalShiftRepository jadwalShiftRepository,
            AbsensiRepository absensiRepository) {
        this.karyawanRepository = karyawanRepository;
        this.jadwalShiftRepository = jadwalShiftRepository;
        this.absensiRepository = absensiRepository;
    }

    @Override
    public void run(String... args) {
        if (karyawanRepository.count() > 0) {
            return; // sudah ada data, tidak perlu isi ulang
        }

        // ===== Karyawan dummy =====
        Karyawan torik = new Karyawan("torik", "AKTIF", LocalDate.of(2026, 1, 10), "torik@gmail.com", "081111111111");
        Karyawan jule = new Karyawan("jule", "AKTIF", LocalDate.of(2026, 2, 5), "jule@gmail.com", "082222222222");
        Karyawan fai = new Karyawan("fai", "AKTIF", LocalDate.of(2026, 3, 15), "fai@gmail.com", "083333333333");
        karyawanRepository.save(torik);
        karyawanRepository.save(jule);
        karyawanRepository.save(fai);

        // ===== Jadwal Shift dummy =====
        JadwalShift shiftPagi = new JadwalShift(null, LocalDate.of(2026, 6, 22), LocalTime.of(8, 0),
                LocalTime.of(12, 0), "pagi");
        JadwalShift shiftSiang = new JadwalShift(null, LocalDate.of(2026, 6, 22), LocalTime.of(13, 0),
                LocalTime.of(17, 0), "siang");
        JadwalShift shiftSore = new JadwalShift(null, LocalDate.of(2026, 6, 22), LocalTime.of(18, 0),
                LocalTime.of(22, 0), "sore");
        jadwalShiftRepository.save(shiftPagi);
        jadwalShiftRepository.save(shiftSiang);
        jadwalShiftRepository.save(shiftSore);

        // ===== Absensi dummy =====

        // torik - shift pagi, datang tepat waktu, sudah selesai kerja
        Absensi absen1 = new Absensi(torik, shiftPagi);
        absen1.setJamMasuk(LocalTime.of(7, 55));
        absen1.setJamKeluar(LocalTime.of(12, 3));
        absen1.setStatusHadir("HADIR");
        absensiRepository.save(absen1);

        // jule - shift siang, datang terlambat, sudah selesai kerja
        Absensi absen2 = new Absensi(jule, shiftSiang);
        absen2.setJamMasuk(LocalTime.of(13, 20));
        absen2.setJamKeluar(LocalTime.of(17, 5));
        absen2.setStatusHadir("TELAT");
        absensiRepository.save(absen2);

        // fai - shift sore, datang tepat waktu, sudah selesai kerja
        Absensi absen3 = new Absensi(fai, shiftSore);
        absen3.setJamMasuk(LocalTime.of(17, 58));
        absen3.setJamKeluar(LocalTime.of(22, 10));
        absen3.setStatusHadir("HADIR");
        absensiRepository.save(absen3);

        // torik - shift siang (hari berikutnya), terlambat, belum check-out
        JadwalShift shiftSiang2 = new JadwalShift(null, LocalDate.of(2026, 6, 23), LocalTime.of(13, 0),
                LocalTime.of(17, 0), "siang");
        jadwalShiftRepository.save(shiftSiang2);
        Absensi absen4 = new Absensi(torik, shiftSiang2);
        absen4.setJamMasuk(LocalTime.of(13, 15));
        absen4.setStatusHadir("TELAT");
        absensiRepository.save(absen4);
    }
}