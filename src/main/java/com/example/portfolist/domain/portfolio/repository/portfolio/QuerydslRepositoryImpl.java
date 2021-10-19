package com.example.portfolist.domain.portfolio.repository.portfolio;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioPreview;
import com.example.portfolist.domain.portfolio.dto.response.QPortfolioPreview;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.global.security.AuthenticationFacade;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.example.portfolist.domain.auth.entity.QUser.*;
import static com.example.portfolist.domain.portfolio.entity.QPortfolio.portfolio;
import static com.example.portfolist.domain.portfolio.entity.QPortfolioField.portfolioField;
import static com.example.portfolist.domain.portfolio.entity.comment.QComment.comment;
import static com.example.portfolist.domain.portfolio.entity.touching.QTouching.*;
import static com.querydsl.core.types.ExpressionUtils.count;


@RequiredArgsConstructor
@Repository
public class QuerydslRepositoryImpl implements QuerydslRepository {

    private final JPAQueryFactory queryFactory;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public Page<PortfolioPreview> getPortfolioList(Pageable pageable, List<String> fieldCond) {

        List<Portfolio> touchedPortfolioList = new ArrayList<>();

        if (authenticationFacade.getAuthentication() != null) {
            User loginUser = authenticationFacade.getUser();

            touchedPortfolioList = queryFactory
                    .select(touching.portfolio)
                    .from(touching)
                    .where(touching.user.pk.eq(loginUser.getPk()))
                    .fetch();
        }

        QueryResults<PortfolioPreview> results = queryFactory
                .select(new QPortfolioPreview(
                        portfolio.pk,
                        portfolio.url,
                        portfolio.title,
                        portfolio.introduce,
                        portfolio.date.stringValue(),
                        user.pk,
                        user.name,
                        user.url,
                        new CaseBuilder()
                                .when(portfolio.in(touchedPortfolioList)).then(true)
                                .otherwise(false),
                        portfolio.commentList.size(),
                        portfolio.touchingList.size()
                )).distinct()
                .from(portfolio)
                .join(portfolio.user, user)
                .leftJoin(portfolio.portfolioFields, portfolioField)
                .where(fieldKindIn(fieldCond))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(portfolio.date.stringValue().desc())
                .fetchResults();

        List<PortfolioPreview> content = results.getResults();

        content.forEach(p -> p.setField(
                getFieldKindContentByPortfolioId(p.getId())
        ));

        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<String> getFieldKindContentByPortfolioId(long id) {
        return queryFactory
                .select(portfolioField.fieldKind.content)
                .from(portfolioField)
                .where(portfolioField.portfolio.pk.eq(id))
                .fetch();
    }

    private BooleanExpression fieldKindIn(List<String> fieldCond) {
        if (fieldCond == null || fieldCond.isEmpty()) {
            return null;
        }
        return portfolioField.fieldKind.content.in(fieldCond);
    }

}
