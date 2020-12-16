package kiz.space.trade.controller;

import kiz.space.trade.dto.TrdHeaderDTO;
import kiz.space.trade.service.TrdHeaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TrdHeaderController {

    private final TrdHeaderService trdHeaderService;

    @PostMapping(value = "/api/trade")
    public void save(@RequestBody TrdHeaderDTO.Req dto) {
        trdHeaderService.save(dto);
    }

    @PutMapping(value = "/api/trade")
    public void update(@RequestBody TrdHeaderDTO.Req dto) {
        trdHeaderService.update(dto);
    }

    @GetMapping(value = "/api/trade/{tradeNum}")
    public ResponseBody findOne(@PathVariable Long tradeNum) {
        return (ResponseBody) trdHeaderService.findOne(tradeNum);
    }

}
