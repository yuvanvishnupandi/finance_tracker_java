package com.example.expense_tracker.services;

import com.example.expense_tracker.entities.TransactionCategory;
import com.example.expense_tracker.entities.User;
import com.example.expense_tracker.repositories.TransactionCategoryRepository;
import com.example.expense_tracker.repositories.TransactionRepository; // New Import
import com.example.expense_tracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // New Import

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

    @Autowired
    private TransactionRepository transactionRepository; // Dependency for transactions

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

    // delete (The FIX is here!)
    @Transactional // Ensures both updates/deletes happen as one atomic operation
    public boolean deleteTransactionCategoryById(int transactionCategoryId){
        logger.info("Deleting transaction category with id: " + transactionCategoryId);

        Optional<TransactionCategory> transactionCategoryOptional = transactionCategoryRepository.findById(transactionCategoryId);

        if(transactionCategoryOptional.isEmpty()) {
            logger.warning("Attempted to delete non-existent category: " + transactionCategoryId);
            return false;
        }
        
        // 1. **THE FIX**: Unlink all transactions from this category.
        // This sets the category_id foreign key in the 'transaction' table to NULL 
        // for all records where it matches the category being deleted.
        int updatedCount = transactionRepository.unlinkCategory(transactionCategoryId);
        logger.info("Unlinked " + updatedCount + " transactions from category " + transactionCategoryId);

        // 2. Now the category can be safely deleted.
        transactionCategoryRepository.delete(transactionCategoryOptional.get());
        logger.info("Successfully deleted category: " + transactionCategoryId);
        
        return true;
    }

}