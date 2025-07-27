package net.javaguides.banking.controller;
import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.dto.TransferRequestDto;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.repository.AccountRepository;
import net.javaguides.banking.service.AccountService;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController
{
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto)
    {
        return  new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/calculateinterest")
    public ResponseEntity<Double> getCalculateInterestById(@PathVariable Long id)
    {
        double interest = accountService.calculateInterest(id);
        return ResponseEntity.ok(interest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id)
    {
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }


    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id,
      @RequestBody Map<String,Double> request)
    {
            Double amount = request.get("amount");
            AccountDto accountDto = accountService.deposit(id, amount);
            return ResponseEntity.ok(accountDto);
    }

  @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
      @RequestBody Map<String,Double> request)
    {
            Double amount = request.get("amount");
            AccountDto accountDto = accountService.withdraw(id, amount);
            return ResponseEntity.ok(accountDto);
    }


    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts(); 
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable Long id
   ) 
    {
        accountService.deleteAccount(id);
    }   

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransferRequestDto  request)
    {
        accountService.transferMoney(request.getFromAccountId(), request.getToAccountId(), request.getAmount());
        return ResponseEntity.ok("Transfer success");
    }



    

}
