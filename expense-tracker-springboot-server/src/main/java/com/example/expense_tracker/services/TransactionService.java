package com.example.expense_tracker.services;

import com.example.expense_tracker.entities.Transaction;
import com.example.expense_tracker.entities.TransactionCategory;
import com.example.expense_tracker.entities.User;
import com.example.expense_tracker.repositories.TransactionCategoryRepository;
import com.example.expense_tracker.repositories.TransactionRepository;
import com.example.expense_tracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TransactionService {
    private static final Logger logger = Logger.getLogger(TransactionService.class.getName());

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    // get
    public List<Transaction> getRecentTransactionsByUserId(int userId, int startPage, int endPage, int size){
        logger.info("Getting the most recent transactions for user: " + userId);
        List<Transaction> combinedResults = new ArrayList<>();

        for(int page = startPage; page <= endPage; page++){
            Pageable pageable = PageRequest.of(page, size);
            List<Transaction> pageResults = transactionRepository.findAllByUserIdOrderByTransactionDateDesc(
                    userId,
                    pageable
            );

            combinedResults.addAll(pageResults);
        }
        return combinedResults;
    }

    public List<Transaction> getAllTransactionsByUserIdAndYear(int userId, int year){
        logger.info("Getting all transaction in year:" + year + "for user: " + userId);
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        return transactionRepository.findAllByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(
                userId,
                startDate,
                endDate
        );
    }

    public List<Transaction> getAllTransactionsByUserIdAndYearAndMonth(int userId, int year, int month){
        logger.info("Getting all transaction in month: " + month + " and in year:" + year + "for user: " + userId);
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth());

        return transactionRepository.findAllByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(
                userId,
                startDate,
                endDate
        );
    }

    public List<Integer> getDistinctTransactionYears(int userId){
        return transactionRepository.findDistinctYears(userId);
    }

    // post
    public Transaction createTransaction(Transaction transaction){
        logger.info("Creating Transaction");

        // find category
        Optional<TransactionCategory> transactionCategoryOptional = Optional.empty();
        if(transaction.getTransactionCategory() != null){
            transactionCategoryOptional = transactionCategoryRepository.findById(
                    transaction.getTransactionCategory().getId()
            );
        }

        // find user
        User user = userRepository.findById(transaction.getUser().getId()).get();

        // create the new transaction obj
        Transaction newTransaction = new Transaction();
        newTransaction.setTransactionCategory(
                transactionCategoryOptional.isEmpty() ? null : transactionCategoryOptional.get()
        );
        newTransaction.setUser(user);
        newTransaction.setTransactionName(transaction.getTransactionName());
        newTransaction.setTransactionAmount(transaction.getTransactionAmount());
        newTransaction.setTransactionDate(transaction.getTransactionDate());
        newTransaction.setTransactionType(transaction.getTransactionType());

        return transactionRepository.save(newTransaction);
    }

    // put
    public Transaction updateTransaction(Transaction transaction){
        logger.info("Updating transaction with id: " + transaction.getId());
        Optional<Transaction> transactionOptional = transactionRepository.findById(transaction.getId());

        if(transactionOptional.isEmpty()) return null;
        return transactionRepository.save(transaction);
    }

    // delete
    public void deleteTransactionById(int transactionId){
        logger.info("Deleting transaction with id: " + transactionId);
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);

        if(transactionOptional.isEmpty()) return;

        transactionRepository.delete(transactionOptional.get());
    }
}










