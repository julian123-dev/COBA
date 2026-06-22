package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.JadwalShift;

@Repository
public interface JadwalShiftRepository extends JpaRepository<JadwalShift, Integer> {

    // Cari semua jadwal shift pada tanggal tertentu (dipakai untuk cek slot kosong)
    List<JadwalShift> findByTanggal(LocalDate tanggal);
}