package net.javaguides.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDto 
{
    private Long fromAccountId;
    private Long toAccountId;
    private double amount;

}
