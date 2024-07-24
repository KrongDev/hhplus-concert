package com.hhplusconcert.domain.concert.service;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.concert.model.ConcertSeat;
import com.hhplusconcert.domain.concert.repository.ConcertSeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
        ConcertSeat seat = this.concertSeatRepository.findById(seatId);
        if(Objects.isNull(seat))
            throw new CustomGlobalException(ErrorType.CONCERT_SEAT_NOT_FOUND);
        return seat;
    }

    @Transactional
    public void reserveSeat(String seatId) {
        //
        ConcertSeat seat = this.loadConcertSeatById(seatId);
        seat.reserve();
        this.concertSeatRepository.save(seat);
    }

    @Transactional
    public void unReserveSeat(List<String> seatIds) {
        List<ConcertSeat> seats = this.loadConcertSeatsBySeries(seatIds);
        seats.forEach(ConcertSeat::unReserve);
        this.updateAll(seats);
    }

    @Transactional
    public void updateAll(List<ConcertSeat> seatList) {
        //
        this.concertSeatRepository.saveAll(seatList);
    }
}
