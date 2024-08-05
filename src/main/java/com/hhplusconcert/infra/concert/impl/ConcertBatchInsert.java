package com.hhplusconcert.infra.concert.impl;

import com.hhplusconcert.infra.concert.orm.ConcertJpaRepository;
import com.hhplusconcert.infra.concert.orm.ConcertSeatJpaRepository;
import com.hhplusconcert.infra.concert.orm.ConcertSeriesJpaRepository;
import com.hhplusconcert.infra.concert.orm.jpo.ConcertJpo;
import com.hhplusconcert.infra.concert.orm.jpo.ConcertSeatJpo;
import com.hhplusconcert.infra.concert.orm.jpo.ConcertSeriesJpo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Repository
public class ConcertBatchInsert {
    //
    @PersistenceContext
    private EntityManager entityManager;
    private final ConcertJpaRepository concertJpaRepository;
    private final ConcertSeriesJpaRepository concertSeriesJpaRepository;
    private final ConcertSeatJpaRepository concertSeatJpaRepository;
    private final Long startAt;
    private final Long endAt;

    public ConcertBatchInsert(ConcertJpaRepository concertJpaRepository, ConcertSeriesJpaRepository concertSeriesJpaRepository, ConcertSeatJpaRepository concertSeatJpaRepository) {
        this.concertJpaRepository = concertJpaRepository;
        this.concertSeriesJpaRepository = concertSeriesJpaRepository;
        this.concertSeatJpaRepository = concertSeatJpaRepository;
        Calendar calendar = Calendar.getInstance();
        this.startAt = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 100);
        this.endAt = calendar.getTimeInMillis();
    }

    @Transactional
    public void genConcertTestData(int maxCount, int cutCount) {
        for(int i = 0; i < maxCount; i+=cutCount) {
            List<ConcertJpo> concerts = new ArrayList<>();
            for(int j = 0; j < cutCount; j++) {
                String uuid = UUID.randomUUID().toString();
                concerts.add(new ConcertJpo(
                        uuid,
                        "test_concert_" + j + "-"+ i,
                        "test_userId",
                        startAt
                ));
            }
            List<ConcertSeriesJpo> series = genConcertSeriesTestData(concerts);
            List<ConcertSeatJpo> seats = genConcertSeatTestData(series);
            this.concertJpaRepository.saveAll(concerts);
            this.concertSeriesJpaRepository.saveAll(series);
            this.concertSeatJpaRepository.saveAll(seats);
            clearEntityManager();
        }
    }
    public List<ConcertSeriesJpo> genConcertSeriesTestData(List<ConcertJpo> concertJpos) {
        List<ConcertSeriesJpo> series = new ArrayList<>();
        for(ConcertJpo concertJpo : concertJpos) {
            String uuid = UUID.randomUUID().toString();
            series.add(new ConcertSeriesJpo(
                    uuid,
                    concertJpo.getConcertId(),
                    startAt,
                    endAt,
                    startAt,
                    endAt,
                    startAt
            ));
        }
        return series;
    }
    public List<ConcertSeatJpo> genConcertSeatTestData(List<ConcertSeriesJpo> seriesJpos) {
        List<ConcertSeatJpo> seats = new ArrayList<>();
        for(ConcertSeriesJpo concertSeriesJpo : seriesJpos) {
            int maxRow = 5;
            int maxSeat = 50;
            int oneRowCol = maxSeat/maxRow;
            int index = 0;
            for(int i = 0; i < maxRow; i++) {
                for(int j = 0; j < oneRowCol; j++) {
                    String uuid = UUID.randomUUID().toString();
                    seats.add(
                            new ConcertSeatJpo(
                                    uuid,
                                    concertSeriesJpo.getSeriesId(),
                                    i,
                                    j,
                                    0,
                                    index,
                                    10000,
                                    false
                            ));
                    index++;
                }
            }
        }
        return seats;
    }

    public void clearEntityManager() {
        entityManager.flush();
        entityManager.clear();
    }
}
