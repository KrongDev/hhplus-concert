package com.hhplusconcert.infra.waitingToken.impl;

import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import com.hhplusconcert.domain.watingToken.repository.WaitingTokenRepository;
import com.hhplusconcert.infra.waitingToken.orm.WaitingTokenJpoRepository;
import com.hhplusconcert.infra.waitingToken.orm.jpo.WaitingTokenJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WaitingTokenRepositoryImpl implements WaitingTokenRepository {
    //
    private final WaitingTokenJpoRepository waitingTokenJpoRepository;

    @Override
    public void save(WaitingToken waitingToken) {
        //
        this.waitingTokenJpoRepository.save(new WaitingTokenJpo(waitingToken));
    }

    @Override
    public WaitingToken findById(String tokenId) {
        WaitingTokenJpo jpo = this.waitingTokenJpoRepository.findById(tokenId).orElseThrow();
        return jpo.toDomain();
    }

    @Override
    public WaitingToken findByUserIdAndSeriesId(String userId, String seriesId) {
        //
        WaitingTokenJpo jpo = this.waitingTokenJpoRepository.findByUserIdAndSeriesId(userId, seriesId).orElseThrow();
        return jpo.toDomain();
    }

    @Override
    public void deleteById(String tokenId) {
        //
        this.waitingTokenJpoRepository.deleteById(tokenId);
    }

    @Override
    public void deleteAllByIds(List<String> tokenIds) {
        //
        this.waitingTokenJpoRepository.deleteAllById(tokenIds);
    }
}
