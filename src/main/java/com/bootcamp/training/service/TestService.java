package com.bootcamp.training.service;

import org.springframework.stereotype.Service;

import com.bootcamp.training.dto.TestDTO;
import com.bootcamp.training.dto.TestDTO.ProfesiKesimpulan;

@Service
public class TestService {
        
    public TestDTO.ProfesiResponse analisisMurid(TestDTO.DataSiswaRequest request) {
        TestDTO.ProfesiKesimpulan kesimpulan = tentukanProfesi(request.getUmur(), request.getNilaiRataRata());
        
        return new TestDTO.ProfesiResponse(
            request.getNama(),
            request.getKelas(),
            request.getUmur(),
            kesimpulan
        );
    }
    
    public ProfesiKesimpulan tentukanProfesi(int umur, double nilaiRataRata) {
        boolean dokter = nilaiRataRata >= 85 && umur <= 25;
        boolean guru = nilaiRataRata >= 90 && umur <= 20;
        boolean chef = nilaiRataRata >= 75 && umur >= 35;
        
        ProfesiKesimpulan kesimpulan = new ProfesiKesimpulan();
        kesimpulan.setDokter(dokter);
        kesimpulan.setGuru(guru);
        kesimpulan.setChef(chef);
        
        return kesimpulan;
    }
}