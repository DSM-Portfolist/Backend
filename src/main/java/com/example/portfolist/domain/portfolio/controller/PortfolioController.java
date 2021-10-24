package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.domain.portfolio.dto.request.PortfolioRequest;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioListResponse;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioResponse;
import com.example.portfolist.domain.portfolio.dto.response.RecentPortfolioResponse;
import com.example.portfolist.domain.portfolio.dto.response.ThisMonthPortfolioResponse;
import com.example.portfolist.domain.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/portfolio")
@RestController
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/list")
    public PortfolioListResponse getPortfolioList(Pageable pageable, @RequestParam(required = false)List<String> fieldList) {
        return portfolioService.getPortfolioList(pageable, fieldList);
    }

    @GetMapping("/{id}")
    public PortfolioResponse getPortfolio(@PathVariable long projectId) {
        return null;
    }

    @PostMapping
    public void createPortfolio(@RequestBody PortfolioRequest request) {
    }

    @DeleteMapping
    public void deletePortfolio(long projectId) {
    }

    @PutMapping
    public void updatePortfolio(long projectId) {
    }

    @GetMapping("/recent")
    public RecentPortfolioResponse getRecentPortfolio(@RequestParam int size) {
        return null;
    }

    @GetMapping("/month")
    public ThisMonthPortfolioResponse getThisMonthPortfolio() {
        return null;
    }

    @GetMapping("/user/{userId}")
    public PortfolioListResponse getPortfolioByUser(@PathVariable long userId) {
        return null;
    }

}

