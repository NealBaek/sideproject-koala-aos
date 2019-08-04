package com.ksdigtalnomad.koala.data;

import com.ksdigtalnomad.koala.data.services.UserService;

public enum  ServiceManager {
    INSTANCE;
    private UserService userService;
//    private BalanceService balanceService;
//    private ReceiptService receiptService;
//    private TransactionService transactionService;
//    private PartnerService partnerService;
//    private FAQService faqService;
//    private NoticeService noticeService;
//    private ExchangeService exchangeService;
//    private QuestionService questionService;
//    private TermsService termsService;
//    private M12Service m12Service;

    ServiceManager() {
        userService = RetrofitServiceGenericFactory.createService(UserService.class);
//        balanceService = RetrofitServiceGenericFactory.createService(BalanceService.class);
//        receiptService = RetrofitServiceGenericFactory.createService(ReceiptService.class);
//        transactionService = RetrofitServiceGenericFactory.createService(TransactionService.class);
//        partnerService = RetrofitServiceGenericFactory.createService(PartnerService.class);
//        faqService = RetrofitServiceGenericFactory.createService(FAQService.class);
//        noticeService = RetrofitServiceGenericFactory.createService(NoticeService.class);
//        exchangeService = RetrofitServiceGenericFactory.createService(ExchangeService.class);
//        questionService = RetrofitServiceGenericFactory.createService(QuestionService.class);
//        termsService = RetrofitServiceGenericFactory.createService(TermsService.class);
//        m12Service = RetrofitServiceGenericFactory.createService(M12Service.class);
    }

    public static ServiceManager getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }
//
//    public BalanceService getBalanceService() {
//        return balanceService;
//    }
//
//    public ReceiptService getReceiptService() {
//        return receiptService;
//    }
//
//    public TransactionService getTransactionService() {
//        return transactionService;
//    }
//
//    public PartnerService getPartnerService() {
//        return partnerService;
//    }
//
//    public FAQService getFaqService() {
//        return faqService;
//    }
//
//    public NoticeService getNoticeService() {
//        return noticeService;
//    }
//
//    public ExchangeService getExchangeService() {
//        return exchangeService;
//    }
//
//    public QuestionService getQuestionService() {
//        return questionService;
//    }
//
//    public TermsService getTermsService() {
//        return termsService;
//    }
//
//    public M12Service getM12Service() {
//        return m12Service;
//    }
}
