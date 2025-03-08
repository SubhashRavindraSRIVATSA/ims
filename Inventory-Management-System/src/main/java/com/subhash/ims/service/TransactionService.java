package com.subhash.ims.service;

import com.subhash.ims.dto.Response;
import com.subhash.ims.dto.TransactionRequest;
import com.subhash.ims.enums.TransactionStatus;

public interface TransactionService {
    Response restockInventory(TransactionRequest transactionRequest);
    Response sell(TransactionRequest transactionRequest);
    Response returnToSupplier(TransactionRequest transactionRequest);
    Response getAllTransactions(int page, int size, String searchText);
    Response getTransactionById(Long id);
    Response getAllTransactionByMonthAndYear(int month, int year);
    Response updateTransactionStatus(Long transactionId, TransactionStatus transactionStatus);
}
