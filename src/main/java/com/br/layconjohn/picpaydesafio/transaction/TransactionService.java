package com.br.layconjohn.picpaydesafio.transaction;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.layconjohn.picpaydesafio.wallet.Wallet;
import com.br.layconjohn.picpaydesafio.wallet.WalletRepository;
import com.br.layconjohn.picpaydesafio.wallet.WalletType;
import com.br.layconjohn.picpaydesafio.authorization.AuthorizerService;
import com.br.layconjohn.picpaydesafio.notification.NotificationService;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizerService authorizerService;
    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository, AuthorizerService authorizerService, NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizerService = authorizerService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        //Validar
        this.validate(transaction);

        //Criar transaction
        var newTransaction = this.transactionRepository.save(transaction);

        //debitar na wallet Payer e creditar na wallet payee
        var walletPayer = this.walletRepository.findById(newTransaction.payer()).get();
        var walletPayee =  this.walletRepository.findById(newTransaction.payee()).get();
        this.walletRepository.save(walletPayer.debit(transaction.value()));
        this.walletRepository.save(walletPayee.credit(transaction.value()));

        //chamar serviço de autorização
        this.authorizerService.authorize();

        //notificationService
        this.notificationService.notify(transaction);

        return newTransaction;
    }

    private boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMUM.getValue() && 
        payer.balance().compareTo(transaction.value()) >= 0 &&
        !payer.id().equals(transaction.payee());
    }

    private void validate(Transaction transaction) {
        this.walletRepository.findById(transaction.payee())
        .map(payee -> this.walletRepository.findById(transaction.payer())
            .map(payer -> this.isTransactionValid(transaction, payer) ? true :  null)
            .orElseThrow(() -> new InvalidTransactionException("Invalid Transaction - %s".formatted(transaction))))
        .orElseThrow(() -> new InvalidTransactionException("Invalid Transaction - %s".formatted(transaction)));
                
    }

    public List<Transaction> list() {
        return this.transactionRepository.findAll();
    }
}
