package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.domain.portfolio.dto.request.PortfolioRequest;
import com.example.portfolist.domain.portfolio.dto.response.*;
import com.example.portfolist.domain.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/portfolio")
@RestController
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public PortfolioListResponse getPortfolioList(Pageable pageable, @RequestParam(required = false)List<String> field) {
        return portfolioService.getPortfolioList(pageable, field);
    }

    @GetMapping("/{portfolioId}")
    @ResponseStatus(HttpStatus.OK)
    public PortfolioResponse getPortfolio(@PathVariable long portfolioId) {
        return portfolioService.getPortfolioInfo(portfolioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPortfolio(@RequestPart(value = "portfolioRequest") PortfolioRequest request,
                                @RequestPart(value = "file", required = false) MultipartFile file,
                                @RequestPart(value = "containerImgListList", required = false) List<List<MultipartFile>> containerImgListList) {
        portfolioService.createPortfolio(request, file, containerImgListList);
    }

    @DeleteMapping("/{portfolioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePortfolio(@PathVariable long portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
    }

    @PutMapping("/{portfolioId}")
    public void updatePortfolio(@PathVariable long portfolioId,
                                @RequestPart(value = "portfolioRequest") PortfolioRequest request,
                                @RequestPart(value = "file", required = false) MultipartFile file,
                                @RequestPart(value = "containerImgListList", required = false) List<List<MultipartFile>> containerImgListList) {
        portfolioService.updatePortfolio(portfolioId, request, file, containerImgListList);
    }

    @GetMapping("/recent")
    public List<RecentPortfolioResponse> getRecentPortfolio(Pageable pageable) {
        return portfolioService.getRecentPortfolio(pageable);
    }

    @GetMapping("/month")
    public ThisMonthPortfolioResponse getThisMonthPortfolio() {
        return portfolioService.getThisMonthPortfolio();
    }

    @GetMapping("/user/{userId}")
    public List<PortfolioPreview> getPortfolioByUser(@PathVariable long userId) {
        return portfolioService.getPortfolioByUser(userId);
    }

    @GetMapping("/user/{userId}/touching")
    public List<PortfolioPreview> getTouchedPortfolioByUser(Pageable pageable, @PathVariable long userId) {
        return portfolioService.getMyTouchingPortfolio(pageable, userId);
    }

}

