package com.hhplusconcert.domain.concert.service;

import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.repository.ConcertSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertSeatService {
    //
    private final ConcertSeatRepository concertSeatRepository;

    @Transactional
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
                    ConcertSeat.newInstance(
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

    public List<ConcertSeat> loadConcertSeatsBySeries(List<String> seriesIds) {
        //
        List<ConcertSeat> seatList = this.concertSeatRepository.findAllBySeriesIds(seriesIds);
        seatList = seatList.stream().sorted(Comparator.comparing(ConcertSeat::getSeatIndex)).toList();
        return seatList;
    }

    public ConcertSeat loadConcertSeatById(String seatId) {
        //
        return this.concertSeatRepository.findByIdWithThrow(seatId);
    }

    @Transactional
    public void reserveSeat(String seatId) {
        //
        ConcertSeat seat = this.loadConcertSeatById(seatId);
        seat.reserve();
        this.concertSeatRepository.save(seat);
    }

    @Transactional
    public void updateAll(List<ConcertSeat> seatList) {
        //
        this.concertSeatRepository.saveAll(seatList);
    }
}
