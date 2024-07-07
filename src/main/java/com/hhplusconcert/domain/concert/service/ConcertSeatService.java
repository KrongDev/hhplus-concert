package com.hhplusconcert.domain.concert.service;

import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.repository.ConcertSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertSeatService {
    //
    private final ConcertSeatRepository concertSeatRepository;


    public void createAll(String seriesId, int maxRow, int maxSeat) {
        List<ConcertSeat> concertSeatList = genSeatWithSeries(seriesId, maxRow, maxSeat);
        this.concertSeatRepository.saveAll(concertSeatList);
    }

    public List<ConcertSeat> genSeatWithSeries(String seriesId, int maxRow, int maxSeat) {
        List<ConcertSeat> newSeatList = new ArrayList<>();
        int oneRowCol = maxSeat/maxRow;
        int index = 0;
        for(int i = 0; i < maxRow; i++) {
            for(int j = 0; j < oneRowCol; j++) {
                newSeatList.add(
                    ConcertSeat.of(
                        seriesId,
                        i,
                        j,
                        index++,
                        10000
                    ));
            }
        }
        return newSeatList;
    }

    public List<ConcertSeat> loadConcertSeatsBySeries(String seriesId) {
        //
        List<ConcertSeat> seatList = this.concertSeatRepository.findAllBySeriesId(seriesId);
        seatList = seatList.stream().sorted(Comparator.comparing(ConcertSeat::getSeatIndex)).toList();
        return seatList;
    }
}
