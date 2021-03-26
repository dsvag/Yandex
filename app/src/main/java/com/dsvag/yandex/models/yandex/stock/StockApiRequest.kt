package com.dsvag.yandex.models.yandex.stock

import com.dsvag.yandex.models.yandex.Operation
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockApiRequest(
    @Json(name = "operationName")
    val operationName: Operation = Operation.Instrument,

    @Json(name = "query")
    val query: String = "query Instrument(\$type: InstrumentType!, \$slug: String!, \$isBond: Boolean!, \$isEtf: Boolean!, \$isShare: Boolean!, \$bondSchoolQuestionId: ID!, \$hasBroker: Boolean!, \$hasIIS: Boolean!) {\n  tanker {\n    SEO: keyset(name: \"SEO\") {\n      data\n      id\n      __typename\n    }\n    BubbleCases: keyset(name: \"BubbleCases\") {\n      data\n      id\n      __typename\n    }\n    QnA: keyset(name: \"QnA\") {\n      data\n      id\n      __typename\n    }\n    MoneyFundVTBM: keyset(name: \"MoneyFundVTBM\") {\n      id\n      data\n      __typename\n    }\n    __typename\n  }\n  bunker {\n    bubbleCases {\n      ofz {\n        key\n        theme\n        __typename\n      }\n      vtbm {\n        key\n        theme\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n  ...Trade2\n  ...InstrumentBondData @include(if: \$isBond)\n  ...BondSchoolQuestionData @include(if: \$isBond)\n  ...InstrumentEtfData @include(if: \$isEtf)\n  ...InstrumentShareData @include(if: \$isShare)\n  news {\n    related(slug: \$slug) {\n      ...NewsRelatedProps\n      __typename\n    }\n    __typename\n  }\n  profile {\n    hasHistory: hasHistoryBySlug(slug: \$slug)\n    __typename\n  }\n  instruments {\n    index {\n      ...InstrumentMainConditionsData\n      ...InstrumentMoneyFundCalculatorData\n      __typename\n    }\n    metaData: bySlug(slug: \$slug, type: \$type) {\n      ... on EtfInstrumentItem {\n        isin\n        moneyFund {\n          profability {\n            percent\n            __typename\n          }\n          __typename\n        }\n        __typename\n      }\n      ... on AnyInstrumentItem {\n        id\n        slug\n        logoId\n        type\n        ticker\n        taxFree\n        exchange\n        approved\n        description\n        displayName\n        commissionFree\n        issuer {\n          sector\n          country\n          description\n          displayName\n          type\n          __typename\n        }\n        marketData {\n          id\n          price\n          priceStep\n          bidPrice\n          askPrice\n          timestamp\n          currencyCode\n          absoluteChange\n          percentChange\n          tradeOpen\n          sessionOpen\n          accruedInterest\n          nextSessionStateChange\n          noBrokerResult\n          lotSize\n          __typename\n        }\n        __typename\n      }\n      ... on ShareInstrumentItem {\n        isin\n        listingExchange\n        listingExchangeTicker\n        __typename\n      }\n      ... on BondInstrumentItem {\n        isin\n        faceValue\n        maturityDate\n        issuer {\n          type\n          country\n          __typename\n        }\n        bondInfo {\n          coupon {\n            fixedPeriod {\n              days\n              perYear\n              __typename\n            }\n            payment {\n              toDate\n              nextDate\n              allTimePayment\n              payment {\n                amount\n                currencyCode\n                __typename\n              }\n              __typename\n            }\n            __typename\n          }\n          __typename\n        }\n        __typename\n      }\n      ... on CurrencyInstrumentItem {\n        assetCurrencyCode\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n}\n\nfragment Trade2 on Query {\n  tanker {\n    Trade: keyset(name: \"Trade\") {\n      data\n      id\n      __typename\n    }\n    __typename\n  }\n  profile {\n    cards {\n      cardId\n      maskedNumber\n      paymentSystem\n      __typename\n    }\n    __typename\n  }\n  instruments {\n    metaData: bySlug(slug: \$slug, type: \$type) {\n      ... on AnyInstrumentItem {\n        id\n        slug\n        type\n        ticker\n        displayName\n        approved\n        exchange\n        commissionFree\n        marketData {\n          id\n          priceStep\n          bidPrice\n          askPrice\n          currencyCode\n          tradeOpen\n          sessionOpen\n          nextSessionStateChange\n          lotSize\n          __typename\n        }\n        __typename\n      }\n      ... on CurrencyInstrumentItem {\n        assetCurrencyCode\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n  ...PortfolioBalanceFragment\n  __typename\n}\n\nfragment PortfolioBalanceFragment on Query {\n  brokerPortfolio: portfolio(agreementType: BROKER) @include(if: \$hasBroker) {\n    main {\n      currency {\n        id\n        amount\n        instrument {\n          id\n          slug\n          assetCurrencyCode\n          __typename\n        }\n        __typename\n      }\n      __typename\n    }\n    position: positionBySlug(slug: \$slug) {\n      ... on AnyPortfolioPosition {\n        id\n        amount\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n  iisPortfolio: portfolio(agreementType: IIS) @include(if: \$hasIIS) {\n    main {\n      currency {\n        id\n        amount\n        instrument {\n          id\n          slug\n          assetCurrencyCode\n          __typename\n        }\n        __typename\n      }\n      __typename\n    }\n    position: positionBySlug(slug: \$slug) {\n      ... on AnyPortfolioPosition {\n        id\n        amount\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n  __typename\n}\n\nfragment InstrumentBondData on Query {\n  tanker {\n    InstrumentBond: keyset(name: \"InstrumentBond\") {\n      data\n      id\n      __typename\n    }\n    __typename\n  }\n  state {\n    ofAffairs {\n      now\n      refinancingRatePercent\n      __typename\n    }\n    __typename\n  }\n  instruments {\n    timeline: bondTimelineEventsBySlug(slug: \$slug) {\n      ... on TimelineEvent {\n        date\n        eventType\n        __typename\n      }\n      ... on TimelineMaturityEvent {\n        maturity {\n          price {\n            amount\n            currencyCode\n            __typename\n          }\n          __typename\n        }\n        __typename\n      }\n      ... on TimelineOfferEvent {\n        offer {\n          buyDate\n          endDate\n          beginDate\n          offerType\n          price {\n            amount\n            currencyCode\n            __typename\n          }\n          __typename\n        }\n        __typename\n      }\n      ... on TimelineCouponEvent {\n        coupon {\n          rate\n          rateDate\n          payment {\n            amount\n            currencyCode\n            __typename\n          }\n          __typename\n        }\n        __typename\n      }\n      ... on TimelineCouponRecurrentEvent {\n        recurrent {\n          dates\n          number\n          lastDate\n          __typename\n        }\n        coupon {\n          rate\n          rateDate\n          couponPeriod {\n            days\n            perYear\n            __typename\n          }\n          payment {\n            amount\n            currencyCode\n            __typename\n          }\n          __typename\n        }\n        __typename\n      }\n      __typename\n    }\n    metaData: bySlug(slug: \$slug, type: \$type) {\n      ... on AnyInstrumentItem {\n        id\n        slug\n        type\n        exchange\n        approved\n        displayName\n        commissionFree\n        issuer {\n          type\n          country\n          displayName\n          __typename\n        }\n        marketData {\n          id\n          price\n          askPrice\n          timestamp\n          tradeOpen\n          sessionOpen\n          currencyCode\n          ownershipDate\n          accruedInterest\n          nextSessionStateChange\n          bondYield {\n            toDate\n            annualYield\n            allTimeYield\n            __typename\n          }\n          __typename\n        }\n        __typename\n      }\n      ... on BondInstrumentItem {\n        faceValue\n        riskLevel\n        bondTaxType\n        maturityDate\n        riskLevel\n        bondRating {\n          agencyRatings {\n            agency\n            category\n            dateAward\n            rating\n            __typename\n          }\n          __typename\n        }\n        bondInfo {\n          coupon {\n            payment {\n              toDate\n              nextDate\n              allTimePayment\n              payment {\n                amount\n                currencyCode\n                __typename\n              }\n              minPayment\n              fixedPayment\n              __typename\n            }\n            fixedPeriod {\n              days\n              perYear\n              __typename\n            }\n            __typename\n          }\n          __typename\n        }\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n  __typename\n}\n\nfragment BondSchoolQuestionData on Query {\n  tanker {\n    QnA: keyset(name: \"QnA\") {\n      data\n      id\n      __typename\n    }\n    __typename\n  }\n  school {\n    questionById(id: \$bondSchoolQuestionId) {\n      id\n      __typename\n    }\n    __typename\n  }\n  __typename\n}\n\nfragment InstrumentEtfData on Query {\n  tanker {\n    InstrumentEtf: keyset(name: \"InstrumentEtf\") {\n      data\n      id\n      __typename\n    }\n    QnA: keyset(name: \"QnA\") {\n      data\n      id\n      __typename\n    }\n    __typename\n  }\n  bunker {\n    instruments {\n      etf {\n        etfcontentsoverrides {\n          id: slug\n          slug\n          contents {\n            id: name\n            name\n            weight\n            color\n            __typename\n          }\n          __typename\n        }\n        qna {\n          id\n          category\n          __typename\n        }\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n  instruments {\n    metaData: bySlug(slug: \$slug, type: \$type) {\n      ... on AnyInstrumentItem {\n        id\n        slug\n        __typename\n      }\n      ... on EtfInstrumentItem {\n        detailedEtfContents {\n          slug\n          type\n          name\n          weight\n          __typename\n        }\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n  __typename\n}\n\nfragment InstrumentShareData on Query {\n  tanker {\n    InstrumentShare: keyset(name: \"InstrumentShare\") {\n      data\n      id\n      __typename\n    }\n    QnA: keyset(name: \"QnA\") {\n      data\n      id\n      __typename\n    }\n    __typename\n  }\n  bunker {\n    instruments {\n      share {\n        componentofqna {\n          id\n          category\n          __typename\n        }\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n  instruments {\n    metaData: bySlug(slug: \$slug, type: \$type) {\n      ... on AnyInstrumentItem {\n        id\n        displayName\n        __typename\n      }\n      ... on ShareInstrumentItem {\n        displayName\n        componentOf {\n          weight\n          instrument {\n            id\n            slug\n            displayName\n            logoId\n            __typename\n          }\n          otherComponentNames\n          __typename\n        }\n        __typename\n      }\n      __typename\n    }\n    __typename\n  }\n  __typename\n}\n\nfragment NewsRelatedProps on NewsRelated {\n  cursor\n  hasMore\n  items {\n    ...NewsItemProps\n    __typename\n  }\n  __typename\n}\n\nfragment NewsItemProps on NewsItem {\n  id\n  title\n  source\n  content\n  publicationDate\n  __typename\n}\n\nfragment InstrumentMainConditionsData on InstrumentsIndex {\n  candles(slug: \"moexrepo\", limit: 1) {\n    close\n    __typename\n  }\n  __typename\n}\n\nfragment InstrumentMoneyFundCalculatorData on InstrumentsIndex {\n  candles(slug: \"moexrepo\", limit: 1) {\n    close\n    __typename\n  }\n  __typename\n}\n",

    @Json(name = "variables")
    val variables: StockVariables
)