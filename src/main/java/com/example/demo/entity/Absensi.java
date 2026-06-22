package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

/**
 * Entity Absensi.
 * Atribut: idAbsensi, idKaryawan, idJadwal, jamMasuk, jamKeluar, statusHadir.
 * Berelasi langsung ke Karyawan (siapa yang absen) dan JadwalShift
 * (shift mana yang menjadi acuan jam masuk/keluar).
 *
 * Method catatAbsensi(), lihatAbsensi(), verifikasiAbsensi(), rekapAbsensi()
 * pada diagram adalah logika bisnis yang melibatkan banyak data sekaligus,
 * sehingga diimplementasikan di layer Service (AbsensiService), bukan di sini.
 */
@Entity
@Table(name = "absensi")
@Getter
@Setter
@NoArgsConstructor
public class Absensi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_absensi")
    private Integer idAbsensi;

    // Relasi ke Karyawan: banyak Absensi -> 1 Karyawan
    @ManyToOne
    @JoinColumn(name = "id_karyawan", nullable = false)
    private Karyawan karyawan;

    // Relasi ke JadwalShift: banyak Absensi -> 1 JadwalShift
    @ManyToOne
    @JoinColumn(name = "id_jadwal", nullable = false)
    private JadwalShift jadwal;

    @Column(name = "jam_masuk")
    private LocalTime jamMasuk;

    @Column(name = "jam_keluar")
    private LocalTime jamKeluar;

    // contoh isi: "HADIR", "TELAT", "ALPHA", "IZIN"
    @NotNull(message = "Status hadir tidak boleh kosong")
    @Column(name = "status_hadir", nullable = false, length = 20)
    private String statusHadir;

    public Absensi(Karyawan karyawan, JadwalShift jadwal) {
        this.karyawan = karyawan;
        this.jadwal = jadwal;
        this.statusHadir = "BELUM_ABSEN";
    }
}