package net.javaguides.banking.service;

import java.util.List;

import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.dto.TransferRequestDto;
import net.javaguides.banking.entity.Account;

public interface AccountService
{
    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto deposit(Long id, double amount);

    AccountDto withdraw(Long id, double amount);

    List<AccountDto> getAllAccounts();

    void deleteAccount(Long id);

    void transferMoney(Long fromId,Long ToId,double amount);

    boolean isBelowMinimumBalance  (Account account, double withdrawalAmount) ;

    double calculateInterest(Long id);
    
}
