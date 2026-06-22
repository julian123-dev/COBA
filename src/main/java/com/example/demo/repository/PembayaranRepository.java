package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Pembayaran;

public interface PembayaranRepository extends JpaRepository<Pembayaran, Long> {
}
