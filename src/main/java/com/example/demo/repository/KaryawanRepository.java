package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Karyawan;

@Repository
public interface KaryawanRepository extends JpaRepository<Karyawan, Integer> {

    Optional<Karyawan> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Karyawan> findByNamaContainingIgnoreCase(String nama);
}