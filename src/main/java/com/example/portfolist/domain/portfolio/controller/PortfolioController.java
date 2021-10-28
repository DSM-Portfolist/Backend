package com.example.portfolist.domain.portfolio.controller;

import com.example.portfolist.domain.portfolio.dto.request.PortfolioRequest;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioListResponse;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioResponse;
import com.example.portfolist.domain.portfolio.dto.response.RecentPortfolioResponse;
import com.example.portfolist.domain.portfolio.dto.response.ThisMonthPortfolioResponse;
import com.example.portfolist.domain.portfolio.service.PortfolioService;
import com.example.portfolist.global.file.FileUploadProvider;
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
    private final FileUploadProvider fileUploadProvider;

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public PortfolioListResponse getPortfolioList(Pageable pageable, @RequestParam(required = false)List<String> field) {
        return portfolioService.getPortfolioList(pageable, field);
    }

    @GetMapping("/{portfolioId}")
    @ResponseStatus(HttpStatus.OK)
    public PortfolioResponse getPortfolio(@PathVariable long portfolioId) {
        return portfolioService.getPortfolio(portfolioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPortfolio(@RequestPart(value = "portfolioRequest") PortfolioRequest request,
                                @RequestPart(value = "file", required = false) MultipartFile file,
                                @RequestPart(value = "boxImgListList", required = false) List<List<MultipartFile>> boxImgListList) {
        if (file != null) {
            String fileName = fileUploadProvider.uploadFile(file);
            System.out.println("file is null");
            request.setFileName(fileName);
        }
        portfolioService.createPortfolio(request, boxImgListList);
    }

    @DeleteMapping("/{portfolioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePortfolio(@PathVariable long portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
    }

    @PutMapping
    public void updatePortfolio(long portfolioId) {
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

