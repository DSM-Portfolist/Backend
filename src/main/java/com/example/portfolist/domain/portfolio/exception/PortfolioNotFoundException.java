package com.example.portfolist.domain.portfolio.exception;

import com.example.portfolist.global.error.ErrorCode;
import com.example.portfolist.global.error.exception.GlobalException;

public class PortfolioNotFoundException extends GlobalException {

    public PortfolioNotFoundException() {
        super(ErrorCode.PORTFOLIO_NOT_FOUND);
    }
}
