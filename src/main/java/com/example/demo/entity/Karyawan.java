package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entity Karyawan.
 * Atribut: idKaryawan, nama, status, tanggalMasuk, email, nomorTelp.
 */
@Entity
@Table(name = "karyawan")
@Getter
@Setter
@NoArgsConstructor
public class Karyawan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_karyawan")
    private Integer idKaryawan;

    @NotBlank(message = "Nama tidak boleh kosong")
    @Column(name = "nama", nullable = false, length = 100)
    private String nama;

    // status karyawan, contoh: "AKTIF" atau "NONAKTIF"
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "tanggal_masuk", nullable = false)
    private LocalDate tanggalMasuk;

    @Email(message = "Format email tidak valid")
    @NotBlank(message = "Email tidak boleh kosong")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "nomor_telp", length = 20)
    private String nomorTelp;

    public Karyawan(String nama, String status, LocalDate tanggalMasuk, String email, String nomorTelp) {
        this.nama = nama;
        this.status = status;
        this.tanggalMasuk = tanggalMasuk;
        this.email = email;
        this.nomorTelp = nomorTelp;
    }
}