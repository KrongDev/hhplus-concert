package com.hhplusconcert.infra.concert.orm;

import com.hhplusconcert.infra.concert.orm.jpo.ConcertJpo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpoRepository extends JpaRepository<ConcertJpo, String> {

}
