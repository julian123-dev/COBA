package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.JadwalShift;
import com.example.demo.repository.JadwalShiftRepository;

@Service
public class JadwalShiftService {

    @Autowired
    private JadwalShiftRepository jadwalShiftRepository;

    // buatJadwal(): validasi jam, lalu validasi tabrakan otomatis sebelum simpan
    public JadwalShift buatJadwal(LocalDate tanggal, LocalTime jamMulai, LocalTime jamSelesai, String keterangan) {
        if (!jamMulai.isBefore(jamSelesai)) {
            throw new IllegalArgumentException("Jam mulai harus lebih awal dari jam selesai");
        }
        if (!cekSlotKosong(tanggal, jamMulai, jamSelesai)) {
            throw new IllegalArgumentException("Slot bertabrakan dengan jadwal shift lain pada tanggal tersebut");
        }
        JadwalShift jadwal = new JadwalShift(null, tanggal, jamMulai, jamSelesai, keterangan);
        return jadwalShiftRepository.save(jadwal);
    }

    public List<JadwalShift> lihatSemuaJadwal() {
        return jadwalShiftRepository.findAll();
    }

    public JadwalShift lihatJadwalById(Integer idJadwal) {
        return jadwalShiftRepository.findById(idJadwal)
                .orElseThrow(() -> new IllegalArgumentException("Jadwal dengan id " + idJadwal + " tidak ditemukan"));
    }

    // cekSlotKosong(): cek tabrakan jam pada tanggal yang sama
    public Boolean cekSlotKosong(LocalDate tanggal, LocalTime jamMulai, LocalTime jamSelesai) {
        List<JadwalShift> jadwalDiTanggalItu = jadwalShiftRepository.findByTanggal(tanggal);
        for (JadwalShift jadwal : jadwalDiTanggalItu) {
            boolean bertabrakan = jamMulai.isBefore(jadwal.getJamSelesai()) && jamSelesai.isAfter(jadwal.getJamMulai());
            if (bertabrakan) {
                return false;
            }
        }
        return true;
    }

    public void hapusJadwal(Integer idJadwal) {
        JadwalShift jadwal = lihatJadwalById(idJadwal);
        jadwalShiftRepository.delete(jadwal);
    }
}