package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.domain.portfolio.service.TouchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/touching")
@RequiredArgsConstructor
public class TouchingController {

    private final TouchingService touchingService;

    @PostMapping("/{portfolioId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTouching(@PathVariable long portfolioId) {
        touchingService.createTouching(portfolioId);
    }

    @DeleteMapping("/{portfolioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTouching(@PathVariable long portfolioId) {
        touchingService.deleteTouching(portfolioId);
    }
}
