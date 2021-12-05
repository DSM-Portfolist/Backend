package com.example.portfolist.domain.portfolio.repository.portfolio;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.dto.response.PortfolioPreview;
import com.example.portfolist.domain.portfolio.dto.response.QPortfolioPreview;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.global.security.AuthenticationFacade;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.portfolist.domain.auth.entity.QUser.user;
import static com.example.portfolist.domain.portfolio.entity.QPortfolio.portfolio;
import static com.example.portfolist.domain.portfolio.entity.QPortfolioField.portfolioField;
import static com.example.portfolist.domain.portfolio.entity.touching.QTouching.*;


@RequiredArgsConstructor
@Repository
public class QuerydslRepositoryImpl implements QuerydslRepository {

    private final JPAQueryFactory queryFactory;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public Page<PortfolioPreview> getPortfolioList(Pageable pageable,
                                                   List<String> fieldCond,
                                                   String query,
                                                   String searchType) {

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
                        portfolio.thumbnail,
                        portfolio.title,
                        portfolio.introduce,
                        portfolio.date,
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
                .where(fieldKindIn(fieldCond),
                        searchCond("%" + query + "%", searchType), portfolio.isOpen.eq(true))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(descOrAsc(pageable.getSort()))
                .fetchResults();

        List<PortfolioPreview> content = results.getResults();
        content.forEach(p -> p.setField(getFieldKindContentByPortfolioId(p.getId())));

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

    private BooleanExpression searchCond(String query, String searchType) {
        if (searchType.equals("title")) {
            return portfolio.title.like(query);
        } else if (searchType.equals("user")) {
            return portfolio.user.name.like(query);
        }
        return null;
    }

    private OrderSpecifier<LocalDateTime> descOrAsc(Sort sort) {
        return sort.toString().equals("date: DESC") ? portfolio.date.desc() : portfolio.date.asc();
    }

    private BooleanExpression fieldKindIn(List<String> fieldCond) {
        if (fieldCond == null || fieldCond.isEmpty()) {
            return null;
        }
        return portfolioField.fieldKind.content.in(fieldCond);
    }

    @Override
    public Portfolio findThisMonthPortfolio() {
        return queryFactory
                .selectFrom(portfolio)
                .join(portfolio.touchingList, touching)
                .where(portfolio.isOpen.eq(true))
                .orderBy(touching.count().desc())
                .groupBy(portfolio)
                .fetchFirst();
    }


    @Override
    public List<PortfolioPreview> findAllByUser(User byUser) {

        List<Portfolio> touchedPortfolioList = new ArrayList<>();

        if (authenticationFacade.getAuthentication() != null) {
            User loginUser = authenticationFacade.getUser();

            touchedPortfolioList = queryFactory
                    .select(touching.portfolio)
                    .from(touching)
                    .where(touching.user.pk.eq(loginUser.getPk()))
                    .fetch();
        }

        return queryFactory
                .select(new QPortfolioPreview(
                        portfolio.pk,
                        portfolio.thumbnail,
                        portfolio.title,
                        portfolio.introduce,
                        portfolio.date,
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
                .where(user.pk.eq(byUser.getPk()), portfolio.isOpen.eq(true))
                .orderBy(portfolio.pk.desc())
                .fetch();
    }

    @Override
    public List<PortfolioPreview> findMyTouchingPortfolio(Pageable pageable, User byUser) {
        return queryFactory
                .select(new QPortfolioPreview(
                        portfolio.pk,
                        portfolio.thumbnail,
                        portfolio.title,
                        portfolio.introduce,
                        portfolio.date,
                        user.pk,
                        user.name,
                        user.url,
                        Expressions.asBoolean(true),
                        portfolio.commentList.size(),
                        portfolio.touchingList.size()
                ))
                .from(touching)
                .leftJoin(touching.user, user)
                .leftJoin(touching.portfolio, portfolio)
                .where(user.pk.eq(byUser.getPk()), portfolio.isOpen.eq(true))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(portfolio.pk.desc())
                .fetch();
    }
}
