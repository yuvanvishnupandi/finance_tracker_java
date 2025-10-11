package com.example.expense_tracker.services;

import com.example.expense_tracker.entities.TransactionCategory;
import com.example.expense_tracker.entities.User;
import com.example.expense_tracker.repositories.TransactionCategoryRepository;
import com.example.expense_tracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TransactionCategoryService {
    private static final Logger logger = Logger.getLogger(TransactionCategoryService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    // get
    public Optional<TransactionCategory> getTransactionCategoryById(int id){
        logger.info("Getting transaction category by id: " + id);
        return transactionCategoryRepository.findById(id);
    }

    public List<TransactionCategory> getAllTransactionCategoriesByUserId(int userId){
        logger.info("Getting all transaction categories from user: " + userId);
        return transactionCategoryRepository.findAllByUserId(userId);
    }

    // post
    public TransactionCategory createTransactionCategory(int userId, String categoryName, String categoryColor){
        logger.info("Create Transaction Category with user: " + userId);

        // find the user with the userId
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) return null;

        TransactionCategory transactionCategory = new TransactionCategory();
        transactionCategory.setUser(user.get());
        transactionCategory.setCategoryName(categoryName);
        transactionCategory.setCategoryColor(categoryColor);

        return transactionCategoryRepository.save(transactionCategory);
    }

    // update (put)
    public TransactionCategory updateTransactionCategoryById(int transactionCategoryId, String newCategoryName,
                                                             String newCategoryColor){
        logger.info("Updating TransactionCategory with Id: " + transactionCategoryId);

        Optional<TransactionCategory> transactionCategoryOptional = transactionCategoryRepository.findById(transactionCategoryId);
        if(transactionCategoryOptional.isEmpty()){
            return null;
        }

        TransactionCategory updatedTransactionCategory = transactionCategoryOptional.get();
        updatedTransactionCategory.setCategoryName(newCategoryName);
        updatedTransactionCategory.setCategoryColor(newCategoryColor);
        return transactionCategoryRepository.save(updatedTransactionCategory);
    }

    // delete
    public boolean deleteTransactionCategoryById(int transactionCategoryId){
        logger.info("Deleting transaction category with id: " + transactionCategoryId);

        Optional<TransactionCategory> transactionCategoryOptional = transactionCategoryRepository.findById(transactionCategoryId);

        if(transactionCategoryOptional.isEmpty()) return false;

        transactionCategoryRepository.delete(transactionCategoryOptional.get());
        return true;
    }

}







