package kiz.space.trade.service;

import kiz.space.trade.dto.TrdHeaderDTO;
import kiz.space.trade.dto.TrdTermDTO;
import kiz.space.trade.model.TrdHeader;
import kiz.space.trade.model.TrdTerm;
import kiz.space.trade.repository.TrdHeaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TrdHeaderService {

    private final TrdHeaderRepository trdHeaderRepository;

    public TrdHeaderDTO.Res findOne(Long tradeNum) {
        TrdHeader trdHeader = trdHeaderRepository.findByTradeNum(tradeNum);
        return TrdHeaderDTO.Res.of(trdHeader);
    }

    @Transactional
    public void save(TrdHeaderDTO.Req dto) {
        final TrdHeader trdHeader = dto.toEntity();

        Set<TrdTermDTO.Req> trdTerms = dto.getTrdTerm();
        trdTerms.forEach(item -> {
            trdHeader.addTrdTerm(item.toEntity());
        });

        trdHeaderRepository.save(trdHeader);
    }
}
