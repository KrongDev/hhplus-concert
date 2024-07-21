package com.hhplusconcert.infra.waitingToken.impl;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.watingToken.model.WaitingToken;
import com.hhplusconcert.domain.watingToken.repository.WaitingTokenRepository;
import com.hhplusconcert.infra.waitingToken.orm.WaitingTokenJpaRepository;
import com.hhplusconcert.infra.waitingToken.orm.jpo.WaitingTokenJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WaitingTokenRepositoryImpl implements WaitingTokenRepository {
    //
    private final WaitingTokenJpaRepository waitingTokenJpaRepository;

    @Override
    public void save(WaitingToken waitingToken) {
        //
        this.waitingTokenJpaRepository.save(new WaitingTokenJpo(waitingToken));
    }

    @Override
    public WaitingToken findById(String tokenId) {
        Optional<WaitingTokenJpo> jpo = this.waitingTokenJpaRepository.findById(tokenId);
        return jpo.map(WaitingTokenJpo::toDomain).orElse(null);
    }

    @Override
    public WaitingToken findByUserIdAndSeriesId(String userId, String seriesId) {
        //
        Optional<WaitingTokenJpo> jpo = this.waitingTokenJpaRepository.findByUserIdAndSeriesId(userId, seriesId);
        if(jpo.isEmpty())
            throw new CustomGlobalException(ErrorType.TOKEN_NOT_FOUND);
        return jpo.get().toDomain();
    }

    @Override
    public List<WaitingToken> findAllByExpired(long expiredTime) {
        List<WaitingTokenJpo> jpos = this.waitingTokenJpaRepository.findAllByExpiredAtLessThanEqual(expiredTime);
        return jpos.stream().map(WaitingTokenJpo::toDomain).toList();
    }

    @Override
    public void deleteByUserIdAndSeriesId(String userId, String seriesId) {
        //
        this.waitingTokenJpaRepository.deleteByUserIdAndSeriesId(userId, seriesId);
    }

    @Override
    public void deleteAllByIds(List<String> tokenIds) {
        //
        this.waitingTokenJpaRepository.deleteAllById(tokenIds);
    }

    @Override
    public boolean existsByUserIdAndSeriesId(String userId, String seriesId) {
        //
        Optional<WaitingTokenJpo> jpo = this.waitingTokenJpaRepository.findFirstByUserIdAndSeriesId(userId, seriesId);
        return jpo.isPresent();
    }
}
