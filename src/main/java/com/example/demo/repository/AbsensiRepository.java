package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Absensi;
import com.example.demo.entity.Karyawan;

@Repository
public interface AbsensiRepository extends JpaRepository<Absensi, Integer> {

    List<Absensi> findByKaryawan_IdKaryawan(Integer idKaryawan);

    List<Absensi> findByJadwal_IdJadwal(Integer idJadwal);

    void deleteByKaryawan(Karyawan karyawan);
}