package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entity JadwalShift.
 * Atribut: idJadwal, tanggal, jamMulai, jamSelesai, keterangan.
 * Berdiri sendiri (tidak punya relasi langsung ke Karyawan tertentu) karena
 * merepresentasikan jadwal shift operasional umum (misal "Shift Pagi",
 * "Shift Malam") yang dikelola oleh Admin.
 */
@Entity
@Table(name = "jadwal_shift")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JadwalShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jadwal")
    private Integer idJadwal;

    @NotNull(message = "Tanggal tidak boleh kosong")
    @Column(name = "tanggal", nullable = false)
    private LocalDate tanggal;

    @NotNull(message = "Jam mulai tidak boleh kosong")
    @Column(name = "jam_mulai", nullable = false)
    private LocalTime jamMulai;

    @NotNull(message = "Jam selesai tidak boleh kosong")
    @Column(name = "jam_selesai", nullable = false)
    private LocalTime jamSelesai;

    // contoh isi: "Shift Pagi", "Shift Siang", "Shift Malam"
    @Column(name = "keterangan", length = 100)
    private String keterangan;
}