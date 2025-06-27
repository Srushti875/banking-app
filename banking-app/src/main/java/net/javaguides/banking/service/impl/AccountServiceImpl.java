package net.javaguides.banking.service.impl;

import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.dto.TransferRequestDto;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.entity.Transaction;
import net.javaguides.banking.entity.TransactionType;
import net.javaguides.banking.mapper.AccountMapper;
import net.javaguides.banking.repository.AccountRepository;
import net.javaguides.banking.repository.TransactionRepository;
import net.javaguides.banking.service.AccountService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService
{

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository , TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto)
    {
       Account account = AccountMapper.mapToAccount(accountDto);
       Account savedAccount = accountRepository.save(account);
       return AccountMapper.mapToAccountDto(savedAccount);
    }



 @Override
    public AccountDto getAccountById(Long id) 
    {
        Account account = accountRepository
                            .findById(id)
                            .orElseThrow (() -> new RuntimeException("Account does not exists"));
          return AccountMapper.mapToAccountDto(account);                  

    }



    @Override
    @Transactional
    public AccountDto deposit(Long id, double amount) 
    
    {
        Account account = accountRepository
        .findById(id)
        .orElseThrow (() -> new RuntimeException("Account does not exists"));


       
        double total = account.getBalance() + amount;
        account.setBalance(total);

        Account savedAccount = accountRepository.save(account);

  Transaction transaction = new Transaction( );
  transaction.setAccount(savedAccount); // use the saved account
  transaction.setBalance(amount);
  transaction.setType(TransactionType.DEPOSIT);
  transaction.setTimestamp(LocalDateTime.now());
         transactionRepository.saveAndFlush(transaction);

        return AccountMapper.mapToAccountDto(savedAccount);
      

    }

    @Override
    public AccountDto withdraw(Long id, double amount) 
    {
        Account account = accountRepository
        .findById(id)
        .orElseThrow (() -> new RuntimeException("Account does not exists"));

        
        if(account.getBalance() < amount)
        {
            throw new RuntimeException("insufficient amount");

        }

        if (isBelowMinimumBalance(account, amount)) {
            throw new RuntimeException("Withdrawal denied. Minimum balance rule violated.");
        }
    

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);

     //    Transaction transaction = new Transaction(null,account,amount,TransactionType.WITHDRAWAL,LocalDateTime.now());
     //    transactionRepository.saveAndFlush(transaction);

        return AccountMapper.mapToAccountDto(savedAccount);

    }

    @Override
    public List<AccountDto> getAllAccounts() 
    {
       List<Account>  accounts = accountRepository.findAll();
         return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) 
    {
        Account account = accountRepository
        .findById(id)
        .orElseThrow (() -> new RuntimeException("Account does not exists"));

        accountRepository.deleteById(id);
       
    }

    public void transferMoney(Long fromId,Long toId,double amount)
     {
    //     Optional<Account> fromAccount = Optional.ofNullable(accountRepository.findById(fromId)
    //     .orElseThrow(() -> new RuntimeException("Sender account not found")));

    Account toAccount = accountRepository.findById(toId)
        .orElseThrow(() -> new RuntimeException("Receiver account not found"));


        Account fromAccount = accountRepository
        .findById(fromId)
        .orElseThrow(() -> new RuntimeException("Sender account not found"));
    
    if (fromAccount.getBalance() < amount) 
    {
        throw new RuntimeException("Insufficient balance");
    }

    fromAccount.setBalance(fromAccount.getBalance()-amount);

    toAccount.setBalance(toAccount.getBalance()+amount);

    accountRepository.save(fromAccount);
    accountRepository.save(toAccount);

    Transaction debit = new Transaction(null, fromAccount, amount, TransactionType.WITHDRAWAL, LocalDateTime.now());
    Transaction credit = new Transaction(null, toAccount, amount, TransactionType.DEPOSIT, LocalDateTime.now());

    transactionRepository.save(debit);
    transactionRepository.save(credit);

        
    }

    @Override
    public boolean isBelowMinimumBalance(Account account, double withdrawalamount) {
        double remaining = account.getBalance() - withdrawalamount;
    
        switch (account.getAccountType()) {
            case SAVINGS:
                return remaining < 1000; // ₹1000 minimum
            case CURRENT:
                return remaining < 5000; // ₹5000 minimum
            case JOINT:
                return remaining < 2000; // ₹2000 minimum
            default:
                return true;
        }
    }

    @Override
    public double calculateInterest(Long id)
    {
        Account account = accountRepository
        .findById(id)
        .orElseThrow (() -> new RuntimeException("Account does not exists"));

        double rate;
        switch (account.getAccountType()) {
        case SAVINGS: rate = 0.04; 
                            break;   // 4%
        case CURRENT: rate = 0.02; 
                            break;   // 2%
        case JOINT:   rate = 0.035; 
                            break;  // 3.5%
        default:      rate = 0.0;
    }
    return account.getBalance() * rate;
}

    

   
}
